package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by dastko on 9/4/15.
 */
@Entity
public class Post extends Model {

    @Id
    public Long id;
    @Constraints.MaxLength(255)
    @Constraints.Required
    private String title;
    @Column(columnDefinition = "TEXT")
    @Constraints.Required
    public String content;
    @ManyToOne
    public User user;
    @Formats.DateTime(pattern="dd/MM/yyyy")
    @Column(columnDefinition = "datetime")
    public Date date = new Date();

    public static final Model.Finder<Long, Post> find = new Model.Finder<>(
            Post.class);

    public static List<Post> findBlogPostsByUser(User user) {
        return find
                .where()
                .eq("user", user)
                .findList();
    }

    public static Post findBlogPostById(Long id) {
        return find
                .where()
                .eq("id", id)
                .findUnique();
    }

    public static List<Post> findAllPosts(){
        return find.findList();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
