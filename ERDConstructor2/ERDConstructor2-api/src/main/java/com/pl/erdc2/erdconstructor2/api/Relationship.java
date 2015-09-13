package com.pl.erdc2.erdconstructor2.api;

import java.util.Observable;


public class Relationship extends Observable{
    private int id;
    private String name;
    private String description;
    private Entity sourceEntity;
    private Entity destinationEntity;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyObservers("name");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyObservers("description");
    }

    public Entity getSourceEntity() {
        return sourceEntity;
    }

    public void setSourceEntity(Entity sourceEntity) {
        this.sourceEntity = sourceEntity;
        notifyObservers("sourceEntity");
    }

    public Entity getDestinationEntity() {
        return destinationEntity;
    }

    public void setDestinationEntity(Entity destinationEntity) {
        this.destinationEntity = destinationEntity;
        notifyObservers("destinationEntity");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
