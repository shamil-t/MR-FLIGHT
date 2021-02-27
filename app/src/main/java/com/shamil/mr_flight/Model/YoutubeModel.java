package com.shamil.mr_flight.Model;

public class YoutubeModel {
    String VIDEOID,URL,TITLE,DESC;

    public YoutubeModel() {
    }

    public YoutubeModel(String VIDEOID, String URL, String TITLE, String DESC) {
        this.VIDEOID = VIDEOID;
        this.URL = URL;
        this.TITLE = TITLE;
        this.DESC = DESC;
    }

    public String getVIDEOID() {
        return VIDEOID;
    }

    public void setVIDEOID(String VIDEOID) {
        this.VIDEOID = VIDEOID;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getDESC() {
        return DESC;
    }

    public void setDESC(String DESC) {
        this.DESC = DESC;
    }
}
