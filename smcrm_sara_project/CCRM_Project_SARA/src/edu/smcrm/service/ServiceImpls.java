package edu.smcrm.service;

import edu.smcrm.domain.*;
import java.util.*;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {
    private final Map<String, Student> students = new LinkedHashMap<>();
    @Override public void addStudent(Student s){ students.put(s.getId(), s); }
    @Override public List<Student> listStudents(){ return new ArrayList<>(students.values()); }
    @Override public Student findById(String id){ return students.get(id); }
}

public class CourseServiceImpl implements CourseService {
    private final Map<String, Course> courses = new LinkedHashMap<>();
    @Override public void addCourse(Course c){ courses.put(c.getCode(), c); }
    @Override public List<Course> listCourses(){ return new ArrayList<>(courses.values()); }
    @Override public Course findByCode(String code){ return courses.get(code); }
}

public class EnrollmentServiceImpl implements EnrollmentService {
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentServiceImpl(StudentService ss, CourseService cs){ this.studentService=ss; this.courseService=cs; }

    @Override public void enroll(String studentId, String courseCode) throws Exception {
        Student s = studentService.findById(studentId);
        Course c = courseService.findByCode(courseCode);
        if (s==null) throw new Exception(\"Student not found\");
        if (c==null) throw new Exception(\"Course not found\");
        if (s.getEnrollments().stream().mapToInt(e->e.getCourse().getCredits()).sum() + c.getCredits() > 30)
            throw new Exception(\"Max credit limit exceeded\");
        Enrollment e = new Enrollment(c);
        s.addEnrollment(e);
    }

    @Override public void unenroll(String studentId, String courseCode) {
        Student s = studentService.findById(studentId);
        if (s==null) return;
        s.removeEnrollment(courseCode);
    }

    @Override public void recordMarks(String studentId, String courseCode, double marks) {
        Student s = studentService.findById(studentId);
        if (s==null) return;
        s.getEnrollments().stream().filter(en->en.getCourse().getCode().equals(courseCode)).findFirst().ifPresent(en->en.setMarks(marks));
    }
}