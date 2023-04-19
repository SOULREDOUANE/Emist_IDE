package application;

import java.util.ArrayList;

public class RootFolder {


    private String absolutePath;
    private String name;
    private ArrayList<String> pathOfChildFolders = new ArrayList<>();

    public RootFolder(String absolutePath,String Name) {
        setAbsolutePath(absolutePath);
        setName(Name);
    }

    public void addChildFolder(String path) {
        this.pathOfChildFolders.add(path);
    }

    public void removeChildeFolder(String path) {
        this.pathOfChildFolders.remove(path);
    }


    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
