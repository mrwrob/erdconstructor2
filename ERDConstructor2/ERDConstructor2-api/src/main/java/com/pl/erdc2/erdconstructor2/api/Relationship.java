package com.pl.erdc2.erdconstructor2.api;

import java.awt.Point;
import java.io.Serializable;
import java.util.Observable;


public class Relationship extends Observable implements Serializable{
    private int id;
    private String name;
    private String description;
    private int sourceEntityId;
    private int destinationEntityId;
    
    //save properties
    private Point controlPointLocation;
    private Point nameLabelLocation;
    private boolean controlPointMoved;
    
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

    public int getSourceEntityId() {
        return sourceEntityId;
    }

    public void setSourceEntityId(int sourceEntityId) {
        this.sourceEntityId = sourceEntityId;
    }

    public int getDestinationEntityId() {
        return destinationEntityId;
    }

    public void setDestinationEntityId(int destinationEntityId) {
        this.destinationEntityId = destinationEntityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Point getControlPointLocation() {
        return controlPointLocation;
    }

    public void setControlPointLocation(Point controlPointLocation) {
        this.controlPointLocation = controlPointLocation;
    }

    public Point getNameLabelLocation() {
        return nameLabelLocation;
    }

    public void setNameLabelLocation(Point nameLabelLocation) {
        this.nameLabelLocation = nameLabelLocation;
    }

    public boolean isControlPointMoved() {
        return controlPointMoved;
    }

    public void setControlPointMoved(boolean controlPointMoved) {
        this.controlPointMoved = controlPointMoved;
    }
    
    
}
