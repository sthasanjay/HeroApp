package com.example.heroapp.Model;

public class Heroes {

    private String _id;
    private String name;
    private String desc;
    private String imageName;


    public Heroes(String _id, String name, String desc, String imageName) {
        this._id = _id;
        this.name = name;
        this.desc = desc;
        this.imageName = imageName;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
