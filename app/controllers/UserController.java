package controllers;

import models.Post;
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
        return ok(register.render(new Form<>(Registration.class)));
    }

    public Result signin() {
        return ok(login.render("Some String"));
    }

    public Result signup() {

        Form<Registration> signUpForm = Form.form(Registration.class).bindFromRequest();
        if (signUpForm.hasErrors()) {
            return ok(register.render(signUpForm));
        }
        Registration newUser = signUpForm.get();
        User existingUser = User.findByEmail(newUser.email);
        if (existingUser != null) {
            return badRequest(signUpForm.errorsAsJson());
        }
        if (!newUser.password.equals(signUpForm.data().get("confirmPassword"))) {
            return badRequest(signUpForm.errorsAsJson());
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
            return badRequest(loginForm.errorsAsJson());
        }
        Registration loggingInUser = loginForm.get();
        User user = User.findByEmailAndPassword(loggingInUser.email, loggingInUser.password);
        if (user == null) {
            return badRequest(loginForm.errorsAsJson());
        } else {
            session().clear();
            session("username", loggingInUser.email);
            return ok("success", "User successfully login");
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
