package io.github.etrayed.fileportable;

import io.github.etrayed.fileportable.console.Console;
import io.github.etrayed.fileportable.console.impl.ConsoleImpl;
import io.github.etrayed.fileportable.execution.DownloadExecutor;

/**
 * @author Etrayed
 */
public class FilePortable {

    private final Console console;

    FilePortable(final String defaultUrl) {
        this.console = new ConsoleImpl(defaultUrl);

        checkInput();
    }

    private void checkInput() {
        final String downloadUrl = this.console.readInput("Please enter the source-url: ");

        try {
            DownloadExecutor.execute(DownloadExecutor.validate(downloadUrl));
        } catch (Throwable throwable) {
            console.error("A \'" + throwable.getClass().getSimpleName()
                    + "\' occurred while executing the download process: " + throwable.getMessage());
        }
    }
}
