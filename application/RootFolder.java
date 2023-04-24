package application;

import java.util.ArrayList;

public class RootFolder {


    private String absolutePath;
    private String name;
    private ArrayList<Folder> pathOfChildFolders = new ArrayList<>();

    public RootFolder(String absolutePath,String Name) {
        setAbsolutePath(absolutePath);
        setName(Name);
    }

    public void addChildFolder(Folder folder) {
        this.pathOfChildFolders.add(folder);
    }

    public void removeChildeFolder(Folder folder) {
        this.pathOfChildFolders.remove(folder);
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
