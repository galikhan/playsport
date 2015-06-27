package controllers;

import exceptions.PlaySportException;
import models.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.Image;
import org.codehaus.groovy.util.StringUtil;
import play.Play;
import play.db.jpa.JPA;
import play.libs.MimeTypes;
import play.mvc.Http;
import play.mvc.Scope;
import utils.StringUtils;

import javax.imageio.ImageIO;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 16.05.2015
 * Time: 21:01
 */

public class Manager extends Application {

    public static final String DIRECTORY_PATH = "/home/gali/Documents/playsportfiles";

    public static final int MAX_IMAGES_PER_FIELD = 20;

    public static final int MAX_NUMBER_OF_SCHEDULES = 7;

    public static void index(){
        render();
    }

    public static void addfield(){

        List cities = City.find("select c from City c order by c.name").fetch();
        List fieldTypes = FieldType.findAll();
        List coverings = Covering.findAll();
        List comforts = FieldComfort.findAll();
        Field field = Field.findById(1L);

        List weekdays = WeekDay.findAll();
        List scheduleLength = new ArrayList();
        for(int i = 0; i<MAX_NUMBER_OF_SCHEDULES; i++){
            scheduleLength.add(i);
        }

        List images = Image.find("byFieldAndImageTypeAndRemovedAndEnabled", field, Image.ImageType.ORIGINAL, false, true).fetch();
        render(cities, fieldTypes, coverings, comforts, images, weekdays, scheduleLength);
    }

    public static void save(Field field, List<Long> fieldComfort){

        List<FieldComfort> fieldComfortList = FieldComfort.find("from FieldComfort fc where fc.id in :ids")
                .setParameter("ids", fieldComfort).fetch();
        for(FieldComfort fc:fieldComfortList){
            field.getFieldComfortSet().add(fc);
        }

        field.save();
        allfields();
    }

    public static void allfields(){
        List fields = Field.findAll();
        render(fields);
    }

    public static void uploadImage(Long fieldId, String title, File file) throws IOException, PlaySportException {

        Field field = Field.findById(fieldId);
        List list = Image.find("byField", field).fetch();

        /*
        *   limit uploaded images count for each field(MAX_IMAGES_PER_FIELD)
        * */
        if(file!=null && MimeTypes.getContentType(file.getName()).startsWith("image/")){

            if(list.size()>=MAX_IMAGES_PER_FIELD){
                throw new PlaySportException("number of images exceeds limit");
            }

            String filename = file.getName();
            String extension = parseExtension(file.getName());
            String contentType = MimeTypes.getContentType(file.getName());

            String appPath = Play.applicationPath.getAbsolutePath();
            String originalPath = Play.configuration.getProperty("play.fileupload.original");
            int thumbnailHeight = Integer.parseInt(Play.configuration.getProperty("play.fileupload.thumbnail.height"));
            int optimalHeight = Integer.parseInt(Play.configuration.getProperty("play.fileupload.optimal.height"));

            String randomUUIDName = UUID.randomUUID().toString();

            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

            FileOutputStream fos = new FileOutputStream(appPath+"/"+originalPath+"/"+randomUUIDName+"."+extension);
            BufferedOutputStream bos =new BufferedOutputStream(fos);

            byte [] bytes = new byte [1024];
            while((bis.read(bytes))!=-1){
                bos.write(bytes);
            }
            bis.close();
            bos.close();
            fos.close();

            String url = originalPath+"/"+randomUUIDName+"."+extension;

            Long imageId = saveImage(field, filename, contentType, randomUUIDName, url, Image.ImageType.ORIGINAL);

            File newFile = new File(appPath+"/"+originalPath+"/"+randomUUIDName);

            if(newFile.exists()){
                //create thumbnail
                String thumbnailPath = Play.configuration.getProperty("play.fileupload.thumbnail") + "/"+randomUUIDName+"."+extension;
                createImage(newFile, thumbnailHeight, extension, thumbnailPath);
                saveImage(field, filename, contentType, randomUUIDName, thumbnailPath, Image.ImageType.THUMBNAIL);
                //create optimalopt
                String optimalPath = Play.configuration.getProperty("play.fileupload.optimal")+"/"+randomUUIDName+"."+extension;
                createImage(newFile, optimalHeight, extension, optimalPath);
                saveImage(field, filename, contentType, randomUUIDName, optimalPath, Image.ImageType.OPTIMAL);
            }

            renderJSON("{\"resultId\": \""+imageId+"\"}");
        }else{

            renderJSON("{\"result\": \"it's too bad\" "+fieldId+" | }");
//            renderJSON();
//            throw new PlaySportException("{\"result\": \"it's too bad\" "+fieldId+" | }");
        }
    }

