package io.amazingbob.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterConverter {

    /* 메소드 패턴 */
    private final Pattern METHOD_PATTERN = Pattern.compile("(.*?)(\\(.*?)(Map<.*?>\\s+)(commandMap)(.*?)(\\s*,.*?\\s*\\))(.*?\\{.*)");
    /* 임시 변환 파일 위치 */
    private final String TEMP_FILE_PATH = "./TempFile";

    private int convertedMethodCount;   // 변환한 메소드 갯수
    private File targetFile;    // 변환할 파일
    private File tempFile;      // 임시 변환 파일
    private BufferedReader reader;
    private FileWriter writer;


    public ParameterConverter(File targetFile) throws IOException {
        init(targetFile);
    }

    private void init(File targetFile) throws IOException {
        validateFile(targetFile);
        convertedMethodCount = 0;
        this.targetFile = targetFile;
        this.reader = new BufferedReader(new FileReader(targetFile));
        tempFile = new File(TEMP_FILE_PATH);
        this.writer = new FileWriter(tempFile);
    }


    public File convert() throws IOException {
        return this.convert(targetFile.getParentFile().getPath(), targetFile.getName());
    }

    public File convert(String convertFileName) throws IOException {
        return this.convert(targetFile.getParentFile().getPath(), convertFileName);
    }

    public File convert(String convertFileFolder, String convertFileName) throws IOException {
        String convertFilePath = getConvertFilePath(convertFileFolder, convertFileName);

        String line = null;
        while ((line = reader.readLine()) != null) {
            String convertedLine = convertLine(line);
            writer.write(convertedLine + "\n");
        }

        File convertFile = new File(convertFilePath);
        Files.move(tempFile.toPath(), convertFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        writer.close();
        reader.close();

        return convertFile;
    }

    private String getConvertFilePath(String convertFileFolder, String convertFileName) throws FileNotFoundException {
        // setDefault
        if (convertFileFolder == null || convertFileFolder.isBlank()) {
            convertFileFolder = targetFile.getParentFile().getPath();
        }
        if (!convertFileFolder.endsWith("/")) {
            convertFileFolder += "/";
        }
        if (convertFileName == null || convertFileName.isBlank()) {
            convertFileName = targetFile.getName();
        }
        if (!new File(convertFileFolder).isDirectory()) {
            throw new FileNotFoundException("convertFileFoler 가 잘 못 되었습니다. isNotDirectory");
        }

        return convertFileFolder + convertFileName;
    }

    private void validateFile(File file) throws FileNotFoundException {
        // targetFile validation
        if (file == null) {
            throw new FileNotFoundException("file 인자가 null 입니다.");
        }
        if (!file.isFile()) {
            throw new FileNotFoundException("[%s]는 파일이 아닙니다.".formatted(file.getName()));
        }
    }

    private String convertLine(String line) {
        Matcher methodMatcher = METHOD_PATTERN.matcher(line);
        if (methodMatcher.matches()) {
            String prefix = methodMatcher.group(1);     // public abc
            String preArgs = methodMatcher.group(2);    // (Request req, ...,
            String mapType = methodMatcher.group(3);    // Map<String,Object>
            String mapName = methodMatcher.group(4);    // customMap
            String rest = methodMatcher.group(5) + methodMatcher.group(6) + methodMatcher.group(7); // , ....) {

            String convetedLine = prefix
                    + preArgs
                    + mapType.replace("Map<", "CustomMap<")
                    + mapName.replace("commandMap", "parameterMap")
                    + rest;
            String mapChangeLine = mapType + mapName + " = parameterMap.getMap();";

            convertedMethodCount++;

            return convetedLine + "\n\t\t" + mapChangeLine;
        }

        return line;
    }

    public int getConvertedMethodCount() {
        return convertedMethodCount;
    }

    public void printResult() {
        System.out.println("%s :: %d".formatted(targetFile.getPath(), convertedMethodCount));
    }

}
