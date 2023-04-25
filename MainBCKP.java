import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Course {
    private int crn;
    private String prefix;
    private String title;
    private String location;
    private String modality;

    // Constructor
    public Course(int crn, String prefix, String title, String location, String modality) {
        this.crn = crn;
        this.prefix = prefix;
        this.title = title;
        this.location = location;
        this.modality = modality;
    }

    // Getters and setters
    public int getCrn() {
        return crn;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getModality() {
        return modality;
    }
}

class Lecture extends Course {
    private boolean isGraduate;
    private String audience;


    public String getAudience() {
    return audience;
    }

    public int getNumber() {
    return super.getNumber();
    }

    // Constructor
    public Lecture(int crn, String prefix, String title, String location, String modality, boolean isGraduate) {
        super(crn, prefix, title, location, modality);
        this.isGraduate = isGraduate;
    }

    // Getter
    public boolean isGraduate() {
        return isGraduate;
    }
}

class Lab extends Course {
    // Constructor
    public Lab(int crn, String prefix, String title, String location, String modality) {
        super(crn, prefix, title, location, modality);
    }
}

abstract class Person {
    private String name;
    private int ucfId;

    // Constructor
    public Person(String name, int ucfId) {
        this.name = name;
        this.ucfId = ucfId;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public int getUcfId() {
        return ucfId;
    }
}

class Faculty extends Person {
    private String rank;
    private String officeLocation;
    private List<Lecture> lecturesTaught;

    // Constructor
    public Faculty(String name, int ucfId, String rank, String officeLocation, List<Lecture> lecturesTaught) {
        super(name, ucfId);
        this.rank = rank;
        this.officeLocation = officeLocation;
        this.lecturesTaught = lecturesTaught;
    }

    // Getters
    public String getRank() {
        return rank;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public List<Lecture> getLecturesTaught() {
        return lecturesTaught;
    }
}

class TA extends Person {
    private Faculty advisor;
    private String expectedDegree;
    private List<Lab> labsSupervised;
    private List<Course> coursesTaken;

    // Constructor
    public TA(String name, int ucfId, Faculty advisor, String expectedDegree, List<Lab> labsSupervised, List<Course> coursesTaken) {
        super(name, ucfId);
        this.advisor = advisor;
        this.expectedDegree = expectedDegree;
        this.labsSupervised = labsSupervised;
        this.coursesTaken = coursesTaken;
    }

    // Getters
   
    public Faculty getAdvisor() {
        return advisor;
    }

    public String getExpectedDegree() {
        return expectedDegree;
    }

    public List<Lab> getLabsSupervised() {
        return labsSupervised;
    }

    public List<Course> getCoursesTaken() {
        return coursesTaken;
    }
}

class Student extends Person {
    private List<Lecture> lecturesEnrolled;
    private List<Lab> labsEnrolled;
    // Constructor
    public Student(String name, int ucfId) {
        super(name, ucfId);
        this.lecturesEnrolled = new ArrayList<>();
        this.labsEnrolled = new ArrayList<>();
    }

    // Getters
    public List<Lecture> getLecturesEnrolled() {
        return lecturesEnrolled;
    }

    public List<Lab> getLabsEnrolled() {
        return labsEnrolled;
    }
}

class Schedule {
    private List<Faculty> faculties;
    private List<TA> tas;
    private List<Student> students;
    private List<Lecture> lectures;
    private List<Lab> labs;
    // Constructor
public Schedule() {
    this.faculties = new ArrayList<>();
    this.tas = new ArrayList<>();
    this.students = new ArrayList<>();
    this.lectures = new ArrayList<>();
    this.labs = new ArrayList<>();
}

// Methods to add entities to the schedule
public void addLecture() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the lecture prefix: ");
    String prefix = scanner.nextLine();
    System.out.print("Enter the lecture number: ");
    int number = scanner.nextInt();
    scanner.nextLine(); // consume the newline character
    System.out.print("Enter the lecture title: ");
    String title = scanner.nextLine();
    System.out.print("Enter the lecture CRN: ");
    int crn = scanner.nextInt();
    scanner.nextLine(); // consume the newline character
    System.out.print("Enter the lecture location: ");
    String location = scanner.nextLine();
    System.out.print("Enter the lecture modality: ");
    String modality = scanner.nextLine();
    System.out.print("Enter the lecture audience: ");
    String audience = scanner.nextLine();

    Lecture lecture = new Lecture(prefix, number, title, crn, location, modality, audience);
    lectures.add(lecture);

    System.out.println("Lecture added to the schedule!");
}

public void addLab() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter UCF id: ");
    int ucfId = scanner.nextInt();
    System.out.print("Enter the crns of the lectures: ");
    int crn = scanner.nextInt();
    scanner.nextLine(); // consume the newline character
    System.out.print("Enter the lab location: ");
    String location = scanner.nextLine();

    Lab lab = new Lab(crn, location);
    labs.add(lab);

    System.out.println("Lab added to the schedule!");
}


public void addFaculty() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter faculty name: ");
    String name = scanner.nextLine();

    System.out.print("Enter faculty UCF ID: ");
    int ucfId = scanner.nextInt();
    scanner.nextLine(); // consume the newline character

    System.out.print("Enter faculty rank: ");
    String rank = scanner.nextLine();

    System.out.print("Enter faculty office location: ");
    String officeLocation = scanner.nextLine();

    Faculty faculty = new Faculty(name, ucfId, rank, officeLocation, new ArrayList<>());
    faculties.add(faculty);

    System.out.println("Faculty added to the schedule.");
}

public void addTA() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter TA name: ");
    String name = scanner.nextLine();

    System.out.print("Enter TA UCF ID: ");
    int ucfId = scanner.nextInt();
    scanner.nextLine(); // consume the newline character

    System.out.print("Enter TA expected degree: ");
    String expectedDegree = scanner.nextLine();

    // Choose advisor
    System.out.println("Choose advisor from the following list:");

    for (Faculty faculty : faculties) {
        System.out.println(faculty.getName() + " (" + faculty.getUcfId() + ")");
    }

    System.out.print("Enter advisor UCF ID: ");
    int advisorUcfId = scanner.nextInt();
    scanner.nextLine(); // consume the newline character

    Faculty advisor = null;
    for (Faculty faculty : faculties) {
        if (faculty.getUcfId() == advisorUcfId) {
            advisor = faculty;
            break;
        }
    }

    if (advisor == null) {
        System.out.println("Advisor not found. TA not added to the schedule.");
        return;
    }

    TA ta = new TA(name, ucfId, advisor, expectedDegree, new ArrayList<>(), new ArrayList<>());
    tas.add(ta);

    System.out.println("TA added to the schedule.");
}

public void addStudent() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter student name: ");
    String name = scanner.nextLine();

    System.out.print("Enter student UCF ID: ");
    int ucfId = scanner.nextInt();
    scanner.nextLine(); // consume the newline character

    // Choose student type
    System.out.println("Choose student type:");
    System.out.println("1. Undergraduate");
    System.out.println("2. Graduate");

    System.out.print("Enter student type: ");
    int studentType = scanner.nextInt();
    scanner.nextLine(); // consume the newline character

    if (studentType != 1 && studentType != 2) {
        System.out.println("Invalid student type. Student not added to the schedule.");
        return;
    }

    Student student;
    if (studentType == 1) {
        student = new Student(name, ucfId);
    } else {
        student = new Student(name, ucfId);
    }

    students.add(student);
    System.out.println("Student added to the schedule.");
}

// Method to enroll a student in a lecture or lab
public void enrollStudent() {
    Scanner scanner = new Scanner(System.in);

    // Choose student
    System.out.println("Choose student from the following list:");

    for (Student student : students) {
        System.out.println(student.getName() + " (" + student.getUcfId() + ")");
    }

    System.out.print("Enter student UCF ID: ");
    int studentUcfId = scanner.nextInt();
    scanner.nextLine(); // consume the newline character

    Student student = null;
    for (Student s : students) {
        if (s.getUcfId() == studentUcfId) {
            student = s;
            break;
        }
    }

    if (student == null) {
        System.out.println("Student not found. Enrollment failed.");
        return;
    }

    // Choose lecture or lab
    System.out.println("Choose course to enroll the student in:");
    System.out.println("1. Lecture");
    System.out.println("2. Lab");

    System.out.print("Enter choice: ");
    int courseType = scanner.nextInt();
    scanner.nextLine(); // consume the newline character

    if (courseType != 1 && courseType != 2) {
        System.out.println("Invalid choice. Enrollment failed.");
        return;
    }

    // Choose course
    if (courseType == 1) {
        System.out.println("Choose lecture from the following list:");

        for (Lecture lecture : lectures) {
            System.out.println(lecture.getPrefix() + " " + lecture.getNumber() + " - " + lecture.getTitle()
                    + " (CRN: " + lecture.getCrn() + ")");
        }

        System.out.print("Enter CRN: ");
        int crn = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        Lecture lecture = null;
        for (Lecture l : lectures) {
            if (l.getCrn() == crn) {
                lecture = l;
                break;
            }
        }

        if (lecture == null) {
            System.out.println("Lecture not found. Enrollment failed.");
            return;
        }

        // Enroll student in lecture
        if (student.getLecturesEnrolled().contains(lecture)) {
            System.out.println("Student is already enrolled in this lecture.");
        } else {
            student.getLecturesEnrolled().add(lecture);
            System.out.println("Student enrolled in lecture " + lecture.getPrefix() + " " + lecture.getNumber());
        }
    } else {
        System.out.println("Choose lab from the following list:");

        for (Lab lab : labs) {
            System.out.println("Lab " + lab.getCrn() + " - " + lab.getLocation());
        }

        System.out.print("Enter CRN: ");
        int crn = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        Lab lab = null;
        for (Lab l : labs) {
            if (l.getCrn() == crn) {
                lab = l;
                break;
            }
        }

        if (lab == null) {
            System.out.println("Lab not found. Enrollment failed.");
            return;
        }

        // Enroll student in lab
        if (student.getLabsEnrolled().contains(lab)) {
            System.out.println("Student is already enrolled in this lab.");
        } else {
            student.getLabsEnrolled().add(lab);
            System.out.println("Student enrolled in lab " + lab.getCrn());
        }
    }
}

