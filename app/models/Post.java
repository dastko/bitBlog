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
    private Long id;
    @Column(columnDefinition = "TEXT")
    @Constraints.Required
    private String content;
    @ManyToOne
    private User user;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;
    @Formats.DateTime(pattern="dd/MM/yyyy")
    @Column(columnDefinition = "datetime")
    private Date date = new Date();
    private String title;

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
        return find.orderBy("date desc").findList();
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

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }
}
