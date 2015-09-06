package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    @Constraints.Required()
    @Constraints.Email
    private String email;
    @Constraints.MaxLength(255)
    @Constraints.Required
    private String name;
    @Column(length = 64, nullable = false)
    private byte[] password;
    @Formats.DateTime(pattern = "dd/MM/yyyy")
    @Column(columnDefinition = "datetime")
    private Date registration = new Date();
    @Constraints.MaxLength(150)
    private String adress;
    @Constraints.MaxLength(150)
    private String phone;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Post> posts;


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

    public void setId(Long id) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getRegistration() {
        return registration;
    }

}
