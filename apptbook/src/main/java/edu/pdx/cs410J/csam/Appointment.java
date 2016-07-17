package edu.pdx.cs410J.csam;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.ParserException;

import java.text.*;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Contains variables and methods for representing and manipulating a simple appointment with a description, start time, and end time
 */
public class Appointment extends AbstractAppointment implements Comparable<Appointment> {

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

    DateFormat formatter = new SimpleDateFormat("dd/mm/yyyy, HH:mm");

    try {
      this.prettyStart = formatter.parse(this.startDate + ", " + this.startTime);
    } catch(ParseException e) {
      e.printStackTrace();
    }

    try {
      this.prettyEnd = formatter.parse(this.endDate + ", " + this.endTime);
    } catch(ParseException e) {
      e.printStackTrace();
    }
  }

  /**** Methods ****/

  /**
   * Returns the appointment's start time as a nicely formatted date + time
   *
   * @return Returns <code>prettyStart</code>
     */
  @Override
  public Date getBeginTime() {
    return prettyStart;
  }

  /**
   * Returns the appointment's end time as a nicely formatted date + time
   *
   * @return Returns <code>prettyEnd</code>
   */
  @Override
  public Date getEndTime() {
    return prettyEnd;
  }

  /**
   * Returns the combination of the appointment's start date and start time as a single string
   *
   * @return Returns <code>prettyStart</code> as a string formatted with <code>DateFormat.SHORT</code>
     */
  @Override
  public String getBeginTimeString() {
    if(this.prettyStart == null) {
      return this.startDate + " " + this.startTime;
    }

    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(this.prettyStart);
  }

  /**
   * Returns the combination of the appointment's end date and end time as a single string
   *
   * @return Returns <code>prettyEnd</code> as a string formatted with <code>DateFormat.SHORT</code>
     */
  @Override
  public String getEndTimeString() {
    if(this.prettyEnd == null) {
      return this.endDate + " " + this.endTime;
    }

    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(this.prettyEnd);
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

    DateFormat formatter = new SimpleDateFormat("dd/mm/yyyy, HH:mm");

    try {
      this.prettyStart = formatter.parse(this.startDate + ", " + this.startTime);
    } catch(ParseException e) {
      e.printStackTrace();
    }

    try {
      this.prettyEnd = formatter.parse(this.endDate + ", " + this.endTime);
    } catch(ParseException e) {
      e.printStackTrace();
    }

  }

  public int compareTo(Appointment other) {
    if(this.prettyStart.after(other.prettyStart)) {
      return 1;
    } else if(this.prettyStart.before(other.prettyStart)) {
      return -1;
    } else {
      if(this.prettyEnd.after(other.prettyEnd)) {
        return 1;
      } else if(this.prettyEnd.before(other.prettyEnd)) {
        return -1;
      } else {
        return this.description.compareTo(other.description);
      }
    }
  }

  public long timeDiff() {
    long duration  = prettyEnd.getTime() - prettyStart.getTime();

    long diff = TimeUnit.MILLISECONDS.toMinutes(duration);

    return diff;
  }

  private String description = "not implemented";

  private String startDate = "not implemented";

  private String startTime = "not implemented";

  private String endDate = "not implemented";

  private String endTime = "not implemented";

  private Date prettyStart;

  private Date prettyEnd;
}
