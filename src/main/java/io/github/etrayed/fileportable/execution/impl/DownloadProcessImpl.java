package io.github.etrayed.fileportable.execution.impl;

import io.github.etrayed.fileportable.FilePortable;
import io.github.etrayed.fileportable.execution.DownloadProcess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Etrayed
 */
public class DownloadProcessImpl implements DownloadProcess {

    private static final byte[] COPY_BUFFER = new byte[1024];

    private final HttpURLConnection httpURLConnection;

    private long globalReadValues;

    public DownloadProcessImpl(final HttpURLConnection httpURLConnection) {
        this.httpURLConnection = httpURLConnection;
    }

    @Override
    public void run(final ScheduledFuture scheduledFuture) throws IOException {
        final File tempFile = File.createTempFile("filePortable", ".tmp");

        FilePortable.getConsole().info("Content will be temporarily saved to \"" + tempFile.getCanonicalPath() + "\"");

        try (final FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
             final InputStream inputStream = httpURLConnection.getInputStream()) {

            tempFile.deleteOnExit();

            int readValue;

            while ((readValue = inputStream.read(COPY_BUFFER)) != -1) {
                fileOutputStream.write(COPY_BUFFER, 0, readValue);

                globalReadValues += readValue;
            }

            httpURLConnection.disconnect();
            scheduledFuture.cancel(true);

            FilePortable.getConsole().info("Download completed!");

            final File outputFile = new File(System.console().readLine("Please enter a filename: "));

            outputFile.mkdirs();

            outputFile.createNewFile();

            Files.copy(tempFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            FilePortable.getConsole().info("Content saved to \"" + outputFile.getCanonicalPath() + "\"");

            System.exit(0);
        } catch (Throwable throwable) {
            throw new IOException(throwable);
        }
    }

    public long getGlobalReadValues() {
        return globalReadValues;
    }
}
