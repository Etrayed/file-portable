package io.github.etrayed.fileportable.execution;

import io.github.etrayed.fileportable.FilePortable;
import io.github.etrayed.fileportable.execution.impl.DownloadProcessImpl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Etrayed
 */
public class DownloadExecutor {

    public static URL validate(final String downloadUrl) throws Throwable {
        try {
            final URL url = new URL(downloadUrl);

            if((downloadUrl.lastIndexOf('/') >= downloadUrl.lastIndexOf('.'))
                    || (downloadUrl.lastIndexOf('?') >=  downloadUrl.lastIndexOf('.')))
                throw new Throwable("Invalid url content");

            return url;
        } catch (Throwable throwable) {
            throw new Throwable(throwable);
        }
    }

    public static void execute(final URL downloadUrl) {
        try {
            final HttpURLConnection httpURLConnection = (HttpURLConnection) downloadUrl.openConnection();

            httpURLConnection.connect();

            final DownloadProcess downloadProcess = new DownloadProcessImpl(httpURLConnection);

            final ScheduledFuture scheduledFuture = Executors.newSingleThreadScheduledExecutor()
                    .scheduleAtFixedRate(new ProgressPrinter(downloadProcess, httpURLConnection.getContentLengthLong()),
                            500, 500, TimeUnit.MILLISECONDS);

            downloadProcess.run(scheduledFuture);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private static class ProgressPrinter implements Runnable {

        private final DownloadProcessImpl downloadProcess;

        private final long maxContentLength;

        private boolean endReached;

        private ProgressPrinter(final DownloadProcess downloadProcess, final long maxContentLength) {
            this.downloadProcess = (DownloadProcessImpl) downloadProcess;
            this.maxContentLength = maxContentLength;
        }

        @Override
        public void run() {
            if(endReached)
                return;

            final int percentage = (int) (downloadProcess.getGlobalReadValues() / (maxContentLength / 100));

            FilePortable.getConsole().info("Downloading... " + percentage + "%");

            if(percentage == 100)
                endReached = true;
        }
    }
}
