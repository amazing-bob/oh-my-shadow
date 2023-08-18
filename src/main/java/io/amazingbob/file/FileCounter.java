package io.amazingbob.file;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileCounter {
    public static void main(String[] args) {

        final String PROJECT_PATH = "C:\\workspace\\project-name\\";
        List<String> modules = Arrays.asList(
                "module-01"
                ,"module-02"
                ,"module-03"
        );

        for ( String moduleName : modules) {
//            System.out.println("---------------------"+ moduleName +"---------------------");
            System.out.println("---------------------");
            countFilesInFolders(PROJECT_PATH + moduleName +"\\src");
        }

    }

    public static void countFilesInFolders(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("유효하지 않은 폴더 경로입니다.");
            return;
        }

        // controller 별 파일 개수
        printControllerFileCount(folderPath, folder);

        // Service(operation) 별 파일 개수
//        printServiceFileCount(folderPath, folder);

        // sql 별 파일 개수
//        printSQLFileCount(folderPath, folder);

        // 전체 파일 count
//        getAndPrintFileCount(folderPath, folder);


        File[] subFolders = folder.listFiles(File::isDirectory);
        if (subFolders != null) {

            for (File subFolder : subFolders) {
                if (subFolder.getPath().contains("setting")) {
                    continue;
                }
                countFilesInFolders(subFolder.getAbsolutePath());
            }
        }
    }

    private static void printSQLFileCount(String folderPath, File folder) {
        if (folderPath.contains("\\src\\main\\resources\\sqlmap")) {
            getAndPrintFileCount(folderPath, folder);
        }
    }

    private static void printServiceFileCount(String folderPath, File folder) {
        if (folderPath.endsWith("opration") || folderPath.endsWith("operation")) {
            getAndPrintFileCount(folderPath, folder);
        }
    }

    private static void printControllerFileCount(String folderPath, File folder) {
        if (folderPath.endsWith("controller")) {
            getAndPrintFileCount(folderPath, folder);
        }
    }

    private static int getAndPrintFileCount(String folderPath, File folder) {
        int fileCount = countFilesInFolder(folder);
//        System.out.println(folderPath + " 폴더 내 파일 개수: " + fileCount);

        int folderPathLength = folderPath.length();
        int projectPathLength = "C:\\WingsAD\\workspace\\wings-application\\".length();
        String moduleName = folderPath.substring(projectPathLength, folderPathLength).split("\\\\")[0];
        int moduleNameLength = moduleName.length();
        String path = folderPath.substring(projectPathLength + moduleNameLength, folderPathLength);
//        System.out.println(folderPath);
//        System.out.println(moduleName);
//        System.out.println(path);

        System.out.println(moduleName +"\t"+ path + "\t" +fileCount);

        return fileCount;
    }

    public static int countFilesInFolder(File folder) {
        File[] files = folder.listFiles(File :: isFile);
        List<String> list = List.of("Peter", "Thomas", "Edvard", "Gerhard");

        return files != null ? files.length : 0;
    }
}
