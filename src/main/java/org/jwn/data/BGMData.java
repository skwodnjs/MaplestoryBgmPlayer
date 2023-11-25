package org.jwn.data;

public class BGMData {
    String title;
    String src;

    BGMData(String title, String src) {
        this.title = title;
        this.src = src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSrc() {
        return src;
    }

    public String getTitle() {
        return title;
    }
}
