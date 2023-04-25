/*
    COP 3330 Final Project
    Alexander Lokhanov, Bobby
*/


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

class Student extends Person {
    private ArrayList<Integer> lectures;
    private ArrayList<Integer> labs;

    public Student(String name, int ucfID, String type) {
        super(name, ucfID, type);
        this.lectures = new ArrayList<>();
        this.labs = new ArrayList<>();
    }

    public void addLecture(int crn) {
        lectures.add(crn);
    }

    public void addLab(int crn) {
        labs.add(crn);
    }

}


public class Main {
    
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

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

        while(option!=7){
            option = sc.nextInt();
            switch(option){
                case 1: 
                    /* 1- Add a new faculty to the schedule.
                    (This requires assigning TAs to the labs when applicable)
                    */
                    System.out.println("option 1 selected.");
                    break;
                case 2:
                    /* 2- Enroll a student to a lecture 
                    (and to one of its labs if applicable)
                    */
                    System.out.println("option 2 selected.");
                    break;
                case 3:
                    /* 3- Print the Schedule of a faculty.
                    */
                    System.out.println("option 3 selected.");
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
            }
        }
    }
}

