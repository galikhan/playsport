package models;

import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 20.05.2015
 * Time: 22:16
 */

@Entity
@Table(name="ps_cities")
public class City extends PersistentObject {

    @Column(name="name_")
    public String name;

    @Column(name="code_")
    public String code;

}
