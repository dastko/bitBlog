package helpers;

import models.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by dastko on 9/4/15.
 */
public class CurrentUser extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context ctx) {
        if(!ctx.session().containsKey("username"))
            return null;
        String username = ctx.session().get("username");
        User u = User.findByEmail(username);
        if (u != null)
            return u.getEmail();
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return redirect("/login");
    }
}
