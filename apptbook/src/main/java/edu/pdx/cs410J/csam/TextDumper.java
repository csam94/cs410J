package edu.pdx.cs410J.csam;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * Created by sam on 7/6/16.
 */
public class TextDumper<T extends AbstractAppointment> implements AppointmentBookDumper {
    public void dump(AbstractAppointmentBook book) throws IOException {

        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(new File(book.getOwnerName()), true));

            //PrintWriter writer = new PrintWriter(book.getOwnerName(), "UTF-8");
            for (Object appointment : book.getAppointments()) {
                writer.append(appointment.toString() + "\n");
            }

            writer.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
