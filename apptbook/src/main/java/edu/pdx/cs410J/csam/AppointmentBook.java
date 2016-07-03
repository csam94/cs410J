package edu.pdx.cs410J.csam;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by sam on 6/26/16.
 */
public class AppointmentBook<T extends AbstractAppointment> extends AbstractAppointmentBook {

    @Override
    public Collection<T> getAppointments() {
       return this.appointments;
    }

    @Override
    public void addAppointment(AbstractAppointment newAppointment) {
        this.appointments.add((T) newAppointment);
    }

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
