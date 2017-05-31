package com.titapr.bean;

/**
 * Created by D on 5/18/2017.
 */
public class Criterion {

    private long num;
    private String name;
    private int range;
    private int weight;
    private String type;
    private String optimType;
    private String edIzmer;
    private String scaleType;

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOptimType() {
        return optimType;
    }

    public void setOptimType(String optimType) {
        this.optimType = optimType;
    }

    public String getEdIzmer() {
        return edIzmer;
    }

    public void setEdIzmer(String edIzmer) {
        this.edIzmer = edIzmer;
    }

    public String getScaleType() {
        return scaleType;
    }

    public void setScaleType(String scaleType) {
        this.scaleType = scaleType;
    }
}
