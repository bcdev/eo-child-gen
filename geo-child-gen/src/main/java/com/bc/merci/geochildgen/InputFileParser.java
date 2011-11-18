package com.bc.merci.geochildgen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class InputFileParser {

    private static final String COMMENT = "#";

    public List<String> parse(InputStream inputStream) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        final ArrayList<String> result = new ArrayList<String>();

        String line;
        while ((line = reader.readLine()) != null) {
            final int commentIndex = line.indexOf(COMMENT);
            if (commentIndex == 0) {
                continue;
            }
            if (line.length() == 0) {
                continue;
            }
            if (commentIndex > 0) {
                line = line.substring(0, commentIndex - 1);
            }
            result.add(line.trim());
        }

        return result;
    }
}
