package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;

/**
 * Created by dastko on 9/17/15.
 */
public class SearchController extends Controller {

    public Result search(String input){
        List <User> usersByName = User.searchTableByInputString(input);

//        List<User> users = User.searchByEmail(input);
//        Set<User> finalUsers = new HashSet<>(users);
//        finalUsers.addAll(usersByName);
//        users.clear();
//        users.addAll(finalUsers);

        ObjectNode result = Json.newObject();
        ArrayNode array = Json.newArray();
        array.add(result);
        if(usersByName.size() == 0){
            result.put("email", "User Not Found");
            result.put("something", "dadada");
            System.out.println(result);
            return ok(array);
        }
        return ok(Json.toJson(usersByName));
    }
}
