package models;

import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 16.05.2015
 * Time: 19:50
 */
@Entity
@Table(name="users")
public class User extends Model implements Serializable{

    public enum Role {
        user, moderator, admin
    }

    @Column(name="name_")
    public String name;

    @Column(name="firstname_")
    public String firstname;

    @Column(name="lastname_")
    public String lastname;

    @Column(name="email_")
    public String email;

    @Column(name="phone_")
    public String phone;

    @Column(name="company_name_")
    public String companyName;

    @Column(name="role_")
    public Role role;
}
