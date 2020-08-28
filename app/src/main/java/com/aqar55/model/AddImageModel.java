package com.aqar55.model;


import java.io.File;
import java.io.Serializable;

public class AddImageModel implements Serializable {
    private String title;
    private File file;

    public AddImageModel(String title, File file) {
        this.title = title;
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