// Method to display the schedule of lectures and labs
public void displaySchedule() {
    System.out.println("Schedule:");
    System.out.println("Lectures:");
    for (Lecture lecture : lectures) {
        System.out.println(lecture.getPrefix() + " " + lecture.getNumber() + " - " + lecture.getTitle() +
                " (CRN: " + lecture.getCrn() + ", Location: " + lecture.getLocation() + ", Modality: " +
                lecture.getModality() + ", Audience: " + lecture.getAudience() + ")");
    }
    System.out.println("Labs:");
    for (Lab lab : labs) {
        System.out.println("Lab " + lab.getCrn() + " - " + lab.getLocation());
    }
}
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Schedule schedule = new Schedule();
            int choice = 0;
    while (choice != 6) {
        System.out.println("Main Menu");
        System.out.println("1. Add a new lecture to the schedule.");
        System.out.println("2. Add a new lab to the schedule.");
        System.out.println("3. Add a new faculty to the schedule.");
        System.out.println("4. Add a new TA to the schedule.");
        System.out.println("5. Add a new student to the schedule.");
        System.out.println("6. Enroll a student in a lecture or lab.");
        System.out.println("7. Display the schedule of lectures and labs.");
        System.out.println("8. Exit the program.");
        System.out.print("Enter your choice: ");

        choice = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        switch (choice) {
            case 1:
                schedule.addLecture();
                break;
            case 2:
                schedule.addLab();
                break;
            case 3:
                schedule.addFaculty();
                break;
            case 4:
                schedule.addTA();
                break;
            case 5:
                schedule.addStudent();
                break;
            case 6:
                schedule.enrollStudent();
                break;
            case 7:
                schedule.displaySchedule();
                break;
            case 8:
                System.out.println("Exiting the program...");
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                break;
        }
    }

    scanner.close();
}
}