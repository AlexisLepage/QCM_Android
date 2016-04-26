package com.iia.cdsm.myqcm.Entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Alexis on 17/02/2016.
 */
public class Answer {

    /** Answer Id */
    protected long id;

    /** Answer title */
    protected String title;

    /** Answer is_valid */
    protected Boolean is_valid;

    /** Answer is_selected */
    protected Boolean is_selected;

    /** Answer Created_at */
    protected String created_at;

    /** Answer Updated_at */
    protected String updated_at;

    /** Answer Question_id */
    protected long question_id;

    /**
     * Get Answer Id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Set Answer Id
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get Answer Title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set Answer Title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get Answer Is_valid
     * @return is_valid
     */
    public Boolean getIs_valid() {
        return is_valid;
    }

    /**
     * Set Answer Is_valid
     * @param is_valid
     */
    public void setIs_valid(Boolean is_valid) {
        this.is_valid = is_valid;
    }

    /**
     * Get Answer Is_selected
     * @return is_selected
     */
    public Boolean getIs_selected() {
        return is_selected;
    }

    /**
     * Set Answer Is_selected
     * @param is_selected
     */
    public void setIs_selected(Boolean is_selected) {
        this.is_selected = is_selected;
    }

    /**
     * Get Answer Created_at
     * @return created_at
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * Set Answer Created_at
     * @param
     */
    public void setCreated_at() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
        this.created_at = sdf.format(c.getTime());
    }

    /**
     * Get Answer Updated_at
     * @return updated_at
     */
    public String getUpdated_at() {
        return updated_at;
    }

    /**
     * Set Answer Updated_at
     * @param
     */
    public void setUpdated_at() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
        this.updated_at = sdf.format(c.getTime());
    }

    /**
     * Get Answer Question_id
     * @return question_id
     */
    public long getQuestion_id() {
        return question_id;
    }

    /**
     * Set Answer Question_id
     * @param question_id
     */
    public void setQuestion_id(long question_id) {
        this.question_id = question_id;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }
}
