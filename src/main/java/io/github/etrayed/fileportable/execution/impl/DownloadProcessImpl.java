package io.github.etrayed.fileportable.execution.impl;

import io.github.etrayed.fileportable.execution.DownloadProcess;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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

        try (final FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
             final InputStream inputStream = httpURLConnection.getInputStream()) {

            tempFile.deleteOnExit();

            int readValue;

            while ((readValue = inputStream.read(COPY_BUFFER)) != -1) {
                fileOutputStream.write(readValue);

                globalReadValues += readValue;
            }

            final File outputFile = new File(httpURLConnection.getURL().getFile());

            outputFile.createNewFile();

            FileUtils.copyFile(tempFile, outputFile);

            httpURLConnection.disconnect();
            scheduledFuture.cancel(true);
        }
    }

    public long getGlobalReadValues() {
        return globalReadValues;
    }
}
