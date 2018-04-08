package edu.sunhacks.recyclehub.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sunhacks.recyclehub.models.Product;
import edu.sunhacks.recyclehub.models.ProductDetails;
import edu.sunhacks.recyclehub.models.User;
import edu.sunhacks.recyclehub.models.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@RestController
public class UserController {

    private UserRepository userRep;

    public UserController(UserRepository userRep){
        this.userRep = userRep;
    }

    @CrossOrigin(origins="http://localhost:8080")
    @RequestMapping(value = "/authenticateUser", method = RequestMethod.POST, produces = "text/plain")
    public @ResponseBody String authenticateUser(@RequestBody String json, HttpServletRequest request, HttpServletResponse response) {
    	System.err.println(json);
        boolean responseDB = false;
        try {

            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> map = new HashMap<String, Object>();

            // convert JSON string to Map
            map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});

            User uCred = new User();
            uCred.setUsername((String)map.getOrDefault("username", ""));
            uCred.setPassword((String)map.getOrDefault("password", ""));
            User userDatabase = userRep.findByUsername(uCred.getUsername());
            if(userDatabase!= null){
                responseDB = uCred.getPassword().equals(userDatabase.getPassword());
                if(responseDB){
                    HttpSession session = request.getSession();
                    session.setAttribute("username", uCred.getUsername());
                }
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
                prodD.setProductName(prodDet.getProductName());
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
                        prodDet.setProductName(prodD.getProductName());
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
            prodD.setProductName(prodDet.getProductName());
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
                    prodDet.setProductName(prodD.getProductName());
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
                    prodDet.setProductName(prodD.getProductName());
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

    @RequestMapping(value = "/calculateRecycleValue", method = RequestMethod.POST,consumes="application/json",produces="application/json")
    public @ResponseBody String calculateRecycleValue(@RequestBody String json, HttpServletRequest request, HttpServletResponse response) {
        String responseDB = "{\"status\" : \"Product not found\"}";

        try {

            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> map = new HashMap<String, Object>();

            // convert JSON string to Map
            map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});

            String pid = (String)map.getOrDefault("pid", "");
            Integer quantity = Integer.parseInt((String)map.getOrDefault("quantity", ""));

            try {

                URL url = new URL("http://localhost:8080/pids"+"?pid="+pid);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

                String output;
                System.out.println("Output from Server .... \n");
                StringBuilder sb = new StringBuilder();
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }

                conn.disconnect();
                ArrayList<Product> prodList = mapper.readValue(sb.toString(), new TypeReference<List<Product>>(){});

                if(prodList.size() > 0){
                    Product prod = prodList.get(0);
                    map.put("productName", prod.getProductName());
                    map.put("garbageGenerated", Double.parseDouble(prod.getGarbageGenerated()) * quantity);
                    map.put("recycleableGarbage", Double.parseDouble(prod.getRecycleableGarbage()) * quantity);
                    map.put("landfillGarbage", Double.parseDouble(prod.getLandfillGarbage()) * quantity);
                    map.put("value", Double.parseDouble(prod.getStates().get(0).getValue()) * quantity);
                    responseDB = mapper.writeValueAsString(map);
                }
            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }


        } catch(Exception e){
            e.printStackTrace();
        }finally{
            return responseDB;
        }
    }
}
