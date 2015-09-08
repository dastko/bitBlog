package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by dastko on 9/8/15.
 */
@Entity
public class Tag extends Model {

    @Id
    public Long id;
    public String name;
    @ManyToOne
    @JsonIgnore
    public Post post;

    public static final Model.Finder<Long, Tag> find = new Model.Finder<>(
            Tag.class);

    private Tag (String name){
        this.name = name;
    }

    public static Tag findOrCreate(String name){
        Tag tag = find.where().eq("name", name).setMaxRows(1).findUnique();
        if(tag == null){
            tag = new Tag(name);
        }
        return tag;
    }

    public static Tag findByName(String name){
        return find.where().eq("name", name).findUnique();
    }

    public static List<Tag> getCloud (){
        List<Tag> cloud = find.findList();
        return cloud;
    }
}
