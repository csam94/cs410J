package edu.pdx.cs410J.csam;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;

/**
 * Created by sam on 7/16/16.
 */
public class PrettyPrinter implements AppointmentBookDumper {

    public PrettyPrinter() {

    }

    public PrettyPrinter(String fileName, String ownerName) {
        this.fileName = fileName;
        this.ownerName = ownerName;
    }

    public void dump(AbstractAppointmentBook book) throws IOException {

        if(this.fileName == null || this.ownerName == null) {
            System.err.println("File name or owner name is null");
            System.exit(1);
        }

        boolean check = new File(this.fileName).exists();

        if (check) {
            BufferedReader br = new BufferedReader(new FileReader(this.fileName));

            try {
                String owner = br.readLine();

                if (owner != null && !owner.equals(this.ownerName)) {
                    System.err.println("Specified owner on command line does not match owner of specified pretty file");
                    System.exit(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(new File(this.fileName), true));

            if(!check) {
                writer.append(this.ownerName + "\n");
            }

            for (Object appointment : book.getAppointments()) {
                writer.append(((Appointment) appointment).getDescription() + " | " + ((Appointment) appointment).getBeginTimeString() + " - " +
                        ((Appointment) appointment).getEndTimeString() + " [" + ((Appointment) appointment).timeDiff() + " minutes]" + "\n");
                writer.append("-----------------------------------------------------------------------" + "\n");
            }

            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    private String fileName = null;
    private String ownerName;
}
