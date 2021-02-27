package com.shamil.mr_flight.Model;

public class FlightModel {
    String HEAD,DESC,IMAGE,LOCATION,SITE;

    public FlightModel() {
    }

    public FlightModel(String HEAD, String DESC, String IMAGE, String LOCATION, String SITE) {
        this.HEAD = HEAD;
        this.DESC = DESC;
        this.IMAGE = IMAGE;
        this.LOCATION = LOCATION;
        this.SITE = SITE;
    }

    public String getHEAD() {
        return HEAD;
    }

    public void setHEAD(String HEAD) {
        this.HEAD = HEAD;
    }

    public String getDESC() {
        return DESC;
    }

    public void setDESC(String DESC) {
        this.DESC = DESC;
    }

    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getSITE() {
        return SITE;
    }

    public void setSITE(String SITE) {
        this.SITE = SITE;
    }
}
