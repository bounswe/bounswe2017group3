package com.bounswe.bounswe2017group3;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "events")
@Entity
public class Event implements Serializable {
    /**
     * Unique serial identifier for the current version of the class.
     */

    /**
     * Auto-generated id of the event.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Name of the event.
     */
    @NotEmpty
    @Column(unique = true)
    private String name;

    /**
     * Description of the event.
     */
    private String description;

    /**
     * Date of the event.
     */
    private Date date;

    /**
     * Location of the event.
     */
    private String location;

    /**
     * Returns the id of the event.
     *
     * @ return Id of the event.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the name of the event.
     *
     * @return name of the event.
     */
    public String getname() {
        return name;
    }

    /**
     * Sets the name of the event.
     *
     * @param name Name of the event.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Returns the description of the event.
     *
     * @return Description of the event.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the event.
     *
     * @param description Description of the event.
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Returns the location of the event.
     *
     * @return location of the event.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the event.
     *
     * @param location location of the event.
     */
    public void setLocation(String location) {
        this.location =location;
    }

    /**
     * Returns the date of the event.
     *
     * @return date of the event.
     */
    public Date getDate(){ return date;}

    /**
     * Sets the date od the event.
     * @param date date of the event.
     */
    public void setDate(Date date) {this.date = date;}
    
    /*
    Privacy Option
    */
    @NotNull                        //Privacy option cannot be null
    private Boolean privacy;  //Privacy option of the event.
    
    //Returns the privacy option of the event.
    public Boolean getPrivacy() {return privacy;}
    
    //Sets the privacy option of the event.
    public void setPrivacy(Boolean privacy) {this.privacy = privacy;}
    
    /**
     * Returns the string representation of the event object.
     *
     * @return String representation of the event object.
     */
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                ", privacy='" + privacy + '\'' +
                '}';
    }
}