package io.amazingbob.file;

import java.io.File;

public class FileInfo {
    private String folderPath;
    private String fileName;
    private File file;

    public FileInfo(String folderPath, String fileName) {
        this.folderPath = folderPath;
        this.fileName = fileName;
        if (!folderPath.endsWith("/")) {
            folderPath = folderPath + "/";
        }
        this.file = new File(folderPath + fileName);
    }

    public FileInfo(File file) {
        this.folderPath = file.getParentFile().getPath();
        this.fileName = file.getName();
        this.file = file;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public String getFileName() {
        return fileName;
    }

    public File getFile() {
        return file;
    }
}
