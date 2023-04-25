/*
    COP 3330 Final Project
    Alexander Lokhanov, Bobby
*/


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


abstract class Person {
    protected String name;
    protected int ucfID;
    protected String type;

    public Person(String name, int ucfID, String type) {
        this.name = name;
        this.ucfID = ucfID;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getUcfID() {
        return ucfID;
    }

    public String getType() {
        return type;
    }
}

abstract class People {
    protected ArrayList<Person> people;

    public People() {
        people = new ArrayList<Person>();
    }

    public void addPerson(Person p) {
        people.add(p);
    }

    public void removePerson(Person p) {
        people.remove(p);
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public People(String name, int ucfID, String type) {
        this.name = name;
        this.ucfID = ucfID;
        this.type = type;
    }
}

class UniversityPeople extends People {
    public Faculty findFacultyByID(int ucfID) {
        for (Person person : people) {
            if (person instanceof Faculty && person.getUcfID() == ucfID) {
                return (Faculty) person;
            }
        }
        return null;
    }

}


// Faculty Inherit Person 
class Faculty extends Person {
    private String rank;
    private String officeLocation;
    private ArrayList<Integer> lectures;

    public Faculty(String name, int ucfID, String rank, String officeLocation) {
        super(name, ucfID, "Faculty");
        this.rank = rank;
        this.officeLocation = officeLocation;
        this.lectures = new ArrayList<>();
    }

    public void addLecture(int crn) {
        lectures.add(crn);
    }

    public ArrayList<Integer> getLectures() {
    return lectures;
}


}

class TA extends Person {
    private Faculty advisor;
    private String degree;
    private ArrayList<Integer> labs;

    public TA(String name, int ucfID, Faculty advisor, String degree) {
        super(name, ucfID, "TA");
        this.advisor = advisor;
        this.degree = degree;
        this.labs = new ArrayList<>();
    }

    public void addLab(int crn) {
        labs.add(crn);
    }

}

class Student extends People {
    private ArrayList<Integer> lectures;
    private ArrayList<Integer> labs;

    /*
    public Student(String name, int ucfID, String type) {
        super(name, ucfID, type);
        this.lectures = new ArrayList<>();
        this.labs = new ArrayList<>();
    }
    */

    public Student(String ucfID, String name, ArrayList<Integer> lectures) {
    super(name, ucfID, "Student");
    this.lectures = lectures;
    this.labs = new ArrayList<>();
}


    public void addLecture(int crn) {
        lectures.add(crn);
    }

    public void addLab(int crn) {
        labs.add(crn);
    }

}

class Lab {
    // Fields
    private int crn;
    private String roomNumber;

    // Constructor
    public Lab(int crn, String roomNumber) {
        this.crn = crn;
        this.roomNumber = roomNumber;
    }

    // Getters
    public int getCRN() {
        return crn;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}

class Lecture {
    // Fields
    private int crn;
    private String prefix;
    private String title;
    private String type;
    private String buildingCode;
    private String roomNumber;
    private boolean isOnline;
    private boolean hasLabs;
    private ArrayList<Lab> labs;

    public boolean requiresLab() {
        return !labs.isEmpty();
    }
    

    // Constructor
    // Lecture Is Online Online
    public Lecture(int crn, String prefix, String title, String type) {
        this(crn, prefix, title, type, null, null, false);
        this.isOnline = true;
    }

    // Master Constructor
    public Lecture(int crn, String prefix, String title, String type, String buildingCode, String roomNumber,
            boolean hasLabs) {
        this.crn = crn;
        this.prefix = prefix;
        this.title = title;
        this.type = type;
        this.buildingCode = buildingCode;
        this.roomNumber = roomNumber;
        this.hasLabs = hasLabs;
        // If Lecture Has Labs
        if (this.hasLabs) {
            labs = new ArrayList<Lab>();
        }
    }

    // Add A Lab
    public void addLab(int crn, String roomNumber) {
        labs.add(new Lab(crn, roomNumber));
    }

    // Returns If The Lecture Is Online
    public boolean isOnline() {
        return isOnline;
    }

    // Returns If Lecture Has Labs
    public boolean hasLabs() {
        return hasLabs;
    }

    // Returns Room Number
    public String getRoomNumber() {
        return roomNumber;
    }

    // Returns All Labs
    public ArrayList<Integer> getLabs() {
        ArrayList<Integer> labCRNs = new ArrayList<>();
        for (Lab lab : labs) {
            labCRNs.add(lab.getCRN());
        }
        return labCRNs;
    }

    // Returns CRN
    public int getCRN() {
        return crn;
    }

    // To String
    @Override
    public String toString() {
        if (isOnline()) {
            return String.format("%d, %s, %s, %s, Online",
                    crn, prefix, title, type);
        } else {
            return String.format("%d, %s, %s, %s, %s, %s, %s",
                    crn, prefix, title, type, buildingCode, roomNumber, hasLabs() ? "Yes" : "No");
        }
    }
}

public class Main {

    public static ArrayList<Lecture> readLectures(String fileName) {
        // ArrayList To Hold Lecture
        ArrayList<Lecture> lectures = new ArrayList<Lecture>();
        ArrayList<People> peopleList = new ArrayList<>();

        File inputFile = new File(fileName);
        boolean readLabs = false;
        // Open The File For Reading
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            // Read File Line By Line
            while ((line = br.readLine()) != null) {
                // Split The Line
                String[] fields = line.trim().split(",");

                if (readLabs) {
                    if (fields.length != 2) {
                        readLabs = false;
                    } else {
                        // Get Lab CRN and Room Number
                        int crn = Integer.parseInt(fields[0].trim());
                        String roomNumber = fields[1].trim();
                        // Add Lab To Last Lecture
                        lectures.get(lectures.size() - 1).addLab(crn, roomNumber);
                        continue;
                    }
                }
                // Get CRN, Prefix, Title, Type
                int crn = Integer.parseInt(fields[0].trim());
                String prefix = fields[1].trim();
                String title = fields[2].trim();
                String type = fields[3].trim();
                // Check If Lecture is Online
                if (fields[4].trim().equalsIgnoreCase("ONLINE")) {
                    // Create A New Online Lecture
                    lectures.add(new Lecture(crn, prefix, title, type));
                } else {
                    // Lecture is Not Online
                    // Get Building Number

                    String buildingCode = fields[4].trim();
                    String roomNumber = fields[5].trim();
                    boolean hasLabs = false;

                    // Check If Lecture Has Labs
                    if (fields[6].trim().equalsIgnoreCase("YES")) {
                        // Has Labs
                        hasLabs = true;
                        readLabs = true;
                    }
                    // Add Lectrue
                    lectures.add(new Lecture(crn, prefix, title, type, buildingCode, roomNumber, hasLabs));
                }
            }
        } catch (IOException | IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        return lectures;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UniversityPeople people = new UniversityPeople();
        ArrayList<Lecture> lectures = readLectures("lec.txt");
        ArrayList<People> peopleList = new ArrayList<>();

        Map<Integer, Faculty> lectureFacultyMap = new HashMap<>();
        // Find And Print Online Courses
        int numOnline = 0;
        for (Lecture lecture : lectures) {
            if (lecture.isOnline()) {
                numOnline++;
            }
        }

        //variable initializtion
        int option = 0;
        System.out.println("\nChoose an option below:");
        System.out.println("\t1- Add a new faculty to the schedule.");
        System.out.println("\t2- Enroll a student to a lecture.");
        System.out.println("\t3- Print the Schedule of a faculty.");
        System.out.println("\t4- Print the schedule of an TA.");
        System.out.println("\t5- Print the schedule of a student.");
        System.out.println("\t6- Delete a scheduled lecture.");
        System.out.println("\t7- Exit Program.");
        System.out.println("\t69- Look Inside.");

        while(option!=7){
            option = sc.nextInt();
            switch(option){
                case 1: 
                    /* 1- Add a new faculty to the schedule.
                    (This requires assigning TAs to the labs when applicable)
                    */
                    System.out.println("Add a new faculty to the schedule:");
                    System.out.println("Enter UCF id:");
                    int ucfID = sc.nextInt();
                    sc.nextLine(); // Consume the newline character left by nextInt()
                    System.out.println("Enter name:");
                    String name = sc.nextLine();
                    System.out.println("Enter rank:");
                    String rank = sc.nextLine();
                    System.out.println("Enter office location:");
                    String officeLocation = sc.nextLine();
                    System.out.println("How many lectures to assign to this faculty?");
                    int numLectures = sc.nextInt();

                    // Create the new Faculty object
                    Faculty newFaculty = new Faculty(name, ucfID, rank, officeLocation);
                    
                    // Assign lectures to the new Faculty
                    for (int i = 0; i < numLectures; i++) {
                        System.out.printf("Enter CRN for lecture %d: ", i + 1);
                        int crn = sc.nextInt();
                        // Find the lecture with this CRN and add it to the Faculty's lectures
                        for (Lecture lecture : lectures) {
                            if (lecture.getCRN() == crn) {
                                newFaculty.addLecture(crn);
                                // Add the relationship between the lecture's CRN and the faculty member
                                lectureFacultyMap.put(crn, newFaculty);
                                break;
                            }
                        }
                    }

                    
                    // Add the new Faculty to the people list
                    people.addPerson(newFaculty);
                    break;

                case 2:
                    /* 2- Enroll a student to a lecture 
                    (and to one of its labs if applicable)
                    */
                    System.out.println("Enroll a student to a lecture.");
                    System.out.println("Enter UCFid: ");
                    int ucfid2 = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter name: ");
                    String name2 = sc.nextLine();
                    System.out.println("Enter the CRNs of the lectures (0 if no lectures): ");
                    String[] inputCRNs = sc.nextLine().split(" ");
                    ArrayList<Integer> studentLectures = new ArrayList<>();

                    for (String crn : inputCRNs) {
                        int crnInt = Integer.parseInt(crn);
                        if (crnInt != 0) {
                            studentLectures.add(crnInt);
                        }
                    }

                    Student newStudent = new Student(String.valueOf(ucfid2), name2, studentLectures);

                    peopleList.add(newStudent);

                    Random random = new Random();
                    for (int crn : studentLectures) {
                        for (Lecture lecture : lectures) {
                            if (lecture.getCRN() == crn) {
                                if (lecture.requiresLab()) { // Make sure you have a requiresLab() method in the Lecture class
                                    ArrayList<Integer> labs = lecture.getLabs(); // This should return ArrayList<Integer> with lab CRNs
                                    int randomLabIndex = random.nextInt(labs.size());
                                    int randomLabCRN = labs.get(randomLabIndex);
                                    newStudent.addLab(randomLabCRN);
                                    System.out.printf("Student with UCFid %d has been enrolled in lab with CRN %d.\n", ucfid2, randomLabCRN);
                                }
                                break;
                            }
                        }
                    }

                    break;

                    break;
                case 3:
                    /* 3- Print the Schedule of a faculty.
                    */
                    System.out.println("Enter the UCF ID of the faculty:");
                    int facultyUcfID = sc.nextInt();
                    Faculty faculty = people.findFacultyByID(facultyUcfID);

                    if (faculty != null) {
                        System.out.println("Schedule for faculty " + faculty.getName() + " (UCF ID: " + faculty.getUcfID() + "):");
                        ArrayList<Integer> facultyLectures = faculty.getLectures();
                        for (int crn : facultyLectures) {
                            for (Lecture lecture : lectures) {
                                if (lecture.getCRN() == crn) {
                                    System.out.println(lecture.toString());
                                }
                            }
                        }
                    } else {
                        System.out.println("No faculty found with UCF ID: " + facultyUcfID);
                    }
                    break;

                case 4:
                    /* 4- Print the schedule of an TA.
                    */
                    System.out.println("option 4 selected.");
                    break;
                case 5:
                    /* 5- Print the schedule of a student.
                    */
                    System.out.println("option 5 selected.");
                    break;
                case 6:
                    /* 6- Delete a scheduled lecture
                    (Deleting a lecture requires deleting its labs and 
                    updating any student’s schedule accordingly)
                    */
                    System.out.println("option 6 selected.");
                    break;
                case 7:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                case 8:
                    /*
                    System.out.println("Enter the CRN of the lecture: ");
                    int crn = sc.nextInt();
                    System.out.printf("Labs tied to lecture with CRN %d:\n", crn);

                    boolean foundLecture = false;
                    for (Lecture lecture : lectures) {
                        if (lecture.getCRN() == crn) {
                            foundLecture = true;
                            ArrayList<Lab> lectureLabs = lecture.getLabs();
                            if (lectureLabs.isEmpty()) {
                                System.out.println("No labs are tied to this lecture.");
                            } else {
                                for (Lab lab : lectureLabs) {
                                    System.out.println(lab.toString());
                                }
                            }
                            break;
                        }
                    }

                    if (!foundLecture) {
                        System.out.println("Lecture with the given CRN not found.");
                    }
                    */
                    break;
                case 69:
                    System.out.println("Lectures:\n");
                    // Print out all of the lectures and their labs
                    for (Lecture lecture : lectures) {
                        System.out.println(lecture.toString());
                        //System.out.println("|");
                        // Check if the lecture has labs
                        if (lecture.hasLabs()) {
                            // Get the labs for the lecture
                            ArrayList<Lab> labs = lecture.getLabs();
                            // Print out the CRN and room number for each lab
                            for (Lab lab : labs) {
                                System.out.println("Lab CRN: " + lab.getCRN() + ", Room Number: " + lab.getRoomNumber());
                            }
                        }
                    }
                    break;
            }
        }
    }
}

