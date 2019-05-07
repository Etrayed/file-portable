package io.github.etrayed.fileportable;

/**
 * @author Etrayed
 */
public class FilePortableLauncher {

    public static void main(String[] args) {
        new FilePortable(System.getProperty("io.github.etrayed.fileportable.defaultUrl", null));
    }
}
