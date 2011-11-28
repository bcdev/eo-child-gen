package com.bc.merci.geochildgen;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileTreeExpanderTest extends TestCase {

    private File testDir;
    private FileTreeExpander expander;

    public void testExpandSingleFile() throws IOException {
        final File testFile = createDiskFile("tstfile.whatever", testDir);

        final String expression = testFile.getAbsolutePath();
        final List<File> result = expander.expand(expression);
        assertEquals(1, result.size());

        assertFileInResult(result, testFile);
    }

    public void testExpandSingleFile_fileNotExisting() throws IOException {
        final File testFile = new File(testDir, "notExistingFile.whatever");
        final String expression = testFile.getAbsolutePath();
        final List<File> result = expander.expand(expression);
        assertEquals(0, result.size());
    }

    public void testExpandWildcardFile() throws IOException {
        createDiskFile("tstfile.whatever", testDir);
        createDiskFile("anotherFile.whatever", testDir);
        createDiskFile("anotherFile.this_not", testDir);

        final String expression = testDir.getAbsolutePath() + "/*.whatever";
        final List<File> result = expander.expand(expression);
        assertEquals(2, result.size());
        assertFileInResult(result, new File(testDir, "tstfile.whatever"));
        assertFileInResult(result, new File(testDir, "anotherFile.whatever"));
    }

    public void testExpandWildcardFile_wildcardInMiddleOfName() throws IOException {
        createDiskFile("TomFileOne.whatever", testDir);
        createDiskFile("TomFileTwo.whatever", testDir);
        createDiskFile("PaulFileTwo.whatever", testDir);
        createDiskFile("anotherFile.this_not", testDir);

        final String expression = testDir.getAbsolutePath() + "/Tom*.whatever";
        final List<File> result = expander.expand(expression);
        assertEquals(2, result.size());
        assertFileInResult(result, new File(testDir, "TomFileOne.whatever"));
        assertFileInResult(result, new File(testDir, "TomFileTwo.whatever"));
    }

    public void testExpandWildcardFile_withDirectory() throws IOException {
        createDiskFile("file_one.N1", testDir);
        createDiskFile("file_two.N1", testDir);
        createDiskFile("ignored.file", testDir);
        createDirectory("ignored_dir", testDir);

        final String expression = testDir.getAbsolutePath() + "/*.N1";
        final List<File> result = expander.expand(expression);
        assertEquals(2, result.size());
        assertFileInResult(result, new File(testDir, "file_one.N1"));
        assertFileInResult(result, new File(testDir, "file_two.N1"));
    }

    public void testExpandWildcardFile_withDirectoryMatchingWildcard() throws IOException {
        createDiskFile("file_one.N1", testDir);
        createDiskFile("file_two.N1", testDir);
        createDiskFile("ignored.file", testDir);
        createDirectory("ignored.N1", testDir);
        createDirectory("a_dir.N1", testDir);

        final String expression = testDir.getAbsolutePath() + "/*.N1";
        final List<File> result = expander.expand(expression);
        assertEquals(2, result.size());
        assertFileInResult(result, new File(testDir, "file_one.N1"));
        assertFileInResult(result, new File(testDir, "file_two.N1"));
    }

    public void testExpandWildcard_anySubDirectory() throws IOException {
        final File dir_a = createDirectory("dir_a", testDir);
        final File dir_b = createDirectory("dir_b", testDir);
        createDiskFile("file_one.N1", dir_a);
        createDiskFile("file_two.N1", dir_b);
        createDiskFile("ignored.file", testDir);

        final String expression = testDir.getAbsolutePath() + "/*/*.N1";
        final List<File> result = expander.expand(expression);
        assertEquals(2, result.size());
        assertFileInResult(result, new File(dir_a, "file_one.N1"));
        assertFileInResult(result, new File(dir_b, "file_two.N1"));
    }

    public void testExpandWildcard_anyNestedSubDirectory() throws IOException {
        final File dir_a = createDirectory("dir_a", testDir);
        final File dir_b = createDirectory("dir_b", testDir);
        final File dir_a_a = createDirectory("dir_a_a", dir_a);
        final File dir_b_b = createDirectory("dir_b_b", dir_b);
        createDiskFile("file_one.N1", dir_a_a);
        createDiskFile("file_two.N1", dir_b_b);
        createDiskFile("ignored.file", dir_a);
        createDiskFile("notMentioned.file", dir_b);

        final String expression = testDir.getAbsolutePath() + "/*/*/*.N1";
        final List<File> result = expander.expand(expression);
        assertEquals(2, result.size());
        assertFileInResult(result, new File(dir_a_a, "file_one.N1"));
        assertFileInResult(result, new File(dir_b_b, "file_two.N1"));
    }

    public void testRecurseDirectory() throws IOException {
        final File dir_a = createDirectory("dir_a", testDir);
        final File dir_b = createDirectory("dir_b", testDir);
        createDiskFile("file_one.N1", dir_a);
        createDiskFile("file_two.N1", dir_b);
        createDiskFile("any.file", testDir);

        final String expression = testDir.getAbsolutePath();
        final List<File> result = expander.expand(expression);
        assertEquals(3, result.size());
        assertFileInResult(result, new File(dir_a, "file_one.N1"));
        assertFileInResult(result, new File(dir_b, "file_two.N1"));
        assertFileInResult(result, new File(testDir, "any.file"));
    }

    public void testRecurseDirectory_withWildcard() throws IOException {
        final File dir_a = createDirectory("dir_a", testDir);
        final File dir_b = createDirectory("dir_b", testDir);
        createDiskFile("file_one.N1", dir_a);
        createDiskFile("file_two.N1", dir_b);
        createDiskFile("any.file", testDir);

        final String expression = testDir.getAbsolutePath() + "/*";
        final List<File> result = expander.expand(expression);
        assertEquals(3, result.size());
        assertFileInResult(result, new File(dir_a, "file_one.N1"));
        assertFileInResult(result, new File(dir_b, "file_two.N1"));
        assertFileInResult(result, new File(testDir, "any.file"));
    }

    public void testDenotesWildcardTree() {
        assertTrue(FileTreeExpander.denotesWildcardTree("/opt/files/*/*/*.bla"));

        assertFalse(FileTreeExpander.denotesWildcardTree("/var/data/*.E1"));
        assertFalse(FileTreeExpander.denotesWildcardTree("/var/data/"));
        assertFalse(FileTreeExpander.denotesWildcardTree("/var/data/blabla.data"));


        if (TestUtils.isWindowsSystem()) {
            // windows syntax searches do not work under linux tb 2011-11-28
            assertTrue(FileTreeExpander.denotesWildcardTree("C:\\directory\\*\\*.files"));

            assertFalse(FileTreeExpander.denotesWildcardTree("C:\\directory\\*.files"));
            assertFalse(FileTreeExpander.denotesWildcardTree("C:\\directory\\"));
            assertFalse(FileTreeExpander.denotesWildcardTree("C:\\directory\\the.file"));
        }
    }

    public void testDenotesWildcardFile() {
        assertTrue(FileTreeExpander.denotesWildcardFile("/var/data/*.E1"));

        assertFalse(FileTreeExpander.denotesWildcardFile("/opt/files/*/*/*.bla"));
        assertFalse(FileTreeExpander.denotesWildcardFile("/var/data/blabla.data"));
        assertFalse(FileTreeExpander.denotesWildcardFile("/var/data/"));

        if (TestUtils.isWindowsSystem()) {
            // windows syntax searches do not work under linux tb 2011-11-28
            assertTrue(FileTreeExpander.denotesWildcardFile("C:\\directory\\*.files"));

            assertFalse(FileTreeExpander.denotesWildcardFile("C:\\directory\\*\\*.files"));
            assertFalse(FileTreeExpander.denotesWildcardFile("C:\\directory\\"));
            assertFalse(FileTreeExpander.denotesWildcardFile("C:\\directory\\the.file"));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void setUp() throws Exception {
        testDir = new File("testDir");
        if (!testDir.mkdirs()) {
            fail("unable to create test directory: " + testDir.getAbsolutePath());
        }
        expander = new FileTreeExpander();
    }

    @Override
    protected void tearDown() throws Exception {
        if (testDir != null) {
            TestUtils.deleteFileTree(testDir);
            if (testDir.isDirectory()) {
                fail("unable to delete test directory - check your file streams!");
            }
        }
    }

    private File createDiskFile(String name, File testDir1) throws IOException {
        final File testFile = new File(testDir1, name);
        if (!testFile.createNewFile()) {
            fail("unable to create test file: " + testFile.getAbsolutePath());
        }
        return testFile;
    }

    private File createDirectory(String name, File parentDir) throws IOException {
        final File subDir = new File(parentDir, name);
        if (!subDir.mkdir()) {
            fail("unable to create test directory: " + subDir.getAbsolutePath());
        }
        return subDir;
    }

    private void assertFileInResult(List<File> result, File file) {
        for (File next : result) {
            if (next.getAbsolutePath().equals(file.getAbsolutePath())) {
                return;
            }

        }
        fail("file not in resultlist");
    }
}

