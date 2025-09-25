package edu.smcrm.domain;

public class Course {
    private final String code;
    private String title;
    private int credits;

    private Course(Builder b) {
        this.code = b.code; this.title = b.title; this.credits = b.credits;
    }

    public String getCode(){return code;}
    public String getTitle(){return title;}
    public int getCredits(){return credits;}
    public void setTitle(String t){this.title=t;}
    public void setCredits(int c){this.credits=c;}
    @Override public String toString(){ return String.format(\"Course[%s - %s (%dcr)]\", code, title, credits); }

    public static class Builder {
        private String code, title; private int credits=3;
        public Builder code(String c){this.code=c;return this;}
        public Builder title(String t){this.title=t;return this;}
        public Builder credits(int c){this.credits=c;return this;}
        public Course build(){ return new Course(this); }
    }
}