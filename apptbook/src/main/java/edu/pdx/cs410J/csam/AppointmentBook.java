package edu.pdx.cs410J.csam;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * Contains variables and methods for representing and manipulating a simple appointment book with an owner name and a collection of <code>Appointment</code>s
 */
public class AppointmentBook<T extends AbstractAppointment> extends AbstractAppointmentBook {

    public AppointmentBook() {

    }

    public AppointmentBook(String owner) {
        this.ownerName = owner;
    }

    /**
     * Returns the collection of appointments in the appointment book
     *
     * @return Returns the <code>AppointmentBook</code>'s <code>appointments</code>
     */
    @Override
    public Collection<T> getAppointments() {
       return this.appointments;
    }

    /**
     * Adds a new appointment to the book's collection of appointments
     *
     * @param newAppointment <code>Appointment</code> to add to the <code>AppointmentBook</code>
     */
    @Override
    public void addAppointment(AbstractAppointment newAppointment) {
        this.appointments.add((T) newAppointment);
    }

    /**
     * Returns the name of the appointment book's owner
     *
     * @return Returns <code>ownerName</code> as a <code>String</code>
     */
    @Override
    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwner(String owner) {
        this.ownerName = owner;
    }

    private Collection<T> appointments = new ArrayList<>();

    private String ownerName;
}
