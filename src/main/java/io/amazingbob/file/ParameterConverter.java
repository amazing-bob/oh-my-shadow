package io.amazingbob.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterConverter {

    private final Pattern METHOD_PATTERN = Pattern.compile("(.*?)(\\(.*?)(Map<.*?>\\s+)(commandMap)(.*?)(\\s*,.*?\\s*\\))(.*?\\{.*)");


    /**
     * 1. 초기화 init()
     *      - BufferedReader / FileWriter null;
     * 2. covert(targetFile) 실행 시 대상파일의 내용 변경
     *      - BufferedReader/FileWriter 초기화
     *      - 현재파일 읽기
     *      - 컨버팅해서 임시 파일생성
     *      - 원본 파일 삭제
     *      - 임시 파일 이전파일 이름으로 변경
     *      - return convertedFile
     * 3. convert(targetFile, convertedFile)
     *      - BufferedReader/FileWriter 초기화
     *      - 현재파일 읽기
     *      - 컨버팅해서 convertedFile 생성
     *      - return convertedFile
     */

    private final String TEMP_FILE_PATH = "./TempFile";
    private int convertedMethodCount;
    private File targetFile;
    private File tempFile;
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


//    private FileInfo originFileInfo;
//    private FileInfo convertedFileInfo;
//
//    public ParameterConverter(FileInfo originFileInfo, FileInfo convertedFileInfo) {
//        this.originFileInfo = originFileInfo;
//        this.convertedFileInfo = convertedFileInfo;
//        initParameterConverter(originFileInfo.getFile(), convertedFileInfo.getFile());
//    }
//
//    private void initParameterConverter(File originFile, File convertedFile) {
//        try {
//            convertedMethodCount = 0;
//            reader = new BufferedReader(new FileReader(originFile));
//            writer = new FileWriter(convertedFile);
//            String line;
//            while ( (line = reader.readLine()) != null ) {
//                String convertedLine = convertLine(line);
////                writer.write(convertedLine+"\n");
//            }
//
//            reader.close();
//            writer.close();
//            System.out.println("========= Conversion completed. MethodCount: %d :: %s -> %s".formatted(this.convertedMethodCount, this.originFileInfo.getFileName(), this.convertedFileInfo.getFileName()));
//
//        } catch (FileNotFoundException ffe) {
//            System.out.println("파일 찾을 수 없음.");
//            ffe.printStackTrace();
//        } catch (IOException ioe) {
//            System.out.println("파일 읽는 도중 오류.");
//            ioe.printStackTrace();
//        }
//    }
//
//    private String convertLine(String line) {
//        Matcher methodMatcher = METHOD_PATTERN.matcher(line);
//        if (methodMatcher.matches()) {
//            String prefix = methodMatcher.group(1);     // public abc
//            String preArgs = methodMatcher.group(2);    // (Request req, ...,
//            String mapType = methodMatcher.group(3);    // Map<String,Object>
//            String mapName = methodMatcher.group(4);    // customMap
//            String rest = methodMatcher.group(5) + methodMatcher.group(6); // , ....) {
//
//            String convetedLine = prefix
//                    + preArgs
//                    + mapType.replace("Map<", "CustomMap<")
//                    + mapName.replace("customMap", "parameterMap")
//                    + rest;
//            String mapChangeLine = mapType + mapName + " = parameterMap.getMap();";
//
//            convertedMethodCount++;
//
//            return convetedLine + "\n" + mapChangeLine;
//        }
//
//        return line;
//    }
//
//
//    public FileInfo getOriginFileInfo() {
//        return originFileInfo;
//    }
//
//    public FileInfo getConvertedFileInfo() {
//        return convertedFileInfo;
//    }

    public int getConvertedMethodCount() {
        return convertedMethodCount;
    }
}
