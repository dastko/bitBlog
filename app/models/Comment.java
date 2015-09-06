package models;

import com.avaje.ebean.Model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 * Created by dastko on 9/6/15.
 */
@Entity
public class Comment extends Model {

    @Id
    private Long id;
    @ManyToOne
    private Post post;
    @ManyToOne
    private User user;
    @Column(columnDefinition = "TEXT")
    private String content;

    public static final Model.Finder<Long, Comment> find = new Model.Finder<>(
            Comment.class);

    public static List<Comment> findAllCommentsByPost(final Post post) {
        return find
                .where()
                .eq("post", post)
                .findList();
    }

    public static List<Comment> findAllCommentsByUser(final User user) {
        return find
                .where()
                .eq("user", user)
                .findList();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
