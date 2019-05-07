package io.github.etrayed.fileportable.execution;

import java.util.concurrent.ScheduledFuture;

/**
 * @author Etrayed
 */
public interface DownloadProcess {

    void run(final ScheduledFuture scheduledFuture);
}
