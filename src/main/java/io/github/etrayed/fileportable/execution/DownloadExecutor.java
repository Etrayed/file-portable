package io.github.etrayed.fileportable.execution;

import io.github.etrayed.fileportable.execution.impl.DownloadProcessImpl;

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
            final DownloadProcess downloadProcess = new DownloadProcessImpl(downloadUrl);

            final ScheduledFuture scheduledFuture = Executors.newSingleThreadScheduledExecutor()
                    .scheduleAtFixedRate(new ProgressPrinter(downloadProcess), 3, 3, TimeUnit.SECONDS);

            downloadProcess.run(scheduledFuture);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private static class ProgressPrinter implements Runnable {

        private final DownloadProcessImpl downloadProcess;

        private ProgressPrinter(final DownloadProcess downloadProcess) {
            this.downloadProcess = (DownloadProcessImpl) downloadProcess;
        }

        @Override
        public void run() {

        }
    }
}