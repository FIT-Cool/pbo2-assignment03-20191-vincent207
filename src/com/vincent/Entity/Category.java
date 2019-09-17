package com.vincent.Entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class Category implements Serializable {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();

    public String getName(){
        return name.get();
    }

    public void setName(String value){
        name.set(value);
    }

    public StringProperty nameProperty(){
        return name;
    }

    public int getId(){
        return id.get();
    }

    public void setId(int value){
        id.set(value);
    }

    public IntegerProperty idProperty(){
        return id;
    }

    @Override
    public String toString() {
        return getName();
    }
}
