package com.pojo;

import java.util.List;

public class Databases {
    private String triple;
    private List formar;
    private List result;
    private String opr;

    public String getOpr() {
        return opr;
    }

    public void setOpr(String opr) {
        this.opr = opr;
    }

    public Databases() {
    }

    public String getTriple() {
        return triple;
    }

    public void setTriple(String triple) {
        this.triple = triple;
    }

    public List getFormar() {
        return formar;
    }

    public void setFormar(List formar) {
        this.formar = formar;
    }

    public List getResult() {
        return result;
    }

    public void setResult(List result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Databases{" +
                "triple='" + triple + '\'' +
                ", formar=" + formar +
                ", result=" + result +
                ", opr='" + opr + '\'' +
                '}';
    }
}
