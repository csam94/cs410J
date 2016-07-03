package edu.pdx.cs410J.csam;

import edu.pdx.cs410J.AbstractAppointment;

public class Appointment extends AbstractAppointment {

  @Override
  public String getBeginTimeString() {
    return startDate + " " + startTime;
  }

  @Override
  public String getEndTimeString() {
    return this.endDate + " " + this.endTime;
  }

  @Override
  public String getDescription() {
    return this.description;
  }

  public void setAppointment(String desc, String startD, String startT, String endD, String endT) {
    this.description = desc;

    this.startDate = startD;

    this.startTime = startT;

    this.endDate = endD;

    this.endTime = endT;
  }

  private String description = "not implemented";

  private String startDate = "not implemented";

  private String startTime = "not implemented";

  private String endDate = "not implemented";

  private String endTime = "not implemented";
}
