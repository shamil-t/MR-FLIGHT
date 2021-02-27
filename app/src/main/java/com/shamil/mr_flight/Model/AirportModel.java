package com.shamil.mr_flight.Model;

public class AirportModel {
    String NAME,DESC,LOCATION,IMAGE;

    public AirportModel() {
    }

    public AirportModel(String NAME, String DESC, String LOCATION,String IMAGE) {
        this.NAME = NAME;
        this.DESC = DESC;
        this.LOCATION = LOCATION;
        this.IMAGE = IMAGE;
    }

    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getDESC() {
        return DESC;
    }

    public void setDESC(String DESC) {
        this.DESC = DESC;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }
}
