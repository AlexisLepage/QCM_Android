package com.iia.cdsm.myqcm.Entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alexis on 17/02/2016.
 */
public class Qcm {

    /** Qcm Id */
    protected long id;

    /** Qcm Name */
    protected String name;

    /** Qcm IsDone */
    protected Integer is_done;

    /** Qcm isAvailable */
    protected Boolean is_available;

    /** Qcm beginningAt */
    protected String beginning_at;

    /** Qcm isAvailable */
    protected String finished_at;

    /** Qcm isAvailable */
    protected Integer duration;

    /** Qcm Created_at */
    protected String created_at;

    /** Qcm Updated_at */
    protected String updated_at;

    /** Qcm Category_id */
    protected long category_id;


    /**
     * Get Qcm Id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Set Qcm Id
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get Qcm Name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set Qcm Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Qcm IsDone
     * @return is_done
     */
    public Integer getIs_done() {
        return is_done;
    }

    /**
     * Set Qcm IsDone
     * @param is_done
     */
    public void setIs_done(Integer is_done) {
        this.is_done = is_done;
    }

    /**
     * Get Qcm IsAvailable
     * @return isAvailable
     */
    public Boolean getIs_available() {
        return is_available;
    }

    /**
     * Set Qcm IsAvailable
     * @param is_available
     */
    public void setIs_available(Boolean is_available) {
        this.is_available = is_available;
    }

    /**
     * Get Qcm Beginning_at
     * @return beginning_at
     */
    public String getBeginning_at() {
        return beginning_at;
    }

    /**
     * Set Qcm Beginning_at
     * @param beginning_at
     */
    public void setBeginning_at(String beginning_at) {
        this.beginning_at = beginning_at;
    }

    /**
     * Get Qcm Finished_at
     * @return finished_at
     */
    public String getFinished_at() {
        return finished_at;
    }

    /**
     * Set Qcm Finished_at
     * @param finished_at
     */
    public void setFinished_at(String finished_at) {
        this.finished_at = finished_at;
    }

    /**
     * Get Qcm Duration
     * @return duration
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Set Qcm Duration
     * @param duration
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Get Qcm Updated_at
     * @return updated_at
     */
    public String getUpdated_at() {
        return updated_at;
    }

    /**
     * Set Qcm Updated_at
     * @param
     */
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * Get Qcm Created_at
     * @return created_at
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * Set Qcm Created_at
     * @param
     */
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    /**
     * Get Qcm Category_id
     * @return category_id
     */
    public long getCategory_id() {
        return category_id;
    }

    /**
     * Set Qcm Category_id
     * @param category_id
     */
    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }



    @Override
    public String toString() {
        return this.getName();
    }
}


