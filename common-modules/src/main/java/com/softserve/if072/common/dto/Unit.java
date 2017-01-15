package com.softserve.if072.common.dto;


public class Unit {

    private int id;
    private String name;

    public Unit() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Unit(int id, String name) {
        this.id = id;
        this.name = name;
    }


}
