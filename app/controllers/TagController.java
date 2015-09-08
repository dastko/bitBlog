package controllers;

import models.Tag;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by dastko on 9/8/15.
 */
public class TagController extends Controller {


    public Result postsByTag(String name){
        return ok(Json.toJson(Tag.findByName(name)));
    }

    public Result getTags(){
        return ok(Json.toJson(Tag.getCloud()));
    }


}
