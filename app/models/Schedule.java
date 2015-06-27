package models;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 09.06.2015
 * Time: 09:13
 */
@Entity
@Table(name="ps_schedule")
public class Schedule extends PersistentObject{

    @ManyToOne
    @JoinColumn(name="field_")
    public Field field;

    @ManyToOne
    @JoinColumn(name="begin_day_")
    public WeekDay beginDay;

    @ManyToOne
    @JoinColumn(name="end_day_")
    public WeekDay endDay;

    @Column(name="begin_time_")
    public String beginTime;

    @Column(name="end_time_")
    public String endTime;

    @Column(name="payment_")
    public int payment;

    @Column(name="begin_hour_")
    public int beginHour;

    @Column(name="begin_minute_")
    public int beginMinute;

    @Column(name="end_hour_")
    public int endHour;

    @Column(name="end_minute_")
    public int endMinute;

}
