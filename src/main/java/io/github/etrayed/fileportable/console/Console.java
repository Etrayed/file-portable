package io.github.etrayed.fileportable.console;

/**
 * @author Etrayed
 */
public interface Console {

    void info(final String info);

    void error(final String error);

    String readInput(final String inputDisplay);
}
