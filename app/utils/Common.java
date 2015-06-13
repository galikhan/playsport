package utils;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 13.06.2015
 * Time: 21:21
 */
public class Common {

    public static boolean isEmpty(String filename){
        if(filename == null || filename.isEmpty() || filename.trim().equals(""))
            return true;
        return false;
    }

}
