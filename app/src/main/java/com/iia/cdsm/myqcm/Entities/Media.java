package com.iia.cdsm.myqcm.Entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Alexis on 17/02/2016.
 */
public class Media {

    /** Media Id */
    protected long id;

    /** Media name */
    protected String name;

    /** Media url */
    protected String url;

    /** Media Created_at */
    protected String created_at;

    /** Media Updated_at */
    protected String updated_at;

    /** Media TypeMedia_id */
    protected long typeMedia_id;

    /** Media Question_id */
    protected long question_id;


    /**
     * Get Media Id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Set Media Id
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get Media Url
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set Media Url
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get Media Name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set Media Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Media Created_at
     * @return created_at
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * Set Media Created_at
     * @param
     */
    public void setCreated_at() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
        this.created_at = sdf.format(c.getTime());
    }

    /**
     * Get Media Updated_at
     * @return updated_at
     */
    public String getUpdated_at() {
        return updated_at;
    }

    /**
     * Set Media Updated_at
     * @param
     */
    public void setUpdated_at() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
        this.updated_at = sdf.format(c.getTime());
    }

    /**
     * Get Media TypeMedia_id
     * @return typeMedia_id
     */
    public long getTypeMedia_id() {
        return typeMedia_id;
    }

    /**
     * Set Media TypeMedia_id
     * @param typeMedia_id
     */
    public void setTypeMedia_id(long typeMedia_id) {
        this.typeMedia_id = typeMedia_id;
    }

    /**
     * Get Media Question_id
     * @return question_id
     */
    public long getQuestion_id() {
        return question_id;
    }

    /**
     * Set Media Question_id
     * @param question_id
     */
    public void setQuestion_id(long question_id) {
        this.question_id = question_id;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
