package io.amazingbob.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ParameterConverterTest {

    String originFolderPath;
    String originFileName;
    File originFile;
    String convertedFolderPath;
    String convertedFileName;
    File convertedFile;

    ParameterConverter converterS;
    ParameterConverter converterF;

    @BeforeEach
    void setUp () {
        File tobeFolder = new File("/Users/buru/Projects/oh-my-shadow/src/main/resources/test-data/replace/to-be");
        for ( File file : tobeFolder.listFiles() ) {
            file.delete();
        }

        originFolderPath = "/Users/buru/Projects/oh-my-shadow/src/main/resources/test-data/replace/as-is";
        originFileName = "SampleController.java";
        originFile = new File(originFolderPath, originFileName);

        convertedFolderPath = "/Users/buru/Projects/oh-my-shadow/src/main/resources/test-data/replace/to-be";
        convertedFileName = "SampleController.java";
        convertedFile = new File(convertedFolderPath, convertedFileName);

        converterS = new ParameterConverter(
                new FileInfo(originFolderPath, originFileName),
                new FileInfo(convertedFolderPath, convertedFileName));

        converterF = new ParameterConverter(
                new FileInfo(originFolderPath, originFileName),
                new FileInfo(convertedFolderPath, convertedFileName));

    }

    @DisplayName("1. converterInit")
    @Test
    void isInitConverter() throws IOException {
        assertEquals(originFolderPath, converterS.getOriginFileInfo().getFolderPath());
        assertEquals(originFileName, converterS.getOriginFileInfo().getFileName());
        assertEquals(originFile, converterS.getOriginFileInfo().getFile());
        assertEquals(8, converterS.getConvertedMethodCount());

        assertEquals(originFolderPath, converterF.getOriginFileInfo().getFolderPath());
        assertEquals(originFileName, converterF.getOriginFileInfo().getFileName());
        assertEquals(originFile, converterF.getOriginFileInfo().getFile());
        assertEquals(8, converterF.getConvertedMethodCount());

    }


}