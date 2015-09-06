package controllers;

import helpers.CurrentUser;
import helpers.SessionHelper;
import models.Comment;
import models.Post;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.post;

import java.util.List;

/**
 * Created by dastko on 9/6/15.
 */
public class CommentController extends Controller {

    final static Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Security.Authenticated(CurrentUser.class)
    public Result addComment(Long id) {
        Form<CommentForm> commentForm = Form.form(CommentForm.class).bindFromRequest();
        try {
            Post newPost = Post.findBlogPostById(id);
            List<Comment> commentList = Comment.findAllCommentsByPost(newPost);
            if (commentForm.hasErrors()) {
                return badRequest(post.render(newPost, commentList, commentForm));
            } else {
                Comment newComment = new Comment();
                Post post = Post.findBlogPostById(id);
                post.save();
                newComment.setPost(post);
                newComment.setContent(commentForm.get().comment);
                newComment.setUser(getUser());
                newComment.save();
                flash("commentAdded", "Comment Successfully Added!");
                return redirect("/post/" + post.getId());
            }
        } catch (Exception e){
            logger.warn("Comment Failed" + e);
            return redirect("/login");
        }
    }

    @Security.Authenticated(CurrentUser.class)
    public Result deleteComment(Long id){
        User user = SessionHelper.currentUser(ctx());
        if(user == null){
            return redirect("/login");
        }
        try{
            Comment comment = Comment.findById(id);
            if(comment == null){
                return notFound(views.html.errorPages.notFound.render("Comment not found"));
            }
            if(user.getId() == comment.getUser().getId()){
                comment.delete();
                return redirect("/profile/" + user.getEmail());
            }
        } catch (Exception e){
            logger.warn("Delete Comment failed" + e);
        }
        return badRequest(views.html.errorPages.badRequest.render("Bad Request"));
    }

    private static User getUser() {
        return User.findByEmail(session().get("username"));
    }

    public static class CommentForm {
        @Constraints.Required
        public String comment;
    }
}
