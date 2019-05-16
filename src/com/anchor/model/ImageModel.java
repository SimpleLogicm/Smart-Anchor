package com.anchor.model;

/*
 * Created by Sambhaji Karad on 20-Dec-17
 * Mobile 9423476192
 * Email sambhaji2134@gmail.com/
*/

import java.io.Serializable;

public class ImageModel implements Serializable{

    private long id;
    private String imageName;
    private String imagePath;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
