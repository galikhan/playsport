package models;

import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 16.05.2015
 * Time: 20:36
 */
@Entity
@Table(name="coverings")
public class Covering extends Model{

    @Column(name="name_")
    public String name;

    @Column(name="info_", columnDefinition = "text")
    public String info;

}
