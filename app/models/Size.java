package models;

import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 16.05.2015
 * Time: 20:39
 */

@Entity
@Table(name="sizes")
public class Size extends Model {

    @Column(name="name_")
    public String name;

    @Column(name="length_")
    public int length;

    @Column(name="width_")
    public int width;

}
