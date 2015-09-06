package controllers;

import models.Comment;
import models.Post;
import models.User;
import play.data.Form;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.post;

import java.util.List;

/**
 * Created by dastko on 9/6/15.
 */
public class CommentController extends Controller {

    public Result addComment(Long id) {
        Form<CommentForm> commentForm = Form.form(CommentForm.class).bindFromRequest();
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
    }

    private static User getUser() {
        return User.findByEmail(session().get("username"));
    }

    public static class CommentForm {

        @Constraints.Required
        public String comment;
    }
}
