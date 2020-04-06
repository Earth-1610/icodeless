package com.itangcent.icode.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    /**
     * Makes a directory, including any necessary but nonexistent parent
     * directories. If a file already exists with specified name but it is
     * not a directory then an IOException is thrown.
     * If the directory cannot be created (or does not already exist)
     * then an IOException is thrown.
     *
     * @param directory directory to create, must not be {@code null}
     * @throws NullPointerException if the directory is {@code null}
     * @throws IOException          if the directory cannot be created or the file already exists but is not a directory
     */
    public static void forceMkdir(final File directory) throws IOException {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                final String message =
                        "File "
                                + directory
                                + " exists and is "
                                + "not a directory. Unable to create directory.";
                throw new IOException(message);
            }
        } else {
            if (!directory.mkdirs()) {
                // Double-check that some other thread or process hasn't made
                // the directory in the background
                if (!directory.isDirectory()) {
                    final String message =
                            "Unable to create directory " + directory;
                    throw new IOException(message);
                }
            }
        }
    }

    /**
     * Makes any necessary but nonexistent parent directories for a given File. If the parent directory cannot be
     * created then an IOException is thrown.
     *
     * @param file file with parent to create, must not be {@code null}
     * @throws NullPointerException if the file is {@code null}
     * @throws IOException          if the parent directory cannot be created
     * @since 2.5
     */
    public static void forceMkdirParent(final File file) throws IOException {
        final File parent = file.getParentFile();
        if (parent == null) {
            return;
        }
        forceMkdir(parent);
    }
}
