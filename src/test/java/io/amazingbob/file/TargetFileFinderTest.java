package io.amazingbob.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TargetFileFinderTest {

    private String rootPath = "/Users/buru/Projects/oh-my-shadow";
    private TargetFileFinder finder;

    @DisplayName("1. 오예")
    @Test
    void test_1() throws Exception {
        finder = new TargetFileFinder(rootPath);

        assertEquals(rootPath, finder.getFolder().getPath());
        List<File> folders =  finder.getFolders();
        folders.forEach(folder -> System.out.println(folder.getPath()));

//        assertEquals("/Users/buru/Projects/oh-my-shadow/src/main/resources/test-data/replace/folder", finder.getFolders());
//        assertEquals("/Users/buru/Projects/oh-my-shadow/src/main/resources/test-data/replace/folder/folder-1", finder.getFolders());
//        assertEquals("/Users/buru/Projects/oh-my-shadow/src/main/resources/test-data/replace/folder/folder-2", finder.getFolders());
//        assertEquals("/Users/buru/Projects/oh-my-shadow/src/main/resources/test-data/replace/folder/folder-1-1", finder.getFolders());
//        assertEquals("/Users/buru/Projects/oh-my-shadow/src/main/resources/test-data/replace/folder/folder-1-2", finder.getFolders());
//        assertEquals("/Users/buru/Projects/oh-my-shadow/src/main/resources/test-data/replace/folder/folder-1-1-1", finder.getFolders());
    }

}