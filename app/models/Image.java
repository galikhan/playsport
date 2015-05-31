package models;

import play.db.jpa.Model;

import javax.persistence.*;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 16.05.2015
 * Time: 20:41
 */
@Entity
@Table(name="ps_images")
public class Image extends PersistentObject {

    @ManyToOne
    @JoinColumn(name="field_")
    public Field field;

    @Column(name="name_")
    public String name;

    @Column(name="title_")
    public String title;
}
