package controllers;

import helpers.CurrentUser;
import helpers.SessionHelper;
import models.Comment;
import models.Post;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    final static Logger logger = LoggerFactory.getLogger(PostController.class);

    @Security.Authenticated(CurrentUser.class)
    public Result showNewPost() {
        try {
            List<Post> postList = Post.findBlogPostsByUser(SessionHelper.currentUser(ctx()));
            return ok(newpost.render(new Form<>(Post.class), postList));
        } catch (Exception e){
            logger.warn("Post: " + e);
            return redirect("/login");
        }
    }

    @Security.Authenticated(CurrentUser.class)
    public Result addPost() {
        Form<Post> postForm = Form.form(Post.class).bindFromRequest();
        try {
            List <Post> postList = Post.findBlogPostsByUser(SessionHelper.currentUser(ctx()));
            if (postForm.hasErrors()) {
                return badRequest(newpost.render(postForm, postList));
            }
            Post newPost = postForm.get();
            newPost.setUser(getUser());
            newPost.save();
            //Same
//            Post newPost = new Post();
//            newPost.setTitle(postForm.get().getTitle());
//            newPost.setContent(postForm.get().getContent());
//            newPost.setUser(getUser());
//            newPost.save();
            flash("postAdded", "Post added successfully");
            return redirect("/");
        }catch (Exception e){
            logger.warn("Add Post Failed" + e);
            return redirect("/login");
        }
    }

    @Security.Authenticated(CurrentUser.class)
    public Result deletePost(Long id){
        Post post = Post.findBlogPostById(id);
        if(post == null){
            return notFound(views.html.errorPages.badRequest.render("Post Not Found!"));
        }
        if(SessionHelper.currentUser(ctx()).getId() == post.getUser().getId()){
            post.delete();
            return redirect("/");
        }
        return badRequest(views.html.errorPages.badRequest.render("Mismatch"));
    }

    public Result getPost(Long id){
        try {
            Post newPost = Post.findBlogPostById(id);
            List<Comment> commentList = Comment.findAllCommentsByPost(newPost);
            if(newPost == null){
                return notFound(views.html.errorPages.badRequest.render("Post Not Found!"));
            }
            return ok(post.render(newPost, commentList, new Form<>(CommentController.CommentForm.class)));
        } catch (Exception e){
            logger.warn("Post Not Found:" + e);
            return redirect("/login");
        }
    }

    private static User getUser() {
        return User.findByEmail(SessionHelper.currentUser(ctx()).getEmail());
    }
}
