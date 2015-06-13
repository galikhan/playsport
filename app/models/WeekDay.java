package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 09.06.2015
 * Time: 23:27
 */
@Entity
@Table(name="ps_weekday")
public class WeekDay extends PersistentObject{

    @Column(name="name_")
    public String name;

    @Column(name="name_kk_")
    public String nameKk;

    @Column(name="short_name_")
    public String shortName;

    @Column(name="short_name_kk_")
    public String shortNameKk;

}
