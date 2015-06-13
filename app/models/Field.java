package models;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 16.05.2015
 * Time: 20:18
 */
@Entity
@Table(name="ps_fields")
public class Field extends PersistentObject {

    @Column(name="name_")
    public String name;

    @Column(name="address_")
    public String address;

    @Column(name="info_", columnDefinition = "text")
    public String info;

    @Column(name="phones_", columnDefinition = "text")
    public String phones;

    @Column(name="active_")
    public Boolean active;

    @Column(name="booking_enabled_")
    public Boolean bookingEnabled;

    @ManyToOne
    @JoinColumn(name="city_")
    public City city;

    @ManyToOne
    @JoinColumn(name="covering_")
    public Covering covering;

    @Column(name="width_")
    public int width;

    @Column(name="length_")
    public int length;

    @ManyToOne
    @JoinColumn(name="user_")
    public User user;

    @Column(name="map_")
    public String map;

    @ManyToMany
    @JoinTable(name="m2m_field_fcomforts", joinColumns = {@JoinColumn(name="field_")}, inverseJoinColumns = {@JoinColumn(name="comfort_")})
    public Set<FieldComfort> fieldComfortSet = new HashSet<FieldComfort>();

    public Set<FieldComfort> getFieldComfortSet() {
        return fieldComfortSet;
    }

    public void setFieldComfortSet(FieldComfort fieldComfort) {
        fieldComfortSet.add(fieldComfort);
    }
}
