package edu.smcrm.domain;

import java.time.LocalDateTime;
import java.util.*;

public class Student extends Person {
    private String regNo;
    private boolean active = true;
    private LocalDateTime createdAt = LocalDateTime.now();
    private final Map<String, Enrollment> enrollments = new HashMap<>();

    private Student(Builder b) {
        super(java.util.UUID.randomUUID().toString(), b.fullName, b.email);
        this.regNo = b.regNo;
    }

    public String getRegNo() { return regNo; }
    public boolean isActive() { return active; }
    public void deactivate() { this.active = false; }

    public void addEnrollment(Enrollment e) { enrollments.put(e.getCourse().getCode(), e); }
    public void removeEnrollment(String courseCode) { enrollments.remove(courseCode); }
    public Collection<Enrollment> getEnrollments() { return enrollments.values(); }

    public String profileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(\"Student: \").append(fullName).append(\"\\nRegNo: \").append(regNo).append(\"\\nEmail: \").append(email).append(\"\\nEnrollments:\\n\");
        enrollments.values().forEach(en -> sb.append(\"  \").append(en).append(\"\\n\"));
        return sb.toString();
    }

    public String transcriptString() {
        StringBuilder sb = new StringBuilder();
        sb.append(\"Transcript for \").append(fullName).append(\"\\n\");
        double totalPoints = 0; int totalCredits = 0;
        for (Enrollment e : enrollments.values()) {
            sb.append(e).append(\"\\n\");
            if (e.getGrade()!=null) {
                totalPoints += e.getGrade().getPoints() * e.getCourse().getCredits();
                totalCredits += e.getCourse().getCredits();
            }
        }
        double gpa = totalCredits==0?0: totalPoints / totalCredits;
        sb.append(String.format(\"GPA: %.2f\", gpa));
        return sb.toString();
    }

    @Override public String toString() { return String.format(\"Student[%s,%s]\", id, fullName); }

    public static class Builder {
        private String regNo, fullName, email;
        public Builder regNo(String r){this.regNo=r;return this;}
        public Builder fullName(String n){this.fullName=n;return this;}
        public Builder email(String e){this.email=e;return this;}
        public Student build(){ return new Student(this); }
    }
}