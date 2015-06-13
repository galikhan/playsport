package models;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 09.06.2015
 * Time: 09:13
 */
@Entity
@Table(name="ps_pay_per_hour")
public class PayPerHour extends PersistentObject{

    @Column(name="begin_hour_")
    public Date beginHour;

    @Column(name="end_hour_")
    public Date endHour;

    @Column(name="payment_")
    public int payment;

    @ManyToOne
    @JoinColumn(name="weekday_")
    public WeekDay weekDay;

}
