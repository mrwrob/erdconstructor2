package com.pl.erdc2.erdconstructor2.api;

import java.awt.Point;
import java.io.Serializable;
import java.util.Observable;


public class Relationship extends Observable implements Serializable{
    private int id;
    private String name;
    private String description;
    private int sourceEntityId;
    private String sourceType;
    private int destinationEntityId;
    private String destinationType;
    
    //save properties
    private Point controlPointLocation;
    private Point nameLabelLocation;
    private boolean controlPointMoved;
    
    public static final String[] TYPES = {"0..1", "1", "0..n", "1..n"};

    public Relationship() {
        this.destinationType=TYPES[1];
        this.sourceType=TYPES[1];
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
    }

    public int getSourceEntityId() {
        return sourceEntityId;
    }

    public void setSourceEntityId(int sourceEntityId) {
        if(this.sourceEntityId!=sourceEntityId){
            this.sourceEntityId = sourceEntityId;
            setChanged();
            notifyObservers("sourceEntityId");
        }
    }

    public int getDestinationEntityId() {
        return destinationEntityId;
    }

    public void setDestinationEntityId(int destinationEntityId) {
        if(this.destinationEntityId!=destinationEntityId){
            this.destinationEntityId = destinationEntityId;
            setChanged();
            notifyObservers("destinationEntityId");
        }
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

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        if(!this.sourceType.equals(sourceType)){
           this.sourceType = sourceType;
            setChanged();
            notifyObservers("sourceType");
        }
    }

    public String getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(String destinationType) {
        if(!this.destinationType.equals(destinationType)){
            this.destinationType = destinationType;
            setChanged();
            notifyObservers("destinationType");
        }
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    
}
