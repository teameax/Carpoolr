package is.ru.Carpoolr.models;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by DrepAri on 31.10.14.
 */
public class Registration implements Serializable {

    protected User user;
    protected int seats;
    protected Calendar registration_date;
    protected String trip_id;
    protected String type;

    public Registration(User user, int seats, Calendar registration_date, String trip_id, String type) {
        this.user = user;
        this.seats = seats;
        this.registration_date = registration_date;
        this.trip_id = trip_id;
        this.type = type;
    }

    public Registration() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Calendar getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Calendar registration_date) {
        this.registration_date = registration_date;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
