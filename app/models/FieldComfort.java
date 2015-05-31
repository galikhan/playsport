package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 24.05.2015
 * Time: 22:44
 */
@Entity
@Table(name="ps_field_comforts")
public class FieldComfort extends PersistentObject{

    @Column(name="name_")
    public String name;

    @Column(name="info_")
    public String info;

}
