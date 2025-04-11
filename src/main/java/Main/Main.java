package Main;

import Classes.Dish;
import Classes.Drinks;
import Classes.ObjectFromMenu;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main {
    public static List<ObjectFromMenu> menuItems;
    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = Main.class.getResourceAsStream("/menu.json");
        if (inputStream == null) {
            throw new FileNotFoundException("Resource not found: menu.json");
        }

        JsonNode rootNode = mapper.readTree(inputStream);

        menuItems = new ArrayList<>();

        for (JsonNode node : rootNode) {

            String type = node.get("type").asText();
            String name = node.get("name").asText();
            double cost = node.get("cost").asDouble();
            String timeString = node.get("time to cook").asText();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Time timeToCook = null;
            try {
                Date parsedDate = sdf.parse(timeString);
                timeToCook = new Time(parsedDate.getTime());
            } catch (Exception ex) {
                continue;
            }

//            if ("dish".equals(type)) {
//                int grams = node.get("grams").asInt();
//                menuItems.add(new Dish(name,cost, grams,timeToCook ));
//            } else if ("drinks".equals(type)) {
//                int milliliters = node.get("milliliters").asInt();
//                menuItems.add(new Drinks(name,cost,milliliters,timeToCook));
//            }
            menuItems.add(WhatTypeIsObjectReturn(node, type, name, cost, timeToCook));
        }

        for (Object item : menuItems) {
            System.out.println(item.toString());
        }
    }

    private static ObjectFromMenu WhatTypeIsObjectReturn(JsonNode node, String type, String name, Double cost, Time timeToCook){
       if ("dish".equals(type)){
           int grams = node.get("grams").asInt();
           return new Dish(name,cost, grams,timeToCook );
       }else{
           int milliliters = node.get("milliliters").asInt();
           return new Drinks(name,cost,milliliters,timeToCook);
       }
    }

}
