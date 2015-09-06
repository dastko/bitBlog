package controllers;

import helpers.CurrentUser;
import helpers.SessionHelper;
import models.Comment;
import models.Post;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.newpost;
import views.html.post;

import java.util.List;

/**
 * Created by dastko on 9/4/15.
 */
public class PostController extends Controller {

    @Security.Authenticated(CurrentUser.class)
    public Result newPost() {
        List <Post> postList = Post.findBlogPostsByUser(SessionHelper.currentUser(ctx()));
        return ok(newpost.render(new Form<>(Post.class), postList));
    }

    @Security.Authenticated(CurrentUser.class)
    public Result addPost() {
        Form<Post> postForm = Form.form(Post.class).bindFromRequest();
        List <Post> postList = Post.findBlogPostsByUser(SessionHelper.currentUser(ctx()));
        if (postForm.hasErrors()) {
            return badRequest(newpost.render(postForm, postList));
        }
        Post newPost = new Post();
        newPost.setTitle(postForm.get().getTitle());
        newPost.setContent(postForm.get().getContent());
        newPost.setUser(getUser());
        newPost.save();
        flash("postAdded", "Post added successfully");
        return redirect("/");
    }

    @Security.Authenticated(CurrentUser.class)
    public Result deletePost(Long id){
        Post post = Post.findBlogPostById(id);
        if(post == null){
            return badRequest();
        }
        if(SessionHelper.currentUser(ctx()).getId() == post.getUser().getId()){
            post.delete();
            return redirect("/");
        }
        return badRequest();
    }

    public Result getPost(Long id){
        Post newPost = Post.findBlogPostById(id);
        List<Comment> commentList = Comment.findAllCommentsByPost(newPost);
        if(newPost == null){
            return badRequest();
        }
        return ok(post.render(newPost, commentList, new Form<>(CommentController.CommentForm.class)));
    }

    private static User getUser() {
        return User.findByEmail(SessionHelper.currentUser(ctx()).getEmail());
    }
}
