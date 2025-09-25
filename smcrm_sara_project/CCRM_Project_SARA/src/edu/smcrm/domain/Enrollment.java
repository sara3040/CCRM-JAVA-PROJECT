package edu.smcrm.domain;

import edu.smcrm.domain.Grade;

public class Enrollment {
    private final Course course;
    private Double marks; // 0-100
    private Grade grade;

    public Enrollment(Course course) { this.course = course; }

    public Course getCourse(){return course;}
    public Double getMarks(){return marks;}
    public Grade getGrade(){return grade;}

    public void setMarks(double m){ this.marks = m; this.grade = Grade.fromMarks(m); }

    @Override public String toString(){
        return String.format(\"%s -> marks:%s grade:%s\", course.getCode(), marks==null?\"-\":marks, grade==null?\"-\":grade);
    }
}