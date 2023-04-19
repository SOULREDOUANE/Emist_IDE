package application;

import java.util.ArrayList;

public class Folder {

    private String absolutePath;
    private String fatherPath;
    private String relativePath;
    private String name;

    public Folder(String absolutePath, String name) {
        setAbsolutePath(absolutePath);
        setName(name);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getFatherPath() {
        return fatherPath;
    }

    public void setFatherPath(String fatherPath) {
        this.fatherPath = fatherPath;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    private ArrayList<String> pathOfChildFiles= new ArrayList<>(); 



    public void addChildFolder(String path) {
        this.pathOfChildFiles.add(path);
    }

    public void removeChildeFolder(String path) {
        this.pathOfChildFiles.remove(path);
    }






}
