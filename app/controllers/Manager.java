package controllers;

import models.*;

import java.util.List;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 16.05.2015
 * Time: 21:01
 */
public class Manager extends Application {
    public static void index(){
        render();
    }

    public static void addfield(){
//        List cities = City.find("order_by","name").fetch();
        List cities = City.find("select c from City c order by c.name").fetch();
        List fieldTypes = FieldType.findAll();
        List coverings = Covering.findAll();
        List comforts = FieldComfort.findAll();
        render(cities, fieldTypes, coverings, comforts);
    }

    public static void save(Field field){
        System.out.println(field.city.id);
        System.out.println(field.name);
        field.save();
    }

    public static void allfields(){
        List fields = Field.findAll();
        render(fields);
    }
}
