package is.ru.Carpoolr.models;

import java.io.Serializable;

/**
 * Created by DrepAri on 11.10.14.
 */
public class User implements Serializable {

    //region Member Variables
    protected String email;
    protected String username;
    protected String phone;
    //endregion

    //region Contructors
    public User() { }

    public User(String email, String username, String phone) {
        this.email = email;
        this.username = username;
        this.phone = phone;
    }
    //endregion

    //region Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    //endregion
}
