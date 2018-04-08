package edu.sunhacks.recyclehub.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sunhacks.recyclehub.models.ProductDetails;
import edu.sunhacks.recyclehub.models.User;
import edu.sunhacks.recyclehub.models.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
public class UserController {

    private UserRepository userRep;

    public UserController(UserRepository userRep){
        this.userRep = userRep;
    }

    @RequestMapping(value = "/authenticateUser", method = RequestMethod.POST, produces = "text/plain")
    public @ResponseBody String authenticateUser(@RequestBody String json, HttpServletRequest request, HttpServletResponse response) {
        boolean responseDB = false;
        System.out.println(json);
        try {

            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> map = new HashMap<String, Object>();

            // convert JSON string to Map
            map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});

            User uCred = new User();
            uCred.setUsername((String)map.getOrDefault("username", ""));
            uCred.setPassword((String)map.getOrDefault("password", ""));
            User userDatabase = userRep.findByUsername(uCred.getUsername());
            responseDB = uCred.getPassword().equals(userDatabase.getPassword());
            if(responseDB){
                HttpSession session = request.getSession();
                session.setAttribute("username", uCred.getUsername());
            }
        } catch(Exception e){
            e.printStackTrace();
        }finally{
            return "{\"status\" : \"" + responseDB + "\"}";
        }
    }

    @RequestMapping(value = "/getAllHistory", method = RequestMethod.GET, produces = "text/plain")
    public @ResponseBody String getAllHistory(HttpServletRequest request, HttpServletResponse response) {
        String responseDB = "{\"status\" : \"" + "User not logged in" + "\"}";;
        ObjectMapper mapper = new ObjectMapper();
        try {
            HttpSession session = request.getSession(false);

            if(session != null) {
                User userHistory = userRep.findByUsername((String)session.getAttribute("username"));
                responseDB = "{ \"recycledProducts\":\"" + mapper.writeValueAsString(userHistory.getRecycledProdDetails()) + "\",";
                responseDB += "\"stackedProducts\":\"" +mapper.writeValueAsString(userHistory.getStackedProdDetails()) +  "\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            return responseDB;
        }
    }

    @RequestMapping(value = "/getStackedHistory", method = RequestMethod.GET, produces = "text/plain")
    public @ResponseBody String getStackedHistory(HttpServletRequest request, HttpServletResponse response) {
        String responseDB = "{\"status\" : \"" + "User not logged in" + "\"}";;
        ObjectMapper mapper = new ObjectMapper();
        try {
            HttpSession session = request.getSession(false);

            if(session != null) {
                User userHistory = userRep.findByUsername((String)session.getAttribute("username"));
                responseDB = "{ \"stackedProducts\":\"" +mapper.writeValueAsString(userHistory.getStackedProdDetails()) +  "\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            return responseDB;
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

    @RequestMapping(value = "/addToStack", method = RequestMethod.POST,consumes="application/json",produces="application/json")
    public @ResponseBody String addToStack(@RequestBody List<ProductDetails> stackProdList, HttpServletRequest request, HttpServletResponse response) {

        String responseDB = "{\"status\" : \"" + "User not logged in" + "\"}";

        try {
            System.out.println(stackProdList.toString());
            Map<String, ProductDetails> prodIdToProdDetailsMap = new HashMap<String, ProductDetails>();
            for(ProductDetails prodDet: stackProdList){
                ProductDetails prodD = prodIdToProdDetailsMap.getOrDefault(prodDet.getPid(), new ProductDetails());
                prodD.setPid(prodDet.getPid());
                prodD.setQuantity(prodD.getQuantity()+prodDet.getQuantity());
                prodD.setAmount(prodD.getAmount()+prodDet.getAmount());
                prodIdToProdDetailsMap.put(prodDet.getPid(), prodD);
            }

            HttpSession session = request.getSession(false);
            if(session != null) {
                responseDB = "{\"status\" : \"" + false + "\"}";
                User userHistory = userRep.findByUsername((String)session.getAttribute("username"));
                List<ProductDetails> databaseStackProdList = userHistory.getStackedProdDetails();

                if(databaseStackProdList  != null) {
                    Iterator stackIter = databaseStackProdList.iterator();
                    while (stackIter.hasNext()) {
                        ProductDetails prodDet = (ProductDetails) stackIter.next();
                        ProductDetails prodD = prodIdToProdDetailsMap.getOrDefault(prodDet.getPid(), new ProductDetails());
                        prodDet.setQuantity(prodD.getQuantity() + prodDet.getQuantity());
                        prodDet.setAmount(prodD.getAmount() + prodDet.getAmount());
                        prodIdToProdDetailsMap.remove(prodDet.getPid());
                    }
                }else{
                    databaseStackProdList = new ArrayList<ProductDetails>();
                }
                databaseStackProdList.addAll(prodIdToProdDetailsMap.values());
                userRep.save(userHistory);
                responseDB = "{\"status\" : \"" + true + "\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            return responseDB;
        }
    }

    @RequestMapping(value = "/recycle", method = RequestMethod.POST, consumes="application/json", produces="application/json")
    public @ResponseBody String recycle(@RequestBody List<ProductDetails> stackProdList, HttpServletRequest request, HttpServletResponse response) {
        String responseDB = "{\"status\" : \"" + "User not logged in" + "\"}";
        Map<String, ProductDetails> prodIdToProdDetailsMap = new HashMap<String, ProductDetails>();
        for(ProductDetails prodDet: stackProdList){
            ProductDetails prodD = prodIdToProdDetailsMap.getOrDefault(prodDet.getPid(), new ProductDetails());
            prodD.setPid(prodDet.getPid());
            prodD.setQuantity(prodD.getQuantity()+prodDet.getQuantity());
            prodD.setAmount(prodD.getAmount()+prodDet.getAmount());
            prodIdToProdDetailsMap.put(prodDet.getPid(), prodD);
        }
        try {
            HttpSession session = request.getSession(false);
            if(session != null) {
                responseDB = "{\"status\" : \"" + false + "\"}";
                User userHistory = userRep.findByUsername((String)session.getAttribute("username"));
                Iterator stackIter = userHistory.getStackedProdDetails().iterator();
                while(stackIter.hasNext()){
                    ProductDetails prodDet = (ProductDetails)stackIter.next();
                    ProductDetails prodD = prodIdToProdDetailsMap.getOrDefault(prodDet.getPid(), new ProductDetails());
                    prodDet.setQuantity(prodDet.getQuantity() - prodD.getQuantity());
                    prodDet.setAmount(prodDet.getAmount() - prodD.getAmount());
                    if(prodDet.getQuantity()<=0){
                        stackIter.remove();
                    }
                }
                Iterator recycleIter = userHistory.getRecycledProdDetails().iterator();
                while(recycleIter.hasNext()){
                    ProductDetails prodDet = (ProductDetails)recycleIter.next();
                    ProductDetails prodD = prodIdToProdDetailsMap.getOrDefault(prodDet.getPid(), new ProductDetails());
                    prodDet.setQuantity(prodDet.getQuantity() + prodD.getQuantity());
                    prodDet.setAmount(prodDet.getAmount() + prodD.getAmount());
                    prodIdToProdDetailsMap.remove(prodDet.getPid());
                }
                userHistory.getRecycledProdDetails().addAll(prodIdToProdDetailsMap.values());
                userRep.save(userHistory);
                responseDB = "{\"status\" : \"" + true + "\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            return responseDB;
        }
    }
}
