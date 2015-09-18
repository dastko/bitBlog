package helpers;

import models.User;
import org.apache.commons.codec.binary.Base64;
import play.mvc.*;
import play.mvc.Security;

import java.nio.charset.Charset;

/**
 * Created by dastko on 9/9/15.
 */
public class BasicAuthenticator extends Security.Authenticator {


    private static final String AUTHORIZATION =
            "Authorization";

    @Override
    public String getUsername(Http.Context ctx) {
        try {
            String token = SessionHelper.currentUser(ctx).getToken();
            String encryptedToken = Base64.encodeBase64String(token.getBytes());
            String authHeader = ctx.request().getHeader(AUTHORIZATION);
            if (authHeader == null) {
                ctx.response().setHeader(AUTHORIZATION,
                        encryptedToken);
            }
            byte[] decodedAuth = Base64.
                    decodeBase64(encryptedToken);
            String decoded = new String(decodedAuth, Charset.forName("UTF-8"));
            User user = User.findUserByToken(decoded);
            if (user != null && user.getEmail().equals(SessionHelper.currentUser(ctx).getEmail())) {
                return user.getEmail();
            }
            return null;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return unauthorized();
    }


}

