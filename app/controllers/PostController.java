package controllers;

import helpers.CurrentUser;
import helpers.SessionHelper;
import models.Post;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.newpost;

/**
 * Created by dastko on 9/4/15.
 */
public class PostController extends Controller {

    @Security.Authenticated(CurrentUser.class)
    public Result newPost() {
        return ok(newpost.render(new Form<>(Post.class)));
    }

    @Security.Authenticated(CurrentUser.class)
    public Result addPost() {
        Form<Post> postForm = Form.form(Post.class).bindFromRequest();

        if (postForm.hasErrors()) {
            return ok(newpost.render(postForm));
        }
        Post newPost = new Post();
        newPost.setTitle(postForm.get().getTitle());
        newPost.setContent(postForm.get().getContent());
        newPost.save();
        flash("postAdded", "Post added successfully");
        return redirect("/");
    }

    private static User getUser() {
        return User.findByEmail(SessionHelper.currentUser(ctx()).getEmail());
    }
}
