package edu.pdx.cs410J.csam;

import edu.pdx.cs410J.AbstractAppointmentBook;

import static jdk.nashorn.internal.objects.Global.print;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

  public static void main(String[] args) {
    Class c = AbstractAppointmentBook.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    AppointmentBook book = new AppointmentBook();
    Appointment appointment = new Appointment();

    if(args.length < 4) {
      System.err.println("Missing command line arguments. Arguments are: <Name> <Description> <Start Time> <End Time>");
      System.exit(1);
    }
    else if(args.length == 5) {
      book.setOwner(args[1]);
      appointment.setAppointment(args[2], args[3], args[4]);

      if(args[0] != "-print" && args[0] != "-README") {
        System.err.println("Invalid option " + args[0] + ". Valid options are '-print' and '-README'.");
        System.exit(1);
      }

      if(args[2].trim() == "") {
        System.err.println("Description is empty");
        System.exit(1);
      }
    }
    else if(args.length == 6) {
      if((args[0] == "-print" && args[1] != "-README") || (args[0] == "-README" && args[1] != "-print")) {
        System.err.println("Invalid option " + args[1] + ". Valid options are '-print' and '-README'.");
        System.exit(1);
      }
      else if((args[1] == "-README" && args[0] != "-print") || (args[1] == "-print" && args[0] != "-README")) {
        System.err.println("Invalid option '" + args[0] + "'. Valid options are '-print' and '-README'.");
        System.exit(1);
      }

      if(args[3].trim() == "") {
        System.err.println("Description is empty");
        System.exit(1);
      }
    }
    else {
      System.err.println("Too many arguments/options. The correct format is: [-print|-README] <Name> <Description> <Start Time> <End Time>");
    }

    for (String arg : args) {
      System.out.println(arg);
    }

    book.setOwner(args[0]);
    appointment.setAppointment(args[1], args[2], args[3]);

    System.exit(1);
  }

}