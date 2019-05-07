package io.github.etrayed.fileportable.console.impl;

import io.github.etrayed.fileportable.console.Console;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Etrayed
 */
public class ConsoleImpl implements Console {

    private static final SimpleDateFormat PREFIX_FORMAT;

    private final String defaultUrl;

    public ConsoleImpl(final String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    @Override
    public void info(final String info) {
        System.out.println(PREFIX_FORMAT.format(new Date()) + info);
    }

    @Override
    public void error(final String error) {
        System.err.println(PREFIX_FORMAT.format(new Date()) + error);
    }

    @Override
    public String readInput(final String inputDisplay) {
        if(defaultUrl != null)
            return defaultUrl;

        if(System.console() == null)
            return null;

        return System.console().readLine(inputDisplay);
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }

    static {
        PREFIX_FORMAT = new SimpleDateFormat("[HH-mm-ss] ");
    }
}
