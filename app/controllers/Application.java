package controllers;

import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public Result index() {
        String message = flash("postAdded");
        return ok(index.render(message));
    }

}
