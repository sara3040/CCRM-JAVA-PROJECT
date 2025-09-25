package edu.smcrm.cli;

import edu.smcrm.service.*;
import edu.smcrm.domain.*;
import edu.smcrm.io.ImportExportService;

import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final StudentService studentService = new StudentServiceImpl();
    private static final CourseService courseService = new CourseServiceImpl();
    private static final EnrollmentService enrollmentService = new EnrollmentServiceImpl(studentService, courseService);

    public static void main(String[] args) {
        System.out.println("=== Sara Mollick Course & Records Manager (SMCRM) ===");
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println(\"\\nMenu:\\n1- Manage Students\\n2- Manage Courses\\n3- Enrollment & Grades\\n4- Import Sample Data\\n5- Export Data\\n6- Backup Exports\\n0- Exit\");
            System.out.print(\"Choose: \");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case \"1\": manageStudents(sc); break;
                case \"2\": manageCourses(sc); break;
                case \"3\": manageEnrollment(sc); break;
                case \"4\": ImportExportService.importSample(Paths.get(\"testdata/students.csv\"), Paths.get(\"testdata/courses.csv\"), studentService, courseService); break;
                case \"5\": ImportExportService.exportAll(Paths.get(\"exports\"), studentService, courseService, enrollmentService); break;
                case \"6\": ImportExportService.backupExports(Paths.get(\"exports\")); break;
                case \"0\": running = false; break;
                default: System.out.println(\"Invalid choice\"); break;
            }
        }
        System.out.println(\"Exiting. Goodbye!\") ;
        sc.close();
    }

    private static void manageStudents(Scanner sc) {
        System.out.println(\"Student Menu: a-add, l-list, p-print profile, b-back\");
        String c = sc.nextLine().trim();
        switch (c) {
            case \"a\":
                System.out.print(\"RegNo: \"); String reg = sc.nextLine();
                System.out.print(\"Full name: \"); String name = sc.nextLine();
                System.out.print(\"Email: \"); String email = sc.nextLine();
                Student s = new Student.Builder().regNo(reg).fullName(name).email(email).build();
                studentService.addStudent(s);
                System.out.println(\"Added: \" + s);
                break;
            case \"l\":
                studentService.listStudents().forEach(System.out::println);
                break;
            case \"p\":
                System.out.print(\"Student id: \"); String id = sc.nextLine();
                Student stu = studentService.findById(id);
                if (stu==null) System.out.println(\"Not found\");
                else System.out.println(stu.profileString());
                break;
            default: break;
        }
    }

    private static void manageCourses(Scanner sc) {
        System.out.println(\"Course Menu: a-add, l-list, b-back\");
        String c = sc.nextLine().trim();
        switch (c) {
            case \"a\":
                System.out.print(\"Code: \"); String code = sc.nextLine();
                System.out.print(\"Title: \"); String t = sc.nextLine();
                System.out.print(\"Credits: \"); int cr = Integer.parseInt(sc.nextLine());
                Course course = new Course.Builder().code(code).title(t).credits(cr).build();
                courseService.addCourse(course);
                System.out.println(\"Added: \" + course);
                break;
            case \"l\":
                courseService.listCourses().forEach(System.out::println);
                break;
            default: break;
        }
    }

    private static void manageEnrollment(Scanner sc) {
        System.out.println(\"Enrollment Menu: e-enroll, u-unenroll, g-record grade, t-transcript, b-back\");
        String c = sc.nextLine().trim();
        switch (c) {
            case \"e\":
                System.out.print(\"Student id: \"); String sid = sc.nextLine();
                System.out.print(\"Course code: \"); String ccode = sc.nextLine();
                try { enrollmentService.enroll(sid, ccode); System.out.println(\"Enrolled\"); }
                catch (Exception ex) { System.out.println(\"Error: \" + ex.getMessage()); }
                break;
            case \"u\":
                System.out.print(\"Student id: \"); String sid2 = sc.nextLine();
                System.out.print(\"Course code: \"); String ccode2 = sc.nextLine();
                enrollmentService.unenroll(sid2, ccode2);
                System.out.println(\"Unenrolled\"); break;
            case \"g\":
                System.out.print(\"Student id: \"); String sid3 = sc.nextLine();
                System.out.print(\"Course code: \"); String ccode3 = sc.nextLine();
                System.out.print(\"Marks (0-100): \"); double marks = Double.parseDouble(sc.nextLine());
                enrollmentService.recordMarks(sid3, ccode3, marks);
                System.out.println(\"Recorded\"); break;
            case \"t\":
                System.out.print(\"Student id: \"); String sid4 = sc.nextLine();
                Student s = studentService.findById(sid4);
                if (s==null) System.out.println(\"Not found\"); else System.out.println(s.transcriptString());
                break;
            default: break;
        }
    }
}