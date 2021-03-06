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

    public enum ImageType{
        THUMBNAIL, OPTIMAL, ORIGINAL
    }

    @ManyToOne
    @JoinColumn(name="field_")
    public Field field;

    @Column(name="name_")
    public String name;

    @Column(name="title_")
    public String title;

    @Column(name="resource_id_")
    public String resourceId;

    @Column(name="content_type_")
    public String contentType;

    @Column(name="url_")
    public String url;

    @Column(name="size_")
    public int size;

    @Column(name="image_type_")
    @Enumerated(EnumType.STRING)
    public ImageType imageType;


}
