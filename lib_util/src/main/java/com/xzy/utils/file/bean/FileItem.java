package com.xzy.utils.file.bean;

/**
 * 文件的Bean（文件管理/浏览模块中的一个item）
 *
 * @author xzy
 */
@SuppressWarnings("all")
public class FileItem {
    public int filePic;
    public String fileName;
    public String filePath;
    public String fileModifiedTime;

    public int getFilePic() {
        return filePic;
    }

    public void setFilePic(int filePic) {
        this.filePic = filePic;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileModifiedTime() {
        return fileModifiedTime;
    }

    public void setFileModifiedTime(String fileModifiedTime) {
        this.fileModifiedTime = fileModifiedTime;
    }

    public FileItem(int filePic, String fileName, String filePath,
                    String fileModifiedTime) {
        super();
        this.filePic = filePic;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileModifiedTime = fileModifiedTime;
    }

    @Override
    public String toString() {
        return "FileItem [filePic=" + filePic + ", fileName=" + fileName
                + ", filePath=" + filePath + ", fileModifiedTime="
                + fileModifiedTime + "]";
    }

}
