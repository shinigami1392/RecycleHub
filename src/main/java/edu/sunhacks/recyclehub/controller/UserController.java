package edu.sunhacks.recyclehub.controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sunhacks.recyclehub.models.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @RequestMapping(value = "/authenticateUser", method = RequestMethod.POST, produces = "text/plain")
    public @ResponseBody
    String authenticateUser(@RequestBody String json, HttpServletRequest request, HttpServletResponse response) {
        boolean responseDB = false;
        try {

            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> map = new HashMap<String, Object>();

            // convert JSON string to Map
            map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});

            User uCred = new User();
            uCred.setUsername((String)map.getOrDefault("username", ""));
            uCred.setPassword((String)map.getOrDefault("password", ""));

//            responseDB = userService.authenticateUser(uCred);
//            if(responseDB){
//                HttpSession session = request.getSession();
//                PLPUserDB.getInstance().registerUserSession(uCred.getUsername(), session, session.getId());
//            }
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            return "{\"status\" : \"" + responseDB + "\"}";
        }
    }
}
