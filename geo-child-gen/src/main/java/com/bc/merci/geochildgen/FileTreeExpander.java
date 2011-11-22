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
        if (denotesDirectory(expression, expressionFile)) {
            if (expression.endsWith("*")) {
                expressionFile = expressionFile.getParentFile();
            }
            return expandDirectory(expressionFile);
        } else if (isWildcardExpression(expression)) {
            return expandWildcardTree(expressionFile);
        } else {
            final ArrayList<File> result = new ArrayList<File>();
            addFile(expression, result);
            return result;
        }
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

    private ArrayList<File> expandWildcardTree(File expressionFile) {
        final Stack<AbstractFileFilter> filterStack = new Stack<AbstractFileFilter>();
        final WildcardFileFilter wildcardFileFilter = new WildcardFileFilter(expressionFile.getName());
        filterStack.push(wildcardFileFilter);
        expressionFile = expressionFile.getParentFile();

        while (isWildcardExpression(expressionFile.getAbsolutePath())) {
            final WildCardDirectoryFilter directoryFilter = new WildCardDirectoryFilter(expressionFile.getName());
            filterStack.push(directoryFilter);
            expressionFile = expressionFile.getParentFile();
        }

        return expandWildcard(filterStack, filterStack.pop(), expressionFile);
    }

    private ArrayList<File> expandWildcard(Stack<AbstractFileFilter> filterStack, AbstractFileFilter filter, File expressionFile) {
        final ArrayList<File> result = new ArrayList<File>();

        AbstractFileFilter subFilter = null;
        final String[] directoryContent = expressionFile.list(filter);
        for (int i = 0; i < directoryContent.length; i++) {
            final File file = new File(expressionFile, directoryContent[i]);
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
                result.add(file);
            }
        }

        return result;
    }

    private ArrayList<File> expandDirectory(File expressionFile) {
        final ArrayList<File> result = new ArrayList<File>();

        final String[] directoryContent = expressionFile.list();
        for (int i = 0; i < directoryContent.length; i++) {
            final File dirItem = new File(expressionFile, directoryContent[i]);
            if (dirItem.isDirectory()) {
                final ArrayList<File> subTree = expandDirectory(dirItem);
                result.addAll(subTree);
            } else {
                result.add(dirItem);
            }
        }

        return result;
    }

    private boolean denotesDirectory(String expression, File expressionFile) {
        return expression.endsWith("*") || expressionFile.isDirectory();
    }

    private boolean isWildcardExpression(String expression) {
        return expression.contains("*");
    }
}
