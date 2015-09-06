package controllers;

import helpers.CurrentUser;
import helpers.SessionHelper;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.edit;
import views.html.profile;

/**
 * Created by dastko on 9/4/15.
 */
public class UserController extends Controller {

    public Result showUser(String username) {
        User user = User.findByEmail(username);
        if (user == null)
            return notFound();
        else
            return ok(profile.render(user));
    }

    public Result showEditUser(String username) {
        User user = SessionHelper.currentUser(ctx());
        if(user == null) {
            return redirect("/");
        }
        if (!user.getEmail().equals(username)) {
            return badRequest();
        }
        return ok(edit.render(user));
    }

    @Security.Authenticated(CurrentUser.class)
    public Result editUser(String username) {

        DynamicForm form = Form.form().bindFromRequest();
        User user = SessionHelper.currentUser(ctx());
        if (!user.getEmail().equals(username)) {
            return badRequest();
        }
        user.setAdress(form.get("adress"));
        user.setName(form.get("name"));
        user.setPhone(form.get("phone"));
        user.update();
        return redirect("/profile/" + user.getEmail());
        }
}
