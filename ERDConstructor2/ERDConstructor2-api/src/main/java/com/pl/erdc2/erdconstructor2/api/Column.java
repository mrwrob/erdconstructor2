package com.pl.erdc2.erdconstructor2.api;

import java.io.Serializable;
import java.util.Observable;


public class Column extends Observable implements Serializable{
    
    private String name;
    private int id;
    private String description;
    private boolean primary;
    private String type;
    public static int availableId=1;
    
    public Column(){
        id=availableId++;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setChanged();
        notifyObservers("name");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        setChanged();
        notifyObservers("description");
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
        setChanged();
        notifyObservers("primary");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        setChanged();
        notifyObservers("type");
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id=id;
    }
}