    private static boolean createImage(File file, int height, String extension, String filename) throws IOException {

        BufferedImage image = ImageIO.read(file);
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB :image.getType();

        double ratio = (double)image.getWidth() / image.getHeight();
        int width  = (int) Math.ceil(height*ratio);

        BufferedImage thumbnailImage = new BufferedImage(width, height, type);
        Graphics2D graphics = thumbnailImage.createGraphics();
        graphics.drawImage(image, 0, 0,width, height, null);
        graphics.dispose();

        File thumbnailFile = new File(filename);
        return ImageIO.write(thumbnailImage, extension, thumbnailFile);
    }

    private static String parseExtension(String filename){
        if(!StringUtils.isEmpty(filename)){
            return filename.substring(filename.lastIndexOf(".")+1);
        }
        return null;
    }

    private static Long saveImage(Field field,
                                  String filename,
                                  String contentType,
                                  String randomUUIDName,
                                  String url,
                                  Image.ImageType imageType){
        Image image = new Image();
        image.field = field;
        image.name = filename;
        image.contentType = contentType;
        image.resourceId = randomUUIDName;
        image.url = url;
        image.imageType = imageType;
        image.save();
        return image.getId();
    }

    public static void deleteImage(Long imageId){

        Image image = Image.findById(imageId);
        JPA.em().createQuery("update Image i set i.removed = true where i.resourceId = :resourceId")
                .setParameter("resourceId", image.resourceId)
                .executeUpdate();

        renderJSON("{\"removed\":\"yes\"}");
    }

    public static void addSchedule(){

        Scope.Params param = Http.Request.current().get().params;
        //schedule-length - number of schedules

        if(!StringUtils.isEmpty(param.get("schedule-length"))){

            int scheduleLength = Integer.parseInt(param.get("schedule-length"));
            Field field = Field.findById(Long.parseLong(param.get("fieldId")));

            //delete old schedules of id
            Schedule.delete("field",field);

            for(int i=0; i<scheduleLength; i++){

                WeekDay beginDay = WeekDay.findById(Long.parseLong(param.get("begin-day-"+i)));
                WeekDay endDay = WeekDay.findById(Long.parseLong(param.get("begin-day-"+i)));

                Schedule schedule = new Schedule();
                schedule.field = field;
                schedule.beginDay = beginDay;
                schedule.endDay = endDay;

                if(!StringUtils.isEmpty(param.get("payment-" + i)))
                    schedule.payment = Integer.parseInt(param.get("payment-" + i));

                String beginTime = param.get("begin-time-" + i);
                String endTime = param.get("end-time-" + i);

                schedule.beginTime = beginTime;
                schedule.endTime = endTime;

                if(!StringUtils.isEmpty(beginTime)){
                    String [] beginHourMinute = beginTime.split(":");
                    schedule.beginHour = Integer.parseInt(beginHourMinute[0]);
                    schedule.beginMinute = Integer.parseInt(beginHourMinute[1]);
                }

                if(!StringUtils.isEmpty(endTime)){
                    String [] endHourMinute = endTime.split(":");
                    schedule.endHour = Integer.parseInt(endHourMinute[0]);
                    schedule.endMinute = Integer.parseInt(endHourMinute[1]);
                }

                schedule.create();
            }
        }
        index();
    }

}
