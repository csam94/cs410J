package edu.pdx.cs410J.csam;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import java.util.Collection;

/**
 * Created by sam on 6/26/16.
 */
public class AppointmentBook<T extends AbstractAppointment> extends AbstractAppointmentBook {

    @Override
    public Collection<T> getAppointments() {
       return appointments;
    }

    @Override
    public void addAppointment(AbstractAppointment newAppointment) {
        appointments.add((T) newAppointment);
    }

    @Override
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwner(String owner) {
        ownerName = owner;
    }

    private Collection<T> appointments;

    private String ownerName;
}
