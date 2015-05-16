package models;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;

import javax.persistence.*;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 16.05.2015
 * Time: 20:18
 */
@Entity
@Table(name="fields")
public class Field extends Model {

    @Column(name="address_")
    public String address;

    @Column(name="info_", columnDefinition = "text")
    public String info;

    @Column(name="active_")
    public Boolean active;

    @Column(name="booking_enabled_")
    public Boolean bookingEnabled;

    @ManyToOne
    @JoinColumn(name="field_size_")
    public Size fieldSize;

    @ManyToOne
    @JoinColumn(name="covering_")
    public Covering covering;

    @ManyToOne
    @JoinColumn(name="user_")
    public User user;
}
