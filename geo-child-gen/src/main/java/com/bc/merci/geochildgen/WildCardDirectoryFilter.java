package com.bc.merci.geochildgen;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;

class WildCardDirectoryFilter extends WildcardFileFilter {

    WildCardDirectoryFilter(String wildcard) {
        super(wildcard);
    }

    @SuppressWarnings({"SimplifiableIfStatement"})
    @Override
    public boolean accept(File dir, String name) {
        if (new File(dir, name).isFile()) {
            return false;
        }
        return super.accept(dir, name);
    }

    @Override
    public boolean accept(File file) {
        if (file.isFile()) {
            return false;
        }
        return super.accept(file);
    }
}
