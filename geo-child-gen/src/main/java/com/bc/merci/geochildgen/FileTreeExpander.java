package com.bc.merci.geochildgen;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class FileTreeExpander {

    List<File> expand(String expression) {
        final ArrayList<File> result = new ArrayList<File>();


        if (expression.contains("*")) {
            final File expressionFile = new File(expression);
            final WildcardFileFilter wildcardFileFilter = new WildcardFileFilter(expressionFile.getName());

            final File sourceDir = expressionFile.getParentFile();
            if (sourceDir.getAbsolutePath().contains("*")) {
                final List<File> subResult = expand(sourceDir.getAbsolutePath());
                result.addAll(subResult);
            }

            final String[] sourceDirContent = sourceDir.list(wildcardFileFilter);
            for (String sourceFile : sourceDirContent) {
                final File file = new File(sourceDir, sourceFile);
                if (file.isDirectory()) {
                    continue;
                }

                addFile(file.getAbsolutePath(), result);
            }
        } else {
            addFile(expression, result);
        }

        return result;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private void addFile(String expression, ArrayList<File> result) {
        final File file = new File(expression);
        if (file.isFile()) {
            result.add(file);
        } else {
            System.out.println("File does not exist: " + file.getAbsolutePath());
        }
    }
}
