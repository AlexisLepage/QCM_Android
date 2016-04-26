package com.iia.cdsm.myqcm.Entities;

/**
 * Created by Alexis on 17/02/2016.
 */
public class TypeMedia {

    /** TypeMedia Id */
    protected long id;

    /** TypeMedia name */
    protected String name;

    /**
     * Get TypeMedia Id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Set TypeMedia Id
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get TypeMedia Name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set TypeMedia Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
