package helpers;

import models.User;
import play.mvc.Http;

/**
 * Created by dastko on 9/4/15.
 */
public class SessionHelper {

    public static User currentUser(Http.Context ctx){
        String username = ctx.session().get("username");
        if(username == null)
            return null;
        return User.findByEmail(username);
    }
}
