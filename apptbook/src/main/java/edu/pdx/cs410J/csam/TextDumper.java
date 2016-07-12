package edu.pdx.cs410J.csam;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;

/**
 * Class containing single method to dump the information from an <code>AppointmentBook</code> to a text file
 */
public class TextDumper implements AppointmentBookDumper {

    /**
     * Dumps the information of the passed <code>AppointmentBook</code> object to a text file.
     * The text file's name should be the same as the <code>AppointmentBook</code>'s owner.
     * Each line of the text file represents a single appointment (Description|Start Date|Start Time|End Date|End Time).
     *
     * @param book
     * <code>AppointmentBook</code> to be dumped to text file
     *
     * @throws IOException
     */
    public void dump(AbstractAppointmentBook book) throws IOException {

        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(new File(book.getOwnerName()), true));

            for (Object appointment : book.getAppointments()) {
                String[] appointmentDetails = appointment.toString().split(" from ");

                String description = appointmentDetails[0];

                if(description.contains("|")) {
                    System.err.println("Appointment description cannot contain character '|'");
                    System.exit(1);
                }

                String[] startAndEnd = appointmentDetails[1].split(" until ");

                String[] start = startAndEnd[0].split(" ");
                String[] end = startAndEnd[1].split(" ");

                String startDate = start[0];
                String startTime = start[1];

                String endDate = end[0];
                String endTime = end[1];

                String fileAppointment = description + "|" + startDate + "|" + startTime + "|" + endDate + "|" + endTime;

                writer.append(fileAppointment + "\n");
            }

            writer.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
