package controllers;

import models.User;
import play.data.Form;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import views.html.register;

/**
 * Created by dastko on 9/4/15.
 */
public class UserController extends Controller {

    public Result registration() {
        String message = flash("passwordMismatch");
        return ok(register.render(message, new Form<Registration>(Registration.class)));
    }

    public Result signin() {
        return ok(login.render(null, new Form<Registration>(Registration.class)));
    }

    public Result signup() {

        Form<Registration> signUpForm = Form.form(Registration.class).bindFromRequest();
        if (signUpForm.hasErrors()) {
            return ok(register.render("", signUpForm));
        }
        Registration newUser = signUpForm.get();
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
            user.save();
            session().clear();
            session("username", newUser.email);
            return redirect("/");
        }
    }

    public Result login() {
        Form<Registration> loginForm = Form.form(Registration.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return ok(login.render("", loginForm));
        }
        Registration loggingInUser = loginForm.get();
        User user = User.findByEmailAndPassword(loggingInUser.email, loggingInUser.password);
        if (user == null) {
            return ok(login.render(loginForm.errorsAsJson().asText(), loginForm));
        } else {
            session().clear();
            session("username", loggingInUser.email);
            return redirect("/");
        }
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
