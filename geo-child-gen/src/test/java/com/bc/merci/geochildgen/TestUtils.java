package com.bc.merci.geochildgen;

import java.io.File;

class TestUtils {
    // @todo 3 - this code is copied from BcCommons:FileUtils - as Peter stated, we should
    // create a general testing framework that covers this functionality
    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    static void deleteFileTree(File treeRoot) {
        File[] files = treeRoot.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFileTree(file);
                }
                file.delete();
            }
        }
        treeRoot.delete();
    }
}
