package is.ru.Carpoolr.models;

import java.util.Date;

/**
 * Created by DrepAri on 11.10.14.
 */
public class Ride {

    //region Member Variables
    protected Date date;
    protected String demands;
    protected String start;
    protected String note;
    protected int seats;
    protected String destination;
    protected String type;
    protected User user;
    //endregion

    //region Constructors
    public Ride() { }

    public Ride(Date date, String demands, String start, String note, int seats, String destination, String type, User user) {
        this.date = date;
        this.demands = demands;
        this.start = start;
        this.note = note;
        this.seats = seats;
        this.destination = destination;
        this.type = type;
        this.user = user;
    }
    //endregion

    //region Getters and Setters

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDemands() {
        return demands;
    }

    public void setDemands(String demands) {
        this.demands = demands;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    //endregion
}
