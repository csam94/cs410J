package edu.pdx.cs410J.csam;

import edu.pdx.cs410J.AbstractAppointmentBook;

import static jdk.nashorn.internal.objects.Global.print;

/**
 * The main class for the CS410J appointment book Project 1
 */
public class Project1 {

  /**
   * The main method for the CS410J appointment book Project 1
   * Handles command line parsing, error output, and adding an <code>Appointment</code> to an <code>AppointmentBook</code>
   *
   * @param args
   *        Command line arguments passed in by the user
   *        Should contain appointment book owner's name, appointment description, start date and time, and end date and time
   *        May also contain flags to print appointment description or README
     */
  public static void main(String[] args) {
    
    /**** Variables & Objects ****/
    
    Class c = AbstractAppointmentBook.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    AppointmentBook book = new AppointmentBook();
    Appointment appointment = new Appointment();

    int ownerInd = 0;
    int descInd = 1;
    int dateInd = 2;
    int timeInd = 3;

    int dayDigits = 1;

    int dayInd = 2;
    int yearInd = 4;
    int minInd = 2;

    int printFlag = 0;
    
    String startOrEnd = "start";

    /**** Argument parsing ****/
    
    switch(args.length) {

      //Required arguments + one option
      case (7):
        ownerInd = 1;
        descInd = 2;
        dateInd = 3;
        timeInd = 4;

        if(args[0].equals("-README")) {
          printReadme();
        } else if(args[0].equals("-print")) {
          printFlag = 1;
        } else if(!args[0].equals("-print") && !args[0].equals("-README")) {
          System.err.println("Invalid option: " + args[0] + ". Valid options are [-print|-README].");
          System.exit(1);
        }

        break;

      //Required arguments + both options
      case (8):
        ownerInd = 2;
        descInd = 3;
        dateInd = 4;
        timeInd = 5;

        if(args[0].equals("-README") && args[1].equals("-print") || args[1].equals("-README") && args[0].equals("-print")) {
          printReadme();
        } else if((args[0].equals("-print") && !args[1].equals("-README")) || (args[0].equals("-README") && !args[1].equals("-print"))) {
          System.err.println("Invalid option: " + args[1] + ". Valid options are [-print|-README]");
          System.exit(1);
        } else if((args[1].equals("-README") && !args[0].equals("-print")) || (args[1].equals("-print") && !args[0].equals("-README"))) {
          System.err.println("Invalid option: " + args[0] + ". Valid options are [-print|-README]");
          System.exit(1);
        }

        break;

      default:
        //Extraneous arguments
        if(args.length > 8) {
          System.err.println("Too many arguments/options. The correct format is: [-print|-README] <name> <description> <start date> <start time> <end date> <end time>");
          System.exit(1);
        }
        //Too few arguments
        else if(args.length < 6) {
          System.err.println("Missing command line arguments. The correct format is: [-print|-README] <name> <description> <start date> <start time> <end date> <end time>");
          System.exit(1);
        }
    }

    /**** Error handling ****/

    if(args[ownerInd].trim().equals("")) {
      System.err.println("No owner specified");
      System.exit(1);
    }

    if(args[descInd].trim().equals("")) {
      System.err.println("Description is empty");
      System.exit(1);
    }

    for(int i = 0; i < 2; ++i) {

      //Date string too long or too short
      if(args[dateInd].trim().length() < 8 || args[dateInd].trim().length() > 10) {
        System.err.println("Invalid " + startOrEnd + " date (Format is mm/dd/yyyy)");
        System.exit(1);
      }

      //Month is not represented by a number
      if(!Character.isDigit(args[dateInd].trim().charAt(0))) {
        System.err.println("Invalid " + startOrEnd + " date (Month is not represented by a number)");
        System.exit(1);
      }

      //Month is represented by 2 digits
      if(Character.isDigit(args[dateInd].trim().charAt(0)) && Character.isDigit(args[dateInd].trim().charAt(1))) {
        dayDigits = 2;

        dayInd = 3;
        yearInd = 5;

        //First digit of month is not 0 or 1
        if(Character.getNumericValue(args[dateInd].trim().charAt(0)) > 1) {
          System.err.println("Invalid " + startOrEnd + " date (First digit of month is not 0 or 1)");
          System.exit(1);
        }
        //First digit of month is 1, second digit is not 0, 1, or 2
        else if(Character.getNumericValue(args[dateInd].trim().charAt(0)) == 1 && Character.getNumericValue(args[dateInd].trim().charAt(1)) > 2) {
          System.err.println("Invalid " + startOrEnd + " date (First digit of month is 1, second digit is not 0, 1, or 2)");
          System.exit(1);
        }
        //Month is 00
        else if(Character.getNumericValue(args[dateInd].trim().charAt(0)) != 1 && Character.getNumericValue(args[dateInd].trim().charAt(1)) == 0) {
          System.err.println("Invalid " + startOrEnd + " date (Month is 00)");
          System.exit(1);
        }
        //Month is not followed by forward slash "/"
        else if(!Character.toString(args[dateInd].trim().charAt(2)).equals("/")) {
          System.err.println("Invalid " + startOrEnd + " date (Month is not followed by forward slash)");
          System.exit(1);
        }
      }
      //Month is single-digit and not followed by forward slash "/"
      else if(!Character.toString(args[dateInd].trim().charAt(1)).equals("/")) {
        System.err.println("Invalid " + startOrEnd + " date (Month is single-digit and not followed by forward slash)");
        System.exit(1);
      }

      //Day is not represented by a number
      if(!Character.isDigit(args[dateInd].trim().charAt(dayInd))) {
        System.err.println("Invalid " + startOrEnd + " date (Day is not represented by a number)");
        System.exit(1);
      }

      //Day is represented by 2 digits
      if(Character.isDigit(args[dateInd].trim().charAt(dayInd)) && Character.isDigit(args[dateInd].trim().charAt(dayInd + 1))) {
        if(dayDigits == 2) {
          yearInd = 6;
        } else {
          yearInd = 5;
        }

        //First digit of day is not 0, 1, 2, or 3
        if(Character.getNumericValue(args[dateInd].trim().charAt(dayInd)) > 3) {
          System.err.println("Invalid " + startOrEnd + " date (First digit of day is not 0, 1, 2, or 3)");
          System.exit(1);
        }
        //First digit of day is 3, second digit is not 0 or 1
        else if(Character.getNumericValue(args[dateInd].trim().charAt(dayInd)) == 3 && Character.getNumericValue(args[dateInd].trim().charAt(dayInd + 1)) > 1) {
          System.err.println("Invalid " + startOrEnd + " date (First digit of day is 3, second digit is not 0 or 1)");
          System.exit(1);
        }
        //Day is 00
        else if(Character.getNumericValue(args[dateInd].trim().charAt(dayInd)) == 0 && Character.getNumericValue(args[dateInd].trim().charAt(dayInd + 1)) == 0) {
          System.err.println("Invalid " + startOrEnd + " date (Day is 00)");
          System.exit(1);
        }
        //Day is Feb. 30
        else if(Character.getNumericValue(args[dateInd].trim().charAt(dayInd)) == 3 && Character.getNumericValue(args[dateInd].trim().charAt(dayInd + 1)) == 0
                && ((Character.getNumericValue(args[dateInd].trim().charAt(0)) == 0 && Character.getNumericValue(args[dateInd].trim().charAt(1)) == 2)
                || Character.getNumericValue(args[dateInd].trim().charAt(0)) == 2)) {
          System.err.println("Invalid " + startOrEnd + " date (There is no Feb. 30)");
          System.exit(1);
        }
        //Day is Feb. 31
        else if(Character.getNumericValue(args[dateInd].trim().charAt(dayInd)) == 3 && Character.getNumericValue(args[dateInd].trim().charAt(dayInd + 1)) == 1
                && ((Character.getNumericValue(args[dateInd].trim().charAt(0)) == 0 && Character.getNumericValue(args[dateInd].trim().charAt(1)) == 2)
                || Character.getNumericValue(args[dateInd].trim().charAt(0)) == 2)) {
          System.err.println("Invalid " + startOrEnd + " date (There is no Feb. 31)");
          System.exit(1);
        }
        //Day is Apr. 31
        else if(Character.getNumericValue(args[dateInd].trim().charAt(dayInd)) == 3 && Character.getNumericValue(args[dateInd].trim().charAt(dayInd + 1)) == 1
                && ((Character.getNumericValue(args[dateInd].trim().charAt(0)) == 0 && Character.getNumericValue(args[dateInd].trim().charAt(1)) == 4)
                || Character.getNumericValue(args[dateInd].trim().charAt(0)) == 4)) {
          System.err.println("Invalid " + startOrEnd + " date (There is no Apr. 31)");
          System.exit(1);
        }
        //Day is Jun. 31
        else if(Character.getNumericValue(args[dateInd].trim().charAt(dayInd)) == 3 && Character.getNumericValue(args[dateInd].trim().charAt(dayInd + 1)) == 1
                && ((Character.getNumericValue(args[dateInd].trim().charAt(0)) == 0 && Character.getNumericValue(args[dateInd].trim().charAt(1)) == 6)
                || Character.getNumericValue(args[dateInd].trim().charAt(0)) == 6)) {
          System.err.println("Invalid " + startOrEnd + " date (There is no Jun. 31)");
          System.exit(1);
        }
        //Day is Sep. 31
        else if(Character.getNumericValue(args[dateInd].trim().charAt(dayInd)) == 3 && Character.getNumericValue(args[dateInd].trim().charAt(dayInd + 1)) == 1
                && ((Character.getNumericValue(args[dateInd].trim().charAt(0)) == 0 && Character.getNumericValue(args[dateInd].trim().charAt(1)) == 9)
                || Character.getNumericValue(args[dateInd].trim().charAt(0)) == 9)) {
          System.err.println("Invalid " + startOrEnd + " date (There is no Sep. 31)");
          System.exit(1);
        }
        //Day is Nov. 31
        else if(Character.getNumericValue(args[dateInd].trim().charAt(dayInd)) == 3 && Character.getNumericValue(args[dateInd].trim().charAt(dayInd + 1)) == 1
                && (Character.getNumericValue(args[dateInd].trim().charAt(0)) == 1 && Character.getNumericValue(args[dateInd].trim().charAt(1)) == 1)) {
          System.err.println("Invalid " + startOrEnd + " date (There is no Nov. 31)");
          System.exit(1);
        }
        //Day is not followed by forward slash "/"
        else if(!Character.toString(args[dateInd].trim().charAt(dayInd + 2)).equals("/")) {
          System.err.println("Invalid " + startOrEnd + " date (Day is not followed by forward slash)");
          System.exit(1);
        }
      }
      //Day is single-digit and not followed by forward slash "/"
      else if(!Character.toString(args[dateInd].trim().charAt(dayInd + 1)).equals("/")) {
        System.err.println("Invalid " + startOrEnd + " date (Day is single-digit and not followed by forward slash)");
        System.exit(1);
      }

      //Year is not represented by a number
      if(!Character.isDigit(args[dateInd].trim().charAt(yearInd)) || !Character.isDigit(args[dateInd].trim().charAt(yearInd + 1))
              || !Character.isDigit(args[dateInd].trim().charAt(yearInd + 2)) || !Character.isDigit(args[dateInd].trim().charAt(yearInd + 3))) {
        System.err.println("Invalid " + startOrEnd + " date (Year is not represented by a number)");
        System.exit(1);
      }

      //Day is Feb. 29, not on a leap year
      if(Character.getNumericValue(args[dateInd].trim().charAt(dayInd)) == 2 && Character.getNumericValue(args[dateInd].trim().charAt(dayInd + 1)) == 9
              && ((Character.getNumericValue(args[dateInd].trim().charAt(0)) == 0 && Character.getNumericValue(args[dateInd].trim().charAt(1)) == 2)
              || Character.getNumericValue(args[dateInd].trim().charAt(0)) == 2)
              && Integer.parseInt(Character.toString(args[dateInd].trim().charAt(yearInd + 2)) + Character.toString(args[dateInd].trim().charAt(yearInd + 3))) % 4 != 0) {
        System.err.println("Invalid " + startOrEnd + " date (Day is Feb. 29, but year is not a leap year)");
        System.exit(1);
      }

      //Time is too long or too short
      if(args[timeInd].length() < 4 || args[timeInd].length() > 5) {
        System.err.println("Invalid " + startOrEnd + " time (Format is hh:mm)");
        System.exit(1);
      }

      //Hour is not represented by a number
      if(!Character.isDigit(args[timeInd].trim().charAt(0))) {
        System.err.println("Invalid " + startOrEnd + " time (Hour is not represented by a number)");
        System.exit(1);
      }

      //Hour is represented by 2 digits
      if(Character.isDigit(args[timeInd].trim().charAt(0)) && Character.isDigit(args[timeInd].trim().charAt(1))) {
        minInd = 3;

        //First digit of hour is not 1 or 2
        if(Character.getNumericValue(args[timeInd].trim().charAt(0)) > 2 || Character.getNumericValue(args[timeInd].trim().charAt(0)) <= 0) {
          System.err.println("Invalid " + startOrEnd + " time (First digit of hour is not 1 or 2)");
          System.exit(1);
        }
        //Hour is larger than 24
        else if(Character.getNumericValue(args[timeInd].trim().charAt(0)) == 2 && Character.getNumericValue(args[timeInd].trim().charAt(1)) > 4) {
          System.err.println("Invalid " + startOrEnd + " time (Hour is larger than 24)");
          System.exit(1);
        }
        //Hour is not followed by a colon ":"
        else if(!Character.toString(args[timeInd].trim().charAt(2)).equals(":")) {
          System.err.println("Invalid " + startOrEnd + " time (Format is hh:mm)");
          System.exit(1);
        }
      }
      //Hour is represented by 1 digit and is not followed by a colon ":"
      else if(!Character.toString(args[timeInd].trim().charAt(1)).equals(":")) {
        System.err.println("Invalid " + startOrEnd + " time (Format is hh:mm)");
        System.exit(1);
      }

      //Minute is not represented by a number
      if(!Character.isDigit(args[timeInd].trim().charAt(minInd)) || !Character.isDigit(args[timeInd].trim().charAt(minInd + 1))) {
        System.err.println("Invalid " + startOrEnd + " time (Minute is not represented by a number)");
        System.exit(1);
      }
      //Minute is larger than 60
      else if(Character.getNumericValue(args[timeInd].trim().charAt(minInd)) == 6 && Character.getNumericValue(args[timeInd].trim().charAt(minInd + 1)) > 0) {
        System.err.println("Invalid " + startOrEnd + " time (Minute is larger than 60)");
        System.exit(1);
      }
      //Hour is 24 and minute is not 00
      else if(Character.getNumericValue(args[timeInd].trim().charAt(0)) == 2 && Character.getNumericValue(args[timeInd].trim().charAt(1)) == 4
              && Character.getNumericValue(args[timeInd].trim().charAt(minInd)) != 0 && Character.getNumericValue(args[timeInd].trim().charAt(minInd + 1)) != 0) {
        System.err.println("Invalid " + startOrEnd + " time (Minute exceeds 24:00)");
        System.exit(1);
      }

      //Switch to parsing end date & end time
      if(i == 0) {
        dateInd = dateInd + 2;
        timeInd = timeInd + 2;

        dayInd = 2;
        yearInd = 4;
        minInd = 2;

        dayDigits = 1;

        startOrEnd = "end";
      }
    }

    //Add appointment to appointment book
    book.setOwner(args[ownerInd]);
    appointment.setAppointment(args[descInd], args[dateInd-2], args[timeInd-2], args[dateInd], args[timeInd]);
    book.addAppointment(appointment);

    System.out.println("Appointment added for " + book.getOwnerName());

    if(printFlag == 1) {
      System.out.println(appointment.toString());
    }

    System.exit(1);
  }

  /**
   * Prints text description of Project 1
   * Should only be called if user properly includes <code>-README</code> as command line argument
   */
  private static void printReadme() {
    System.out.println(" ________");
    System.out.println("| README |");
    System.out.println("-----------------");
    System.out.println("Sam Schroeder");
    System.out.println("CS410J");
    System.out.println("Summer 2016");
    System.out.println("Project 1");
    System.out.println();
    System.out.println("This project parses several command line arguments (some optional, some required) and");
    System.out.println("manipulates objects in memory to add appointments to a non-persistent appointment book");
    System.out.println("represented by several classes written by the professor and expanded by myself (the student).");
    System.out.println();
    System.out.println("Usage: java edu.pdx.cs410J.csam.Project1 [-print|-README] <name> <description> <start date> <start time> <end date> <end time>");
    System.out.println("-----------------");

    System.exit(1);
  }

}