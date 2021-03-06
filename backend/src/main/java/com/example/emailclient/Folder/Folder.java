package com.example.emailclient.Folder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Folder implements IFolder {
    protected String name;
    public boolean setName(String n){
        this.name=n;
        return false;
    }
    public String getName(){
        return this.name;
    }
    @Override
    public boolean create(String name, String email) {
        try{
            String path ="src\\main\\java\\com\\example\\emailclient\\App\\"+email+"\\"+name;
            File f = new File(path);
            if(!f.exists()){
                new File(path).mkdir();
                new File(path+"/index.txt").createNewFile();
                return true;
            }
            else{
                return false;
                // throw new RuntimeException("this name is already exisited");
            }
        } catch (IOException e) {
            System.out.println("error , folder wasn't created");
            return false;
        }
    }

    @Override
    public boolean delete(String name, String email) throws Exception {
        BannedFolders x=new BannedFolders();
        x.IsValid(name);
        File sourceFile = new File("src\\main\\java\\com\\example\\emailclient\\App\\"+email+"\\"+name);
        if(!sourceFile.exists()) {
            System.out.println("Couldn't find folder with such name");
        }

        if (deleteDirectory(sourceFile)) {
            System.out.println("Folder deleted successfully");
            return true;
        }
        else {
            System.out.println("Failed to delete Folder");
        }

        return false;
    }
    boolean deleteDirectory(File file){
        if (file.isDirectory()) {
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    deleteDirectory(entry);
                }
            }
        }
        if (!file.delete()) {
            return false;
        }
        else {return true;}
    }

    @Override
    public boolean rename(String newname, String old, String email) throws Exception {
        System.out.println("start of rename");
        BannedFolders x=new BannedFolders();
        x.IsValid(newname);
        System.out.println("rename "+old);
        System.out.println("sourc=>src\\main\\java\\com\\example\\emailclient\\App\\"+email+"\\"+old);
        File sourceFile = new File("src\\main\\java\\com\\example\\emailclient\\App\\"+email+"\\"+old);
        if(!sourceFile.exists()) {
            System.out.println("Couldn't find folder with such name");
            return false;
        }
        File destFile = new File("src\\main\\java\\com\\example\\emailclient\\App\\"+email+"\\"+newname);
        if (sourceFile.renameTo(destFile)) {
            System.out.println("Folder renamed successfully");
            return true;
        }
        else {
            System.out.println("Failed to rename Folder");
            return false;
        }
    }

    @Override
    public String[] getNames(String email) {
        File x= new File("src\\main\\java\\com\\example\\emailclient\\App\\"+email);
        ArrayList<String> b=new ArrayList<String>();
        b.add("inbox");
        b.add("sent");
        b.add("trash");
        b.add("draft");
        b.add("attachment");
        b.add("info.json");
        String[]temp=x.list();
        ArrayList<String>res=new ArrayList<>();
        for(int i=0;i<temp.length;i++){
            if(b.contains(temp[i])){
                continue;
            }
            res.add(temp[i]);
        }
        return res.toArray(new String[res.size()]);
    }
}