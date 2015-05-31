package models;

import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 23.05.2015
 * Time: 22:58
 */
@MappedSuperclass
public class PersistentObject extends Model {

    @Column(name="created_date_")
    public Date createdDate;

    @Column(name="modified_date_")
    public Date modifiedDate;

    @Column(name="removed_")
    public Boolean removed;

    @Column(name="enabled_")
    public Boolean enabled;
}
