package edu.pdx.cs410J.csam;

import edu.pdx.cs410J.AbstractAppointment;

public class Appointment extends AbstractAppointment {

  @Override
  public String getBeginTimeString() {
    return beginTime;
  }

  @Override
  public String getEndTimeString() {
    return endTime;
  }

  @Override
  public String getDescription() {
    return description;
  }

  public void setAppointment(String desc, String begin, String end) {
    description = desc;

    beginTime = begin;

    endTime = end;
  }

  private String description;

  private String beginTime;

  private String endTime;
}
