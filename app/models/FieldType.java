package models;

import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 23.05.2015
 * Time: 22:47
 */
@Entity
@Table(name="ps_field_types")
public class FieldType extends PersistentObject {

    @Column(name="name_")
    public String name;

    @Column(name="icon_", length = 30)
    public String icon;
}
