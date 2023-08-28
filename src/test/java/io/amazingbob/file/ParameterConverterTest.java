package io.amazingbob.file;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ParameterConverterTest {

    String targetFileFolder;
    String targetFileName;
    File targetFile;
    String convertedFolderPath;
    String convertedFileName;
    File convertedFile;

    ParameterConverter converterS;
    ParameterConverter converterF;
    ParameterConverter converter;


    @BeforeEach
    void setUp() throws IOException {
        targetFileFolder = "/Users/buru/Projects/oh-my-shadow/src/main/resources/test-data/replace/as-is";
        targetFileName = "SampleController.java";
        targetFile = new File(targetFileFolder + "/" + targetFileName);

    }
    @AfterAll
    static void finish() throws IOException {
        new File("/Users/buru/Projects/oh-my-shadow/src/main/resources/test-data/replace/as-is/SampleController.java").delete();
        new File("/Users/buru/Projects/oh-my-shadow/src/main/resources/test-data/replace/to-be/empty").delete();
        new File("/Users/buru/Projects/oh-my-shadow/src/main/resources/test-data/replace/to-be/SampleController.java").delete();
        new File("/Users/buru/Projects/oh-my-shadow/src/main/resources/test-data/replace/to-be/SampleController.java").delete();
        Files.copy(new File("/Users/buru/Projects/oh-my-shadow/src/main/resources/test-data/replace/as-is/SampleController.java_ori").toPath(), new File("/Users/buru/Projects/oh-my-shadow/src/main/resources/test-data/replace/as-is/SampleController.java").toPath(), StandardCopyOption.REPLACE_EXISTING);



    }


    @DisplayName("1. Throw TEST")
    @Test
    void test_1() throws IOException {
        String convertFileFolder = "/Users/buru/Projects/oh-my-shadow/src/main/resources/test-data/replace/to-be";
        String convertFileName = "SampleController.java";

        assertEquals(targetFileName, new ParameterConverter(targetFile).convert(convertFileFolder, null).getName());
        assertEquals(targetFileName, new ParameterConverter(targetFile).convert(convertFileFolder, "").getName());
        assertEquals("empty", new ParameterConverter(targetFile).convert(convertFileFolder, "empty").getName());
        assertEquals(targetFileFolder, new ParameterConverter(targetFile).convert(null, convertFileName).getParentFile().getPath());
        assertEquals(targetFileFolder, new ParameterConverter(targetFile).convert("", convertFileName).getParentFile().getPath());
        assertThrows(FileNotFoundException.class, () -> new ParameterConverter(targetFile).convert("empty", convertFileName));
        assertEquals(convertFileFolder + "/" + targetFileName, new ParameterConverter(targetFile).convert(convertFileFolder, null).getPath());
        assertEquals(targetFileFolder + "/" + targetFileName, new ParameterConverter(targetFile).convert(targetFileFolder, targetFileName).getPath());
    }


}