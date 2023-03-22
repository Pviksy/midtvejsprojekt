package com.opgavedb_1.entities.objects;

public class File extends Object {

    private String filePath;
    private String hexString;


    public File(String title, String filePath) {
        super(title);
        this.filePath = filePath;
    }

    //public File(int id, String title, String filePath) {
    //    super(id, title);
    //    this.filePath = filePath;
    //}

    public File(int id, String hexString, String title) {
        super(id, title);
        this.hexString = hexString;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getHexString() {
        return hexString;
    }

    public void setHexString(String hexString) {
        this.hexString = hexString;
    }
}
