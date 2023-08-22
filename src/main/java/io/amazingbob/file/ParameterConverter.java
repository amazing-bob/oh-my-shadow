package io.amazingbob.file;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterConverter {

    private final Pattern METHOD_PATTERN = Pattern.compile("(.*?)(\\(.*?)(Map<.*?>\\s+)(commandMap)(.*?)(\\s*,.*?\\s*\\))(.*?\\{.*)");

    private FileInfo originFileInfo;
    private FileInfo convertedFileInfo;
    private BufferedReader reader;
    private FileWriter writer;
    private int convertedMethodCount;

    public ParameterConverter(FileInfo originFileInfo, FileInfo convertedFileInfo) {
        this.originFileInfo = originFileInfo;
        this.convertedFileInfo = convertedFileInfo;
        initParameterConverter(originFileInfo.getFile(), convertedFileInfo.getFile());
    }

    private void initParameterConverter(File originFile, File convertedFile) {
        try {
            convertedMethodCount = 0;
            reader = new BufferedReader(new FileReader(originFile));
            writer = new FileWriter(convertedFile);
            String line;
            while ( (line = reader.readLine()) != null ) {
                String convertedLine = convertLine(line);
//                writer.write(convertedLine+"\n");
            }

            reader.close();
            writer.close();
            System.out.println("========= Conversion completed. MethodCount: %d :: %s -> %s".formatted(this.convertedMethodCount, this.originFileInfo.getFileName(), this.convertedFileInfo.getFileName()));

        } catch (FileNotFoundException ffe) {
            System.out.println("파일 찾을 수 없음.");
            ffe.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("파일 읽는 도중 오류.");
            ioe.printStackTrace();
        }
    }

    private String convertLine(String line) {
        Matcher methodMatcher = METHOD_PATTERN.matcher(line);
        if (methodMatcher.matches()) {
            String prefix = methodMatcher.group(1);     // public abc
            String preArgs = methodMatcher.group(2);    // (Request req, ...,
            String mapType = methodMatcher.group(3);    // Map<String,Object>
            String mapName = methodMatcher.group(4);    // customMap
            String rest = methodMatcher.group(5) + methodMatcher.group(6); // , ....) {

            String convetedLine = prefix
                    + preArgs
                    + mapType.replace("Map<", "CustomMap<")
                    + mapName.replace("customMap", "parameterMap")
                    + rest;
            String mapChangeLine = mapType + mapName + " = parameterMap.getMap();";

            convertedMethodCount++;

            return convetedLine + "\n" + mapChangeLine;
        }

        return line;
    }


    public FileInfo getOriginFileInfo() {
        return originFileInfo;
    }

    public FileInfo getConvertedFileInfo() {
        return convertedFileInfo;
    }

    public int getConvertedMethodCount() {
        return convertedMethodCount;
    }
}