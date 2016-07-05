package com.iia.cdsm.myqcm.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by alexis on 09/10/2015.
 */
public class User implements Parcelable{

    public static final String SERIAL = "USER";

    //User ID
    private int id;

    //User Login
    private String login;

    //User name
    private String name;

    //User firstname
    private String firstname;

    //User password
    private String password;

    //User email
    private String email;

    public User(int id, String login, String name, String firstname, String password, String email)
    {
        super();
        this.id = id;
        this.login = login;
        this.name = name;
        this.firstname = firstname;
        this.password = password;
        this.email = email;
    }

    public User(String login, String password)
    {
        super();
        this.login = login;
        this.password = password;
    }

    public User(Parcel in) {
        this.id = in.readInt();
        this.login = in.readString();
        this.name = in.readString();
        this.firstname = in.readString();
        this.password = in.readString();
        this.email = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User() {
    }

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     *
     * @param firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.login);
        dest.writeString(this.name);
        dest.writeString(this.firstname);
        dest.writeString(this.password);
        dest.writeString(this.email);
    }
}
