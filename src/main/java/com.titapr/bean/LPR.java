package com.titapr.bean;


public class LPR {

    private long num;
    private String name;
    private int range;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LPR lpr = (LPR) o;

        return num == lpr.num;

    }

    @Override
    public int hashCode() {
        return (int) (num ^ (num >>> 32));
    }

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
}
