package com.pl.erdc2.erdconstructor2.api;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Entity implements Serializable{
    private int id;
    private String name;
    private String description;
    
    //save properties
    private Rectangle bounds;
    private Point location;
    private List<Column> columns;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public List<Column> getColumns() {
        if(columns==null)
            columns=new ArrayList<>();
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
    
}
