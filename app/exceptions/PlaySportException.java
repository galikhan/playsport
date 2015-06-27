package exceptions;

import java.io.IOException;

/**
 * @author: Galikhan Khamitov <galikhin.khamitov@metaphor.kz>
 * Date: 21.06.2015
 * Time: 19:16
 */
public class PlaySportException extends IOException{

    public PlaySportException(String message) {
        super(message);
    }
}
