package edu.pdx.cs410J.csam;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.*;

/**
 * Class containing a single method and a single variable to parse a text file for appointments in the format
 * used by the <code>TextDumper</code> class and create new <code>Appointment</code>s and <code>AppointmentBook</code>s based on the parsed information
 */
public class TextParser implements AppointmentBookParser {
    
    /**** Constructors ****/

    /**
     * Default constructor
     */
    public TextParser() {
    }

    /**
     * Constructor used to pass the name of a file to a <code>TextParser</code>
     *
     * @param fileName
     * Name of the text file to parse
     */
    public TextParser(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Parses a text file based on the format used by the <code>TextDumper</code> class.
     * Each line of the text file should represent a single appointment (Description|Start Date|Start Time|End Date|End Time).
     * File name should be the same as the <code>AppointmentBook</code> owner.
     *
     * @return
     * Returns an <code>AppointmentBook</code> created with the information from the parsed text file.
     * Returns an empty <code>AppointmentBook</code> if the specified file does not exist.
     *
     * @throws ParserException
     */
    @Override
    public AbstractAppointmentBook parse() throws ParserException {
        if(fileName == null) {
            return new AppointmentBook();
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            AppointmentBook book = new AppointmentBook();
            
            try {
                String singleAppointment = br.readLine();
                ArrayList<String> appointments = new ArrayList<>();

                while (singleAppointment != null) {
                    appointments.add(singleAppointment);
                    singleAppointment = br.readLine();
                }

                for(String s : appointments) {
                    String delimiter = "[|]+";
                    String[] splitAppointment = s.split(delimiter);
                    if(splitAppointment.length != 5) {
                        System.err.println("Appointment in file " + fileName + " is malformed");
                        System.exit(1);
                    }

                    String description = splitAppointment[0];

                    String[] bothDates = new String[] {splitAppointment[1], splitAppointment[3]};
                    String[] bothTimes = new String[] {splitAppointment[2], splitAppointment[4]};
                    
                    int dayDigits = 1;

                    int dayInd = 2;
                    int yearInd = 4;
                    int minInd = 2;

                    String startOrEnd = "start";

                    for(int i = 0; i < 2; ++i) {

                        //Date string too long or too short
                        if(bothDates[i].length() < 8 || bothDates[i].length() > 10) {
                            System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (Format is mm/dd/yyyy)");
                            System.exit(1);
                        }

                        //Month is not represented by a number
                        if(!Character.isDigit(bothDates[i].charAt(0))) {
                            System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (Month is not represented by a number)");
                            System.exit(1);
                        }

                        //Month is represented by 2 digits
                        if(Character.isDigit(bothDates[i].charAt(0)) && Character.isDigit(bothDates[i].charAt(1))) {
                            dayDigits = 2;

                            dayInd = 3;
                            yearInd = 5;

                            //First digit of month is not 0 or 1
                            if(Character.getNumericValue(bothDates[i].charAt(0)) > 1) {
                                System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (First digit of month is not 0 or 1)");
                                System.exit(1);
                            }
                            //First digit of month is 1, second digit is not 0, 1, or 2
                            else if(Character.getNumericValue(bothDates[i].charAt(0)) == 1 && Character.getNumericValue(bothDates[i].charAt(1)) > 2) {
                                System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (First digit of month is 1, second digit is not 0, 1, or 2)");
                                System.exit(1);
                            }
                            //Month is 00
                            else if(Character.getNumericValue(bothDates[i].charAt(0)) != 1 && Character.getNumericValue(bothDates[i].charAt(1)) == 0) {
                                System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (Month is 00)");
                                System.exit(1);
                            }
                            //Month is not followed by forward slash "/"
                            else if(!Character.toString(bothDates[i].charAt(2)).equals("/")) {
                                System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (Month is not followed by forward slash)");
                                System.exit(1);
                            }
                        }
                        //Month is single-digit and not followed by forward slash "/"
                        else if(!Character.toString(bothDates[i].charAt(1)).equals("/")) {
                            System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (Month is single-digit and not followed by forward slash)");
                            System.exit(1);
                        }

                        //Day is not represented by a number
                        if(!Character.isDigit(bothDates[i].charAt(dayInd))) {
                            System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (Day is not represented by a number)");
                            System.exit(1);
                        }

                        //Day is represented by 2 digits
                        if(Character.isDigit(bothDates[i].charAt(dayInd)) && Character.isDigit(bothDates[i].charAt(dayInd + 1))) {
                            if(dayDigits == 2) {
                                yearInd = 6;
                            } else {
                                yearInd = 5;
                            }

                            //First digit of day is not 0, 1, 2, or 3
                            if(Character.getNumericValue(bothDates[i].charAt(dayInd)) > 3) {
                                System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (First digit of day is not 0, 1, 2, or 3)");
                                System.exit(1);
                            }
                            //First digit of day is 3, second digit is not 0 or 1
                            else if(Character.getNumericValue(bothDates[i].charAt(dayInd)) == 3 && Character.getNumericValue(bothDates[i].charAt(dayInd + 1)) > 1) {
                                System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (First digit of day is 3, second digit is not 0 or 1)");
                                System.exit(1);
                            }
                            //Day is 00
                            else if(Character.getNumericValue(bothDates[i].charAt(dayInd)) == 0 && Character.getNumericValue(bothDates[i].charAt(dayInd + 1)) == 0) {
                                System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (Day is 00)");
                                System.exit(1);
                            }
                            //Day is Feb. 30
                            else if(Character.getNumericValue(bothDates[i].charAt(dayInd)) == 3 && Character.getNumericValue(bothDates[i].charAt(dayInd + 1)) == 0
                                    && ((Character.getNumericValue(bothDates[i].charAt(0)) == 0 && Character.getNumericValue(bothDates[i].charAt(1)) == 2)
                                    || Character.getNumericValue(bothDates[i].charAt(0)) == 2)) {
                                System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (There is no Feb. 30)");
                                System.exit(1);
                            }
                            //Day is Feb. 31
                            else if(Character.getNumericValue(bothDates[i].charAt(dayInd)) == 3 && Character.getNumericValue(bothDates[i].charAt(dayInd + 1)) == 1
                                    && ((Character.getNumericValue(bothDates[i].charAt(0)) == 0 && Character.getNumericValue(bothDates[i].charAt(1)) == 2)
                                    || Character.getNumericValue(bothDates[i].charAt(0)) == 2)) {
                                System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (There is no Feb. 31)");
                                System.exit(1);
                            }
                            //Day is Apr. 31
                            else if(Character.getNumericValue(bothDates[i].charAt(dayInd)) == 3 && Character.getNumericValue(bothDates[i].charAt(dayInd + 1)) == 1
                                    && ((Character.getNumericValue(bothDates[i].charAt(0)) == 0 && Character.getNumericValue(bothDates[i].charAt(1)) == 4)
                                    || Character.getNumericValue(bothDates[i].charAt(0)) == 4)) {
                                System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (There is no Apr. 31)");
                                System.exit(1);
                            }
                            //Day is Jun. 31
                            else if(Character.getNumericValue(bothDates[i].charAt(dayInd)) == 3 && Character.getNumericValue(bothDates[i].charAt(dayInd + 1)) == 1
                                    && ((Character.getNumericValue(bothDates[i].charAt(0)) == 0 && Character.getNumericValue(bothDates[i].charAt(1)) == 6)
                                    || Character.getNumericValue(bothDates[i].charAt(0)) == 6)) {
                                System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (There is no Jun. 31)");
                                System.exit(1);
                            }
                            //Day is Sep. 31
                            else if(Character.getNumericValue(bothDates[i].charAt(dayInd)) == 3 && Character.getNumericValue(bothDates[i].charAt(dayInd + 1)) == 1
                                    && ((Character.getNumericValue(bothDates[i].charAt(0)) == 0 && Character.getNumericValue(bothDates[i].charAt(1)) == 9)
                                    || Character.getNumericValue(bothDates[i].charAt(0)) == 9)) {
                                System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (There is no Sep. 31)");
                                System.exit(1);
                            }
                            //Day is Nov. 31
                            else if(Character.getNumericValue(bothDates[i].charAt(dayInd)) == 3 && Character.getNumericValue(bothDates[i].charAt(dayInd + 1)) == 1
                                    && (Character.getNumericValue(bothDates[i].charAt(0)) == 1 && Character.getNumericValue(bothDates[i].charAt(1)) == 1)) {
                                System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (There is no Nov. 31)");
                                System.exit(1);
                            }
                            //Day is not followed by forward slash "/"
                            else if(!Character.toString(bothDates[i].charAt(dayInd + 2)).equals("/")) {
                                System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (Day is not followed by forward slash)");
                                System.exit(1);
                            }
                        }
                        //Day is single-digit and not followed by forward slash "/"
                        else if(!Character.toString(bothDates[i].charAt(dayInd + 1)).equals("/")) {
                            System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (Day is single-digit and not followed by forward slash)");
                            System.exit(1);
                        }

                        //Year is not represented by a number
                        if(!Character.isDigit(bothDates[i].charAt(yearInd)) || !Character.isDigit(bothDates[i].charAt(yearInd + 1))
                                || !Character.isDigit(bothDates[i].charAt(yearInd + 2)) || !Character.isDigit(bothDates[i].charAt(yearInd + 3))) {
                            System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (Year is not represented by a number)");
                            System.exit(1);
                        }

                        //Day is Feb. 29, not on a leap year
                        if(Character.getNumericValue(bothDates[i].charAt(dayInd)) == 2 && Character.getNumericValue(bothDates[i].charAt(dayInd + 1)) == 9
                                && ((Character.getNumericValue(bothDates[i].charAt(0)) == 0 && Character.getNumericValue(bothDates[i].charAt(1)) == 2)
                                || Character.getNumericValue(bothDates[i].charAt(0)) == 2)
                                && Integer.parseInt(Character.toString(bothDates[i].charAt(yearInd + 2)) + Character.toString(bothDates[i].charAt(yearInd + 3))) % 4 != 0) {
                            System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " date (Day is Feb. 29, but year is not a leap year)");
                            System.exit(1);
                        }

                        //Time is too long or too short
                        if(bothTimes[i].length() < 4 || bothTimes[i].length() > 5) {
                            System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " time (Format is hh:mm)");
                            System.exit(1);
                        }

                        //Hour is not represented by a number
                        if(!Character.isDigit(bothTimes[i].charAt(0))) {
                            System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " time (Hour is not represented by a number)");
                            System.exit(1);
                        }

                        //Hour is represented by 2 digits
                        if(Character.isDigit(bothTimes[i].charAt(0)) && Character.isDigit(bothTimes[i].charAt(1))) {
                            minInd = 3;

                            //First digit of hour is not 1 or 2
                            if(Character.getNumericValue(bothTimes[i].charAt(0)) > 2 || Character.getNumericValue(bothTimes[i].charAt(0)) <= 0) {
                                System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " time (First digit of hour is not 1 or 2)");
                                System.exit(1);
                            }
                            //Hour is larger than 24
                            else if(Character.getNumericValue(bothTimes[i].charAt(0)) == 2 && Character.getNumericValue(bothTimes[i].charAt(1)) > 4) {
                                System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " time (Hour is larger than 24)");
                                System.exit(1);
                            }
                            //Hour is not followed by a colon ":"
                            else if(!Character.toString(bothTimes[i].charAt(2)).equals(":")) {
                                System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " time (Format is hh:mm)");
                                System.exit(1);
                            }
                        }
                        //Hour is represented by 1 digit and is not followed by a colon ":"
                        else if(!Character.toString(bothTimes[i].charAt(1)).equals(":")) {
                            System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " time (Format is hh:mm)");
                            System.exit(1);
                        }

                        //Minute is not represented by a number
                        if(!Character.isDigit(bothTimes[i].charAt(minInd)) || !Character.isDigit(bothTimes[i].charAt(minInd + 1))) {
                            System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " time (Minute is not represented by a number)");
                            System.exit(1);
                        }
                        //Minute is larger than 60
                        else if(Character.getNumericValue(bothTimes[i].charAt(minInd)) == 6 && Character.getNumericValue(bothTimes[i].charAt(minInd + 1)) > 0) {
                            System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " time (Minute is larger than 60)");
                            System.exit(1);
                        }
                        //Hour is 24 and minute is not 00
                        else if(Character.getNumericValue(bothTimes[i].charAt(0)) == 2 && Character.getNumericValue(bothTimes[i].charAt(1)) == 4
                                && Character.getNumericValue(bothTimes[i].charAt(minInd)) != 0 && Character.getNumericValue(bothTimes[i].charAt(minInd + 1)) != 0) {
                            System.err.println("In file " + fileName + ": Invalid " + startOrEnd + " time (Minute exceeds 24:00)");
                            System.exit(1);
                        }

                        //Switch to parsing end date & end time
                        dayInd = 2;
                        yearInd = 4;
                        minInd = 2;

                        dayDigits = 1;

                        startOrEnd = "end";

                    }

                    Appointment newAppointment = new Appointment(description, bothDates[0], bothTimes[0], bothDates[1], bothTimes[1]);
                    book.addAppointment(newAppointment);
                }
            } 
            catch(IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    br.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }

            return book;
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    private String fileName = null;
}
