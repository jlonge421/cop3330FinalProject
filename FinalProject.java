/*
    COP 3330 Final Project
    Alexander Lokhanov, Robert Dyer
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

    // public People(String name, int ucfID, String type) {
    //     this.name = name;
    //     this.ucfID = ucfID;
    //     this.type = type;
    // }
}

class UniversityPeople extends People {
    private ArrayList<Person> peopleList;

    public UniversityPeople() {
        this.peopleList = new ArrayList<>();
    }

    public Faculty findFacultyByID(int ucfID) {
        for (Person person : people) {
            if (person instanceof Faculty && person.getUcfID() == ucfID) {
                return (Faculty) person;
            }
        }
        return null;
    }

    public TA findTAByID(int ucfID) {
        for (Person person : people) {
            if (person instanceof TA && person.getUcfID() == ucfID) {
                return (TA) person;
            }
        }
        return null;
    }

    public Student findStudentByID(int ucfID) {
        for (Person person : people) {
            if (person instanceof Student && person.getUcfID() == ucfID) {
                return (Student) person;
            }
        }
        return null;
    }



    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<>();
        for (Person person : people) {
            if (person instanceof Student) {
                students.add((Student) person);
            }
        }
        return students;
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

    public Faculty(String name){
        super(name, 0000000, "TA supervisor");
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
    private ArrayList<Integer> lectures;

    public TA(String name, int ucfID, Faculty advisor, String degree) {
        super(name, ucfID, "TA");
        this.advisor = advisor;
        this.degree = degree;
        this.labs = new ArrayList<>();
        this.lectures = new ArrayList<>();
    }

    public void addLab(int crn) {
        labs.add(crn);
        System.out.println("lab "+crn+" added");
    }

    public void addLecture(int crn) {
        lectures.add(crn);
    }

    public ArrayList<Integer> getLectures() {
        return lectures;
    }

    public int getLecture(int idx) {
        ArrayList<Integer> lecturesCRNs = new ArrayList<>();
        for (int lecture : lectures) {
            lecturesCRNs.add(lecture);
        }
        int lecIdx = lecturesCRNs.get(idx); //int lecIdx = lecturesCRNs.get(idx).getCRN();
        return lecIdx;
    }


    public String getSchedule(ArrayList<Lecture> allLectures, ArrayList<Lab> allLabs) {
        StringBuilder schedule = new StringBuilder();
        schedule.append("Schedule for ").append(name).append(" (UCFID: ").append(ucfID).append("):\n");
        
        if (lectures.isEmpty() && (labs == null || labs.isEmpty())) {
            schedule.append("No classes enrolled.\n");
            schedule.append("No labs enrolled.");
        } else {
            schedule.append("Lectures:\n");
            for (int lectureCRN : lectures) {
                Lecture lecture = FinalProject.getLectureByCRN(lectureCRN, allLectures);
                if (lecture != null) {
                    schedule.append(lecture.toString()).append("\n");
                }
            }
            
            
                schedule.append("Labs:\n");
                if (labs != null && !labs.isEmpty()){
                    for (int labCRN : labs) {
                        Lab lab = FinalProject.getLabByCRN(labCRN, allLabs);
                        if (lab != null) {
                            schedule.append(lab.toString()).append("\n");
                        }
                    }
                } 
        }
        
        return schedule.toString();
    }
}

class Student extends Person {
    private ArrayList<Integer> lectures;
    private ArrayList<Integer> labs;

    /*
    public Student(String name, int ucfID, String type) {
        super(name, ucfID, type);
        this.lectures = new ArrayList<>();
        this.labs = new ArrayList<>();
    }
    */

    public Student(String name, int ucfID, ArrayList<Integer> lectures) {
        super(name, ucfID, "Student");
        this.lectures = lectures;
        this.labs = new ArrayList<>(); // Initialize the labs ArrayList
    }

    public void removeLecture(Lecture lectureToRemove) {
        lectures.remove(lectureToRemove);
        if (lectureToRemove.hasLabs()) {
            labs.removeAll(lectureToRemove.getLabs());
        }
    }

    public ArrayList<Integer> getLectures() {
        return lectures;
    }

    public int getLecture(int idx) {
        ArrayList<Integer> lecturesCRNs = new ArrayList<>();
        for (int lecture : lectures) {
            lecturesCRNs.add(lecture);
        }
        int lecIdx = lecturesCRNs.get(idx); //int lecIdx = lecturesCRNs.get(idx).getCRN();
        return lecIdx;
    }

    public String getSchedule(ArrayList<Lecture> allLectures, ArrayList<Lab> allLabs) {
        StringBuilder schedule = new StringBuilder();
        schedule.append("Schedule for ").append(name).append(" (UCFID: ").append(ucfID).append("):\n");
        
        if (lectures.isEmpty() && (labs == null || labs.isEmpty())) {
            schedule.append("No classes enrolled.");
        } else {
            schedule.append("Lectures:\n");
            for (int lectureCRN : lectures) {
                Lecture lecture = FinalProject.getLectureByCRN(lectureCRN, allLectures);
                if (lecture != null) {
                    schedule.append(lecture.toString()).append("\n");
                }
            }
            
            if (labs != null && !labs.isEmpty()) {
                schedule.append("Labs:\n");
                for (int labCRN : labs) {
                    Lab lab = FinalProject.getLabByCRN(labCRN, allLabs);
                    if (lab != null) {
                        schedule.append(lab.toString()).append("\n");
                    }
                }
            }
        }
        
        return schedule.toString();
    }




    public void addLecture(int crn) {
        lectures.add(crn);
    }

    public void addLab(int crn) {
        labs.add(crn);
    }

}

