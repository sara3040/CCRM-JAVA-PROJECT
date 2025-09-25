package edu.smcrm.io;

import edu.smcrm.service.*;
import edu.smcrm.domain.*;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class ImportExportService {
    public static void importSample(Path studentsCsv, Path coursesCsv, StudentService ss, CourseService cs) {
        try {
            Files.lines(studentsCsv).skip(1).map(l->l.split(\",\")).forEach(parts->{
                Student s = new Student.Builder().regNo(parts[0]).fullName(parts[1]).email(parts[2]).build();
                ss.addStudent(s);
            });
            Files.lines(coursesCsv).skip(1).map(l->l.split(\",\")).forEach(parts->{
                Course c = new Course.Builder().code(parts[0]).title(parts[1]).credits(Integer.parseInt(parts[2])).build();
                cs.addCourse(c);
            });
            System.out.println(\"Imported sample data.\");
        } catch (IOException e) { System.out.println(\"Import failed: \" + e.getMessage()); }
    }

    public static void exportAll(Path outDir, StudentService ss, CourseService cs, edu.smcrm.service.EnrollmentService es) {
        try {
            Files.createDirectories(outDir);
            Path sfile = outDir.resolve(\"students_export.csv\");
            Path cfile = outDir.resolve(\"courses_export.csv\");
            Files.write(sfile, ss.listStudents().stream().map(s->String.join(\",\", s.getRegNo(), s.getFullName(), s.getEmail())).collect(Collectors.toList()));
            Files.write(cfile, cs.listCourses().stream().map(c->String.join(\",\", c.getCode(), c.getTitle(), String.valueOf(c.getCredits()))).collect(Collectors.toList()));
            System.out.println(\"Exported to \" + outDir.toAbsolutePath());
        } catch (Exception e) { System.out.println(\"Export failed: \" + e.getMessage()); }
    }

    public static void backupExports(Path exportsDir) {
        try {
            if (!Files.exists(exportsDir)) { System.out.println(\"No exports to backup\"); return; }
            String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern(\"yyyyMMdd_HHmmss\"));
            Path backup = exportsDir.resolveSibling(\"backup_\" + ts);
            Files.createDirectories(backup);
            Files.walk(exportsDir).filter(Files::isRegularFile).forEach(p->{
                try { Files.copy(p, backup.resolve(p.getFileName()), StandardCopyOption.REPLACE_EXISTING); } catch (Exception ex) {}
            });
            System.out.println(\"Backup created: \" + backup.toAbsolutePath());
            // recursive size calc (demonstration idea): print total bytes
            long total = Files.walk(backup).filter(Files::isRegularFile).mapToLong(p->{ try { return Files.size(p); } catch(Exception e){ return 0L; }}).sum();
            System.out.println(\"Total backup size bytes: \" + total);
        } catch (Exception e) { System.out.println(\"Backup failed: \" + e.getMessage()); }
    }
}