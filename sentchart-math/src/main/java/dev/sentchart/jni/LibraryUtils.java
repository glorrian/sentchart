package dev.sentchart.jni;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class LibraryUtils {
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static final boolean IS_WINDOWS = OS_NAME.contains("windows");
    private static final boolean IS_LINUX = OS_NAME.contains("linux");
    private static final boolean IS_MACOS = OS_NAME.contains("mac") || OS_NAME.contains("darwin");

    static {
        if (!IS_LINUX && !IS_MACOS && !IS_WINDOWS)
            throw new UnsupportedOperationException("Unsupported operation system: " + OS_NAME);
    }

    private static String getPrefix() {
        return IS_WINDOWS ? "" : "lib";
    }

    private static String getSuffix() {
        if (IS_WINDOWS){
            return ".dll";
        }
        if (IS_LINUX) {
            return ".so";
        }
        if (IS_MACOS) {
            return ".dylib";
        }
        return null;
    }

    public static void loadFromResource(Class<?> cls, String libraryName) {
        InputStream libStream = cls.getResourceAsStream(System.mapLibraryName(libraryName));
        try {
            File tmpFile = File.createTempFile(getPrefix(), getSuffix());
            try {
                Files.copy(libStream, tmpFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                if (tmpFile.exists()) {
                    System.load(tmpFile.getAbsolutePath());
                }
            } finally {
                tmpFile.deleteOnExit();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
