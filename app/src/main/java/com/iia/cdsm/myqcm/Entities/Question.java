package com.iia.cdsm.myqcm.Entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Alexis on 17/02/2016.
 */
public class Question {

    /** Question Id */
    protected long id;

    /** Question title */
    protected String title;

    /** Question value */
    protected Integer value;

    /** Question Created_at */
    protected String created_at;

    /** Question Updated_at */
    protected String updated_at;

    /** Question Qcm_id */
    protected long qcm_id;

    /**
     * Get Question Id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Set Question Id
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get Question Title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set Question Title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get Question Value
     * @return value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Set Question Value
     * @param value
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * Get Question Created_at
     * @return created_at
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * Set Question Created_at
     * @param
     */
    public void setCreated_at() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
        this.created_at = sdf.format(c.getTime());
    }

    /**
     * Get Question Updated_at
     * @return updated_at
     */
    public String getUpdated_at() {
        return updated_at;
    }

    /**
     * Set Question Updated_at
     * @param
     */
    public void setUpdated_at() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
        this.updated_at = sdf.format(c.getTime());
    }

    /**
     * Get Question Ccm_id
     * @return qcm_id
     */
    public long getQcm_id() {
        return qcm_id;
    }

    /**
     * Set Question Ccm_id
     * @param qcm_id
     */
    public void setQcm_id(long qcm_id) {
        this.qcm_id = qcm_id;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }
}
