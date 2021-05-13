package com.example.myproject;

public class Qualification {
    private String name;
    private String dno;
    private String sslc;
    private String hsc;
    private  String ug;
    private  String pg;

    public Qualification(String name, String dno, String sslc, String hsc, String ug, String pg) {
        this.name = name;
        this.dno = dno;
        this.sslc = sslc;
        this.hsc = hsc;
        this.ug = ug;
        this.pg = pg;
    }

    public String getName() {
        return name;
    }

    public String getDno() {
        return dno;
    }

    public String getSslc() {
        return sslc;
    }

    public String getHsc() {
        return hsc;
    }

    public String getUg() {
        return ug;
    }

    public String getPg() {
        return pg;
    }
}
