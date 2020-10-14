package studentgrades;

import java.util.Scanner;
import java.util.Arrays;
import java.text.MessageFormat;
/**
 * @author Jacob Hinkebein
 */
public class StudentGrades {

    static Scanner sc = new Scanner(System.in);
    static int lgradetot[] = {0, 0, 0, 0, 0}; //For extra credit
    public static void main(String[] args) {
       String sid;
       System.out.println("Welcome to the grading program.\n\nPlease enter student information:");
       sid = getStudentID();
       while(!"quit".equals(sid)){ //Have to do this instead of sid != "quit" or it doesn't work
           calcGrade(sid);
           sid = getStudentID();
       }
        String lgrades[] = {"A", "B", "C", "D", "F"};
        System.out.println("There were a total of: ");
        for (int i = 0; i < lgrades.length; i++) {
            String out = MessageFormat.format("{0} {1}‘s", lgradetot[i], lgrades[i]);
            System.out.println(out);
        }
        
        System.out.println("Thanks for using the grade calculator!");
    }
    static String getStudentID(){
        String id = ""; //string.empty 
        boolean goodval = false;
        do {
            System.out.println("Student ID (enter ‘quit’ to exit): ");
            id = sc.next();
            if ("quit".equals(id.toLowerCase())) {   
                goodval = true;
            } else {
                if (!"A".equals(id.substring(0,1))) {
                    System.out.println("Error: student number must begin with an ‘A’");
                    sc.nextLine();
                    id = "";
                } else {
                    if (id.length() > 9) {
                        System.out.println("Error: student number must have 8 digits after the ‘A’");
                        sc.nextLine();
                        id = "";
                    } else {
                        try{
                            long snum;
                            snum = Long.parseLong(id.substring(1));
                            if (snum == 0) {
                                System.out.println("Error: student number cannot be all zeros after the ‘A’");
                                sc.nextLine();
                                id = "";
                            } else {
                                goodval = true;
                            }
                            
                        } catch (NumberFormatException e){
                            System.out.println("Error: student number must have 8 digits after the ‘A’");
                            sc.nextLine();
                            id = "";
                        }
                    }
                }   
            }
        } while (!goodval);
     return id;   
    }
    
    static void calcGrade (String sid){
        double q1, q2, q3, q4, q5, qm, mt, pr, fe, qavg, cavg;
        String lgrade;
        
        q1 = getScore("Quiz 1");
        q2 = getScore("Quiz 2");
        q3 = getScore("Quiz 3");
        q4 = getScore("Quiz 4");
        q5 = getScore("Quiz 5");
        qm = getScore("Quiz Make-Up");
        mt = getScore("Mid-Term Exam");
        pr = getScore("Problems");
        
        double q[] = {q1, q2, q3, q4, q5, qm };
        Arrays.sort(q);
        qavg = Math.round((q1 + q2 + q3 + q4)/4.0);
        
        if (Math.round(qavg+mt+pr) >= 90) {
            cavg = (qavg + mt + pr)/3.0;
            lgrade = "A";
            lgradetot[0]++;
        } else {
            fe = getScore("Final Exam");
            cavg = (qavg*.5) + (mt*.15) + (pr*.1) + (fe*.25);
            if (Math.round(cavg) >= 90) {
                lgrade = "A";
                lgradetot[0]++;
            } else if (Math.round(cavg) <= 89 && Math.round(cavg) >= 80) {
                lgrade = "B";
                lgradetot[1]++;
            } else if (Math.round(cavg) <= 79 && Math.round(cavg) >= 70) {
                lgrade = "C";
                lgradetot[2]++;
            }else if (Math.round(cavg) <= 69 && Math.round(cavg) >= 60) {
                lgrade = "D";
                lgradetot[3]++;
            } else{
                lgrade = "F";
                lgradetot[4]++;
            }
        }
        String out = MessageFormat.format("Student #{0} earned a quiz average of {1}, a course average of"
                +" {2}, and a letter grade of {3}.", sid, qavg, cavg, lgrade);
        System.out.println(out);
    }
    static double getScore(String scorename) {
        double s = 0;
        do {
            try{
                System.out.println(scorename + " score: ");
                s = sc.nextDouble();
                if (s < 0 || s > 125) {
                    System.out.println("Error: " + scorename + " score is out of bounds: 0-125 only. Please re-enter.");
                    s = -1;
                }
            } catch (Exception e) { //Bug with NumberFormatException, crashed program if a letter was entered
                //odd since it worked in the studentID method
                System.out.println("Error: " + scorename +  " score must be numeric. Please re-enter");
                sc.nextLine();
                s = -1;
            }
        } while (s < 0 || s > 125);
        return s;
    }
}
