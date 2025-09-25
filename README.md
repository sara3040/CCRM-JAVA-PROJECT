[README.md](https://github.com/user-attachments/files/22542997/README.md)
# Sara Mollick Course & Records Manager (SMCRM) - Java SE Console Skeleton

This repository contains a compact, runnable Java project skeleton fulfilling the SMCRM project requirements (structure + sample implementations). It is intended as a starting **working model** that demonstrates the required concepts: OOP, enums, builders, singleton pattern placeholder, NIO.2 usage, Streams, Date/Time API, custom exceptions (to be added), and CLI flow.

## How to run
1. Install JDK 17+ and set JAVA_HOME.
2. From project root:
   ```bash
   javac -d out src/edu/smcrm/cli/Main.java src/edu/smcrm/domain/*.java src/edu/smcrm/service/*.java src/edu/smcrm/io/*.java src/edu/smcrm/util/*.java
   java -cp out edu.smcrm.cli.Main
   ```
3. Use the menu to import sample data (`4`), export (`5`) and backup (`6`).

## What is included
- `src/edu/smcrm/domain` - Person, Student (Builder), Course (Builder), Enrollment, enums (Semester/Grade)
- `src/edu/smcrm/service` - Service interfaces and simple in-memory implementations
- `src/edu/smcrm/io` - ImportExportService demonstrating NIO.2 import/export/backup and recursive size calculation
- `src/edu/smcrm/cli` - Main CLI class with menu-driven operations
- `testdata` - sample CSV files for quick import
- `README.md` - this file

## Notes / Next steps
- Add custom exception classes, assertions, more design patterns (Singleton for config), nested classes, anonymous inner class example, and unit tests.
- Add more robust CSV parsers and JSON export (Gson/Jackson).
- Create screenshots and record a short demo video for submission.
