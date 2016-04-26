package com.iia.cdsm.myqcm.Entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Alexis on 17/02/2016.
 */
public class Category {

    /** Category Id */
    protected long id;

    /** Category name */
    protected String name;

    /** Category Created_at */
    protected String created_at;

    /** Category Updated_at */
    protected String updated_at;

    /**
     * Get Category Id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Set Category Id
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get Category Name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set Category Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Category Updated_at
     * @return updated_at
     */
    public String getUpdated_at() {
        return updated_at;
    }

    /**
     * Set Category Updated_at
     * @param
     */
    public void setUpdated_at() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
        this.updated_at = sdf.format(c.getTime());
    }

    /**
     * Get Category Name
     * @return name
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * Set Category Created_at
     * @param
     */
    public void setCreated_at() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
        this.created_at = sdf.format(c.getTime());
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
