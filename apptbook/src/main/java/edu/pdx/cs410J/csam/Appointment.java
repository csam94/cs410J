package edu.pdx.cs410J.csam;

import edu.pdx.cs410J.AbstractAppointment;

/**
 * Contains variables and methods for representing and manipulating a simple appointment with a description, start time, and end time
 */
public class Appointment extends AbstractAppointment {

  /**** Constructors ****/

  /**
   * Default constructor
   */
  public Appointment() {

  }

  /**
   * Constructor for passing in <code>Appointment</code> information
   *
   * @param desc
   * Appointment description
   *
   * @param startD
   * Appointment start date
   *
   * @param startT
   * Appointment start time
   *
   * @param endD
   * Appointment end date
   *
   * @param endT
   * Appointment end time
   */
  public Appointment(String desc, String startD, String startT, String endD, String endT) {
    this.description = desc;

    this.startDate = startD;

    this.startTime = startT;

    this.endDate = endD;

    this.endTime = endT;
  }

  /**** Methods ****/

  /**
   * Returns the combination of the appointment's start date and start time as a single string
   *
   * @return Returns <code>startDate</code> and <code>startTime</code> combined into a single <code>String</code>
     */
  @Override
  public String getBeginTimeString() {
    return startDate + " " + startTime;
  }

  /**
   * Returns the combination of the appointment's end date and end time as a single string
   *
   * @return Returns <code>endDate</code> and <code>endTime</code> combined into a single <code>String</code>
     */
  @Override
  public String getEndTimeString() {
    return this.endDate + " " + this.endTime;
  }

  /**
   * Returns the appointment's description
   *
   * @return Returns <code>description</code> as a <code>String</code>
     */
  @Override
  public String getDescription() {
    return this.description;
  }

  /**
   * Sets all of an appointment's parameters
   *
   * @param desc Appointment's description
   * @param startD Appointment's start date
   * @param startT Appointment's start time
   * @param endD Appointment's end date
   * @param endT Appointment's end time
   */
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
