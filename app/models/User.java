package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dastko on 9/4/15.
 */
@Entity
public class User extends Model{

    @Id
    private Long id;
    @Column(unique = true)
    @Constraints.MaxLength(255)
    @Constraints.Required
    @Constraints.Email
    private String email;
    @Constraints.MaxLength(255)
    @Constraints.Required
    private String name;
    @Column(length = 64, nullable = false)
    private byte[] password;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Post> posts;
    @Formats.DateTime(pattern = "dd/MM/yyyy")
    @Column(columnDefinition = "datetime")
    private Date registration = new Date();
    @Constraints.MaxLength(10)
    private String gender;

    public static final Finder<Long, User> find = new Finder<>(
            User.class);

    private static byte[] hashPassword(String value) {
        try {
            return MessageDigest.getInstance("SHA-512").digest(value.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        if (User.findByEmail(email) != null) {
            errors.add(new ValidationError("email", "This e-mail is already registered."));
        }
        return errors.isEmpty() ? null : errors;
    }

    public static User findByEmailAndPassword(String email, String password) {
        return find
                .where()
                .eq("email", email.toLowerCase())
                .eq("password", hashPassword(password))
                .findUnique();
    }

    public static User findByEmail(String email){
        return find.where()
                .eq("email", email.toLowerCase()).
                        findUnique();
    }

    public static User findById(Long id){
        return find.byId(id);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
