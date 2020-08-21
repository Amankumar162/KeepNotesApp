package com.example.keepnotes.model;

public class extract {
    private String titles;
    private String content;

    public extract(){}
    public extract(String titles,String content){
        this.titles = titles;
        this.content = content;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
