package controllers;

import helpers.CurrentUser;
import helpers.SessionHelper;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Security.Authenticated(CurrentUser.class)
public class UserController extends Controller {

    final static Logger logger = LoggerFactory.getLogger(UserController.class);

    public Result showUserPage(String username) {
        try {
            User user = SessionHelper.currentUser(ctx());
            if(!user.getEmail().contains(username)){
                badRequest(views.html.errorPages.badRequest.render("Mismatch"));
            }
            return ok(profile.render(user));
        } catch (Exception e) {
            logger.warn("User not found: " + e);
            return notFound(views.html.errorPages.notFound.render("User Not Found!"));
        }
    }

    public Result showEditPage(String username) {
        User user = SessionHelper.currentUser(ctx());
        if (user == null) {
            return redirect("/login");
        }
        if (!user.getEmail().equals(username)) {
            return badRequest(views.html.errorPages.badRequest.render("Mismatch"));
        }
        return ok(edit.render(user));
    }

    public Result editUser(String username) {
        DynamicForm form = Form.form().bindFromRequest();
        User user = SessionHelper.currentUser(ctx());
        if (user == null) {
            return redirect("/login");
        }
        if (!user.getEmail().equals(username)) {
            return badRequest(views.html.errorPages.badRequest.render("Mismatch"));
        }
        try {
            user.setAdress(form.get("adress"));
            user.setName(form.get("name"));
            user.setPhone(form.get("phone"));
            user.update();
            return redirect("/profile/" + user.getEmail());
        } catch (Exception e) {
            logger.warn("Update failed:" + e);
            return redirect("/login");
        }
    }
}
