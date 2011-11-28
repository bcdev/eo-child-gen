package com.bc.merci.geochildgen;

import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@SuppressWarnings({"unchecked"})
class FileTreeExpander {

    List<File> expand(String expression) {
        File expressionFile = new File(expression);
        if (denotesDirectory(expressionFile)) {
            if (expression.endsWith("*")) {
                expressionFile = expressionFile.getParentFile();
            }
            return expandDirectory(expressionFile);
        } else if (denotesWildcardTree(expression)) {
            return expandWildcardTree(expressionFile);
        } else if (denotesWildcardFile(expression)) {
            return expandWildcardFile(expressionFile);
        } else {
            return createSingleFileResult(expressionFile);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    // package access for testing only tb 2011-11-24
    static boolean denotesWildcardTree(String expression) {
        final File expressionFile = new File(expression);
        final File parentFile = expressionFile.getParentFile();
        return parentFile.getAbsolutePath().contains("*") && expressionFile.getAbsolutePath().contains("*");
    }

    // package access for testing only tb 2011-11-24
    static boolean denotesWildcardFile(String expression) {
        final File expressionFile = new File(expression);
        final File parentFile = expressionFile.getParentFile();
        return expressionFile.getAbsolutePath().contains("*") && !parentFile.getAbsolutePath().contains("*");
    }

    private void addFile(File file, ArrayList<File> result) {
        if (file.isFile()) {
            result.add(file);
        } else {
            System.out.println("File does not exist: " + file.getAbsolutePath());
        }
    }

     private List<File> createSingleFileResult(File expressionFile) {
        final ArrayList<File> result = new ArrayList<File>();
        addFile(expressionFile, result);
        return result;
    }

    private ArrayList<File> expandWildcardTree(File expressionFile) {
        final Stack<AbstractFileFilter> filterStack = new Stack<AbstractFileFilter>();
        final WildcardFileFilter wildcardFileFilter = new WildcardFileFilter(expressionFile.getName());
        filterStack.push(wildcardFileFilter);
        expressionFile = expressionFile.getParentFile();

        while (expressionFile.getAbsolutePath().contains("*")) {
            final WildCardDirectoryFilter directoryFilter = new WildCardDirectoryFilter(expressionFile.getName());
            filterStack.push(directoryFilter);
            expressionFile = expressionFile.getParentFile();
        }

        return expandWildcard(filterStack, filterStack.pop(), expressionFile);
    }

    private List<File> expandWildcardFile(File expressionFile) {
        final ArrayList<File> result = new ArrayList<File>();
        final WildcardFileFilter wildcardFileFilter = new WildcardFileFilter(expressionFile.getName());
        final File searchDirectory = expressionFile.getParentFile();
        final String[] directoryContent = searchDirectory.list(wildcardFileFilter);
        for (String directoryItem : directoryContent) {
            addFile(new File(searchDirectory, directoryItem), result);
        }

        return result;  //To change body of created methods use File | Settings | File Templates.
    }

    private ArrayList<File> expandWildcard(Stack<AbstractFileFilter> filterStack, AbstractFileFilter filter, File expressionFile) {
        final ArrayList<File> result = new ArrayList<File>();

        AbstractFileFilter subFilter = null;
        final String[] directoryContent = expressionFile.list(filter);
        for (String directoryItem : directoryContent) {
            final File file = new File(expressionFile, directoryItem);
            if (file.isDirectory()) {
                if (subFilter == null) {
                    if (filterStack.isEmpty()) {
                        return result;
                    }
                    // need to have the same stack for each recursion layer tb 2011-11-22
                    filterStack = (Stack<AbstractFileFilter>) filterStack.clone();
                    subFilter = filterStack.pop();
                }
                final ArrayList<File> subResult = expandWildcard(filterStack, subFilter, file);
                result.addAll(subResult);
            } else {
                addFile(file, result);
            }
        }

        return result;
    }

    private ArrayList<File> expandDirectory(File expressionFile) {
        final ArrayList<File> result = new ArrayList<File>();

        final String[] directoryContent = expressionFile.list();
        for (String directoryItem : directoryContent) {
            final File dirItem = new File(expressionFile, directoryItem);
            if (dirItem.isDirectory()) {
                final ArrayList<File> subTree = expandDirectory(dirItem);
                result.addAll(subTree);
            } else {
                addFile(dirItem, result);
            }
        }

        return result;
    }

    private boolean denotesDirectory(File expressionFile) {
        return expressionFile.getName().endsWith("*") || expressionFile.isDirectory();
    }
}