class Lab {
    private int crn;
    private String roomNumber;

    public Lab(int crn, String roomNumber) {
        this.crn = crn;
        this.roomNumber = roomNumber;
    }

    public int getCRN() {
        return crn;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public String toString() {
        return "Lab CRN: " + crn + ", Room Number: " + roomNumber;
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

    

    // Constructor
    // Lecture Is Online Online
    public Lecture(int crn, String prefix, String title, String type) {
        this(crn, prefix, title, type, null, null, false);
        this.isOnline = true;
    }

    // Master Constructor
    public Lecture(int crn, String prefix, String title, String type, String buildingCode, String roomNumber, boolean hasLabs) {
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


    public boolean requiresLab() {
    return labs != null && !labs.isEmpty();
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

    public int getLabs(int idx) {
        ArrayList<Integer> labCRNs = new ArrayList<>();
        for (Lab lab : labs) {
            labCRNs.add(lab.getCRN());
        }
        int labIdx = labCRNs.get(idx);
        return labIdx;
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



public class FinalProject {

    public static ArrayList<Lecture> readLectures(String fileName, ArrayList<Lab> allLabs) {
        ArrayList<Lecture> lectures = new ArrayList<Lecture>();
        File inputFile = new File(fileName);
        boolean readLabs = false;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.trim().split(",");

                if (readLabs) {
                    if (fields.length != 2) {
                        readLabs = false;
                    } else {
                        int crn = Integer.parseInt(fields[0].trim());
                        String roomNumber = fields[1].trim();
                        lectures.get(lectures.size() - 1).addLab(crn, roomNumber);

                        // Add the Lab object to the allLabs list
                        Lab lab = new Lab(crn, roomNumber);
                        allLabs.add(lab);
                        continue;
                    }
                }
                int crn = Integer.parseInt(fields[0].trim());
                String prefix = fields[1].trim();
                String title = fields[2].trim();
                String type = fields[3].trim();

                if (fields[4].trim().equalsIgnoreCase("ONLINE")) {
                    lectures.add(new Lecture(crn, prefix, title, type));
                } else {
                    String buildingCode = fields[4].trim();
                    String roomNumber = fields[5].trim();
                    boolean hasLabs = false;

                    if (fields[6].trim().equalsIgnoreCase("YES")) {
                        hasLabs = true;
                        readLabs = true;
                    }
                    lectures.add(new Lecture(crn, prefix, title, type, buildingCode, roomNumber, hasLabs));
                }
            }
        } catch (IOException | IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        return lectures;
    }


    public static Lecture getLectureByCRN(int crn, ArrayList<Lecture> allLectures) {
        for (Lecture lecture : allLectures) {
            if (lecture.getCRN() == crn) {
                return lecture;
            }
        }
        return null;
    }

    public static Lab getLabByCRN(int crn, ArrayList<Lab> allLabs) {
        for (Lab lab : allLabs) {
            if (lab.getCRN() == crn) {
                return lab;
            }
        }
        return null;
    }

    public static void deleteLecture(int lectureCRN, ArrayList<Lecture> allLectures, ArrayList<Lab> allLabs, UniversityPeople people) {
        // Find and remove the lecture
        Lecture lectureToRemove = null;
        for (Lecture lecture : allLectures) {
            if (lecture.getCRN() == lectureCRN) {
                lectureToRemove = lecture;
                break;
            }
        }
        if (lectureToRemove != null) {
            allLectures.remove(lectureToRemove);

            // Remove associated labs
            if (lectureToRemove.hasLabs()) {
                ArrayList<Integer> labCRNsToRemove = lectureToRemove.getLabs();
                allLabs.removeIf(lab -> labCRNsToRemove.contains(lab.getCRN()));
            }

            // Update students' schedules
            for (Student student : people.getStudents()) {
                student.removeLecture(lectureToRemove);
            }
        } else {
            System.out.println("Lecture with CRN " + lectureCRN + " not found.");
        }
    }

    //public static void addTa




    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // ArrayList<Lecture> allLectures = new ArrayList<>();
        // ArrayList<Lab> allLabs = new ArrayList<>();
        // ArrayList<People> peopleList = new ArrayList<>();
        // ArrayList<Lecture> allLectures = new ArrayList<>();
        // ArrayList<Lab> allLabs = new ArrayList<>();
        // String filePath = "lec.txt";
        // allLectures = readLectures(filePath, allLabs);

        ArrayList<Lecture> allLectures = new ArrayList<>();
        ArrayList<Lab> allLabs = new ArrayList<>();
        UniversityPeople people = new UniversityPeople();

        String filePath = "lec.txt";
        allLectures = readLectures(filePath, allLabs);

        Map<Integer, Faculty> lectureFacultyMap = new HashMap<>();
        // Find And Print Online Courses
        int numOnline = 0;
        for (Lecture lecture : allLectures) {
            if (lecture.isOnline()) {
                numOnline++;
            }
        }

        //variable initializtion
        int option = 0;
        

        while(option!=7){
            System.out.println("\nChoose an option below:");
            System.out.println("\t1- Add a new faculty to the schedule.");
            System.out.println("\t2- Enroll a student to a lecture.");
            System.out.println("\t3- Print the Schedule of a faculty.");
            System.out.println("\t4- Print the schedule of an TA.");
            System.out.println("\t5- Print the schedule of a student.");
            System.out.println("\t6- Delete a scheduled lecture.");
            System.out.println("\t7- Exit Program.");
            //System.out.println("\t69- Look Inside.");
            System.out.print("\nEnter your choice:");
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

                    // make sure an integer is entered
                    int numLectures = 0;
                    while(true){
                        try{
                            String stringIntTemp = sc.next();
                            numLectures = Integer.parseInt(stringIntTemp);
                            break;
                            
                        } catch (Exception e) {
                            System.out.println("Make sure you are entering a whole number. Try again..");
                        }
                    }

                    // Create the new Faculty object
                    Faculty newFaculty = new Faculty(name, ucfID, rank, officeLocation);
                    
                    // Assign lectures to the new Faculty
                    for (int i = 0; i < numLectures; i++) {
                        System.out.printf("Enter CRN for lecture %d: ", i + 1);
                        int crn = sc.nextInt();
                        // Find the lecture with this CRN and add it to the Faculty's lectures
                        for (Lecture lecture : allLectures) {
                            if (lecture.getCRN() == crn) {
                                newFaculty.addLecture(crn);
                                // Add hash relationship between the lecture's CRN and the faculty member
                                lectureFacultyMap.put(crn, newFaculty);
                                if(lecture.hasLabs()){
                                    int numLabs = lecture.getLabs().size();
                                    System.out.println(crn+" has "+numLabs+" lab sections:" +lecture.getLabs());
                                    for(int ixLabs=0; ixLabs<numLabs; ixLabs++){
                                        int curLab = lecture.getLabs(ixLabs);
                                        System.out.print(curLab+", ");
                                        //begin TA stuff here
                                        System.out.println("Enter the TA’s id for "+curLab+": ");
                                        int ucfIDTA = sc.nextInt();
                                        // Search for the TA's lecture's and make sure they dont match curCRN
                                        // check students for the TA's ucfID. 
                                        // if found, make sure getLectures(TA)!=curLecCRN

                                        Student studentTA = people.findStudentByID(ucfIDTA);
                                        // Good idea to print out if the TA has been found as a Student
                                        //for()
                                        System.out.println("TA’s supervisor’s name: ");
                                        String trashXchg = sc.nextLine();
                                        String advisor = sc.nextLine();
                                        Faculty supervisor = new Faculty(advisor);
                                        System.out.println("Name of TA: ");
                                        String nameTA = sc.nextLine();
                                        System.out.println("Degree Seeking: ");
                                        String degree = sc.nextLine();
                                        
                                        //construct TA after gathering all info
                                        TA newTA = new TA(nameTA, ucfIDTA, supervisor, degree);
                                        people.addPerson(newTA);
                                        for(Lecture lecture2 : allLectures){
                                            int numLecTA = newTA.getLectures().size();
                                            for (int idx = 0; idx < numLecTA; idx++){
                                                if (lecture2.getCRN() == newTA.getLecture(idx)) {

                                                    System.out.println("TA is a student in the lecture! Pick another TA...");
                                                    break;
                                                }
                                                // else, if TA is good to hire
                                                System.out.println("HEEEEREEEE!");
                                                newTA.addLab(curLab);

                                            }
                                        }

                                        


                                        

                                    }
                                    System.out.println("");
                                }

                                break;
                            }
                        }
                    }
                    // Add the new Faculty to the people list
                    people.addPerson(newFaculty);
                    System.out.println("Added "+numLectures +" lectures to UCF ID "+ ucfID);
                /*
                 // add TA to labs
                 for (size.lecture.getLabs()){
                     if(lecture.hasLabs()){
                         System.out.println(crn+" has labs: "+lecture.getLabs());
                         // ask for TA name for each of the labs.
                         // check that the TA's schedule as a student isnt in that CRN
                         System.out.println("Enter the TA’s id for "+lecture.getLabs(0)+": ");
                         int ucfIDTA = sc.nextInt();

                     }
                 }
                    */
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

                    Student newStudent = new Student(name2, ucfid2, studentLectures);

                    people.addPerson(newStudent);


                    Random random = new Random();
                    for (int crn : studentLectures) {
                        for (Lecture lecture : allLectures) {
                            if (lecture.getCRN() == crn) {
                                if (lecture.requiresLab()) { // Make sure you have a requiresLab() method in the Lecture class
                                    ArrayList<Integer> labs = lecture.getLabs(); // This should return ArrayList<Integer> with lab CRNs
                                    int randomLabIndex = random.nextInt(labs.size());
                                    int randomLabCRN = labs.get(randomLabIndex);
                                    //System.out.println("1=."+randomLabIndex+".; 2=."+randomLabCRN+".");
                                    newStudent.addLab(randomLabCRN);
                                    System.out.printf("Student with UCFid %d has been enrolled in lab with CRN %d.\n", ucfid2, randomLabCRN);
                                }
                                break;
                            }
                        }
                    }

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
                            for (Lecture lecture : allLectures) {
                                if (lecture.getCRN() == crn) {
                                    System.out.println(lecture.toString());
                                }
                            }
                        }
                    } else {
                        System.out.println("No faculty found with UCF ID: " + facultyUcfID);
                    }
                    break;

                // ...

                case 4:
                    /* 4- Print the schedule of an TA.
                    */
                    System.out.println("Enter the UCF ID of the TA:");
                    int taUcfID = sc.nextInt();
                    TA ta = people.findTAByID(taUcfID);

                    if (ta != null) {
                        System.out.println(ta.getSchedule(allLectures, allLabs));
                    } else {
                        System.out.println("No TA found with UCF ID: " + taUcfID);
                    }
                    break;

                case 5:
                    /* 5- Print the schedule of a student.
                    */
                    System.out.println("Enter the UCF ID of the student:");
                    int studentUcfID = sc.nextInt();
                    Student student = people.findStudentByID(studentUcfID);

                    if (student != null) {
                        System.out.println(student.getSchedule(allLectures,allLabs));
                    } else {
                        System.out.println("No student found with UCF ID: " + studentUcfID);
                    }

                    break;

                case 6:
                    /* 6- Delete a scheduled lecture
                    (Deleting a lecture requires deleting its labs and 
                    updating any student’s schedule accordingly)
                    */
                    System.out.println("option 6 selected.");
                    int lectureCRNToDelete = sc.nextInt();
                    deleteLecture(lectureCRNToDelete, allLectures, allLabs, people);
                    break;
                case 7:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                case 69:
                    System.out.println("Lectures:\n");
                    // Print out all of the lectures and their labs
                    for (Lecture lecture : allLectures) {
                        System.out.println(lecture.toString());
                        //System.out.println("|");
                        // Check if the lecture has labs
                        if (lecture.hasLabs()) {
                            // Get the labs for the lecture
                            ArrayList<Integer> labs = lecture.getLabs();
                            // Print out the CRN and room number for each lab
                            for (Integer labCRN : labs) {
                                System.out.println("Lab CRN: " + labCRN);
                            }
                        }
                    }
                    break;
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }
}
