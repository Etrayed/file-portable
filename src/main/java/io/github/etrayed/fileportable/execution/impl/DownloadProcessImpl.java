package io.github.etrayed.fileportable.execution.impl;

import io.github.etrayed.fileportable.execution.DownloadProcess;

import java.net.URL;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Etrayed
 */
public class DownloadProcessImpl implements DownloadProcess {

    private final URL url;

    public DownloadProcessImpl(final URL url) {
        this.url = url;
    }

    @Override
    public void run(final ScheduledFuture scheduledFuture) {

    }
}
