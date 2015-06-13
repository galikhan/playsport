package controllers;

import models.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.UUID;

import models.Image;
import org.hibernate.annotations.GenericGenerator;
import play.Play;
import play.libs.Files;
import play.libs.MimeTypes;
import utils.Common;

import javax.imageio.ImageIO;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 16.05.2015
 * Time: 21:01
 */

public class Manager extends Application {

    public static final String DIRECTORY_PATH = "/home/gali/Documents/playsportfiles";

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

    public static void uploadImage(Long fieldId, String title, File file) throws IOException {

        if(file!=null && MimeTypes.getContentType(file.getName()).startsWith("image/")){

            String appPath = Play.applicationPath.getAbsolutePath();
            String originalPath = Play.configuration.getProperty("play.fileupload.original");
            int thumbnailHeight = Integer.parseInt(Play.configuration.getProperty("play.fileupload.thumbnail.height"));
            int optimalHeight = Integer.parseInt(Play.configuration.getProperty("play.fileupload.optimal.height"));

            String randomUUIDName = UUID.randomUUID().toString();

            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);

            FileOutputStream fos = new FileOutputStream(appPath+"/"+originalPath+"/"+randomUUIDName);
            BufferedOutputStream bos =new BufferedOutputStream(fos);

            byte [] bytes = new byte [1024];
            while((bis.read(bytes))!=-1){
                bos.write(bytes);
            }
            bis.close();
            bos.close();
            fos.close();

            Image image = new Image();
            image.field = Field.findById(fieldId);
            image.name = file.getName();
            image.contentType = MimeTypes.getContentType(file.getName());
            System.out.println(MimeTypes.getMimeType(file.getName())+" |");
            image.resourceId = randomUUIDName;
            image.url = originalPath+"/"+randomUUIDName;
            image.save();

            File newFile = new File(appPath+"/"+originalPath+"/"+randomUUIDName);
            String extension = parseExtension(file.getName());

            if(newFile.exists()){
                //create thumbnail
                String thumbnailPath = Play.configuration.getProperty("play.fileupload.thumbnail");
                createImage(newFile, thumbnailHeight, extension, thumbnailPath+"/"+randomUUIDName);
                //create optimalopt
                String optimalPath = Play.configuration.getProperty("play.fileupload.optimal");
                createImage(newFile, optimalHeight, extension, optimalPath+"/"+randomUUIDName);
            }

            index();
        }else{
            //throw exception that file is not image
        }
        index();
    }

    public static boolean createImage(File file, int height, String extension, String filename) throws IOException {

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
        if(!Common.isEmpty(filename)){
            return filename.substring(filename.lastIndexOf(".")+1);
        }
        return null;
    }
}
