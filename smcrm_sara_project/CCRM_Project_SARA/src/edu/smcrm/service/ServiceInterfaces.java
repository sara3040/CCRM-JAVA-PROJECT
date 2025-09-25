package edu.smcrm.service;

import edu.smcrm.domain.Student;
import edu.smcrm.domain.Course;
import edu.smcrm.domain.Enrollment;
import java.util.List;

public interface StudentService {
    void addStudent(Student s);
    List<Student> listStudents();
    Student findById(String id);
}

public interface CourseService {
    void addCourse(Course c);
    List<Course> listCourses();
    Course findByCode(String code);
}

public interface EnrollmentService {
    void enroll(String studentId, String courseCode) throws Exception;
    void unenroll(String studentId, String courseCode);
    void recordMarks(String studentId, String courseCode, double marks);
}