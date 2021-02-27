package com.shamil.mr_flight.Model;

public class AirportStatusViewModel {

    String FLlGHT_NO,FROM,AIRLINE,SCHEDULE,ARRIVAL,STATUS;

    public AirportStatusViewModel() {
    }

    public String getFLlGHT_NO() {
        return FLlGHT_NO;
    }

    public void setFLlGHT_NO(String FLlGHT_NO) {
        this.FLlGHT_NO = FLlGHT_NO;
    }

    public String getFROM() {
        return FROM;
    }

    public void setFROM(String FROM) {
        this.FROM = FROM;
    }

    public String getAIRLINE() {
        return AIRLINE;
    }

    public void setAIRLINE(String AIRLINE) {
        this.AIRLINE = AIRLINE;
    }

    public String getSCHEDULE() {
        return SCHEDULE;
    }

    public void setSCHEDULE(String SCHEDULE) {
        this.SCHEDULE = SCHEDULE;
    }

    public String getARRIVAL() {
        return ARRIVAL;
    }

    public void setARRIVAL(String ARRIVAL) {
        this.ARRIVAL = ARRIVAL;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
