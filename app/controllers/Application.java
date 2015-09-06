package controllers;

import helpers.MailHelper;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.Play;
import play.data.Form;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.UUID;

public class Application extends Controller {

    final static Logger logger = LoggerFactory.getLogger(Application.class);
    private static String url = Play.application().configuration().getString("url");

    public Result index() {
        String message = flash("postAdded");
        return ok(index.render(message));
    }

    public Result registration() {
        String message = flash("passwordMismatch");
        return ok(register.render(message, new Form<>(Registration.class)));
    }

    public Result signin() {
        return ok(login.render(null, new Form<>(Registration.class)));
    }

    public Result signup() {
        Form<Registration> signUpForm = Form.form(Registration.class).bindFromRequest();
        if (signUpForm.hasErrors()) {
            return ok(register.render("", signUpForm));
        }
        Registration newUser = signUpForm.get();
        try {
            User existingUser = User.findByEmail(newUser.email);
            if (existingUser != null) {
                return ok(register.render("User already exist", signUpForm));
            }
            if (!newUser.password.equals(signUpForm.data().get("confirmPassword"))) {
                flash("passwordMismatch", "Password does not match the confirm password");
                return redirect("/registration");
            } else {
                User user = new User();
                user.setEmail(newUser.email);
                user.setPassword(newUser.password);
                user.setToken(UUID.randomUUID().toString());
                user.save();
                // Sending Email To user
                String host = url + "validate/" + user.getToken();
                MailHelper.send(user.getEmail(), host);
                session().clear();
                session("username", newUser.email);
                return redirect("/");
            }
        } catch (Exception e) {
            logger.warn("Registration failed: " + e);
            return redirect("/login");
        }
    }

    public Result login() {
        Form<Registration> loginForm = Form.form(Registration.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return ok(login.render("", loginForm));
        }
        Registration loggingInUser = loginForm.get();
        try {
            User user = User.findByEmailAndPassword(loggingInUser.email, loggingInUser.password);
            if (user == null) {
                return ok(login.render(loginForm.errorsAsJson().asText(), loginForm));
            } else {
                session().clear();
                session("username", loggingInUser.email);
                return redirect("/");
            }
        } catch (Exception e) {
            logger.warn("login Failed" + e);
            return redirect("/login");
        }
    }

    public Result logout() {
        session().clear();
        return redirect("/");
    }

    public static class Registration {
        @Constraints.Required
        @Constraints.Email
        public String email;
        @Constraints.Required
        @Constraints.MinLength(6)
        public String password;
    }
}
