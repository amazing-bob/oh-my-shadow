package io.amazingbob.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TargetFileFinder {
    private String rootPath;
    private List<File> forders;


    public TargetFileFinder(String rootPath) {
        this.rootPath = rootPath;
    }

    public File getFolder() {
        return new File(rootPath);
    }

    public List<File> getFolders() throws Exception {
        this.forders = new ArrayList<>();
        searchFolder(rootPath);
        return this.forders;
    }

    private void searchFolder(String path) throws Exception {
        File folder = new File(path);

        if (!folder.isDirectory() || !folder.exists()) {
            forders.add(folder);
            return ;
        } else {

        }
        // 파일 추가

        // 폴더


        File[] subFolders = folder.listFiles();
        if (subFolders != null) {
            for (File subFolder : subFolders) {
                searchFolder(subFolder.getAbsolutePath());
            }
        }
    }

    /**
     * 1. 모든 폴더 탐색
     * 2. 조건 맞는 폴더 임시 저장
     * 3. 조건에 맞는 폴더 path List 리턴
     * 4. 조건에 맞는 파일 path List 리턴
     *
     */





}
