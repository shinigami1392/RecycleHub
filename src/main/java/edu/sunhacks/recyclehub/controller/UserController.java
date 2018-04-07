package edu.sunhacks.recyclehub.controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sunhacks.recyclehub.models.User;
import edu.sunhacks.recyclehub.models.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private UserRepository userRep;

    public UserController(UserRepository userRep){
        this.userRep = userRep;
    }

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
            System.out.println("responseDB1"+responseDB);
            User userDatabase = userRep.findByUsername(uCred.getUsername());
            responseDB = uCred.getPassword().equals(userDatabase.getPassword());
            System.out.println("responseDB2"+responseDB);
            if(responseDB){
                HttpSession session = request.getSession();
                session.setAttribute("username", uCred.getUsername());
            }
        } catch(Exception e){
            System.out.println("responseDB6"+responseDB);
            e.printStackTrace();
        }finally{
            System.out.println("responseDB7"+responseDB);
            return "{\"status\" : \"" + responseDB + "\"}";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = "text/plain")
    public @ResponseBody String logout(HttpServletRequest request, HttpServletResponse response) {
        String responseDB = "User not logged in";
        try {
            HttpSession session = request.getSession(false);

            if(session != null) {
                session.invalidate();
                responseDB = "Logged out successfully";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            return "{\"status\" : \"" + responseDB + "\"}";
        }
    }
}
