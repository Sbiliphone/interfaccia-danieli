package com.ApiLink;

import com.Exeptions.RequestNotValid;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;


public class Requests {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String escape(String s){
        return s.replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("\"", "\\\"");
    }

    public static String bodyToString(ResponseBody body){
        String bodyString = body.asString();
        //if (bodyString.charAt(0) != '{') bodyString = "{ \"sectionModels\":" + bodyString + '}';
        //bodyString = escape(bodyString);
        return bodyString;
    }

    public static String[] splitJSONArray(ResponseBody body){
        String base = bodyToString(body);
        String[] strings = base.split("},");

        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].replace("[", "");
            strings[i] = strings[i].replace("]", "");
            strings[i] = strings[i].replace("\n", "");
            strings[i] = strings[i].replace("\t", "");
            if (strings[i].charAt(strings[i].length()-1) != '}'){
                strings[i] += "}";
            }
        }
        return strings;
    }



    public static String POSTRequest(String body, String endpoint) throws RequestNotValid {
        RestAssured.baseURI = endpoint;
        RequestSpecification request = RestAssured.given();

        request.body(body);

        System.out.println("BODY = " + body);

        request.header("Accept", ContentType.JSON.getAcceptHeader());
        request.header("Content-Type", ContentType.JSON);

        try{
            Response response = request.post("/");

            int statusCode = response.getStatusCode();
            if (statusCode != 200 && statusCode != 201 && statusCode != 203) throw new RequestNotValid();

            System.out.println("Response body: " + response.body().asString());

            return response.body().asString();
        }catch (Exception e){
            System.out.println("Exeption e = " + e);
            throw new RequestNotValid();
        }

    }



    public static String[] GETRequest(String endpoint) throws RequestNotValid {

        RestAssured.baseURI = endpoint;
        RequestSpecification httpRequest = RestAssured.given();

        try{
            Response response = httpRequest.get();

            // Retrieve the body of the Response
            ResponseBody body = response.getBody();

            return splitJSONArray(body);
        }catch(Exception e){
            System.out.println("Exeption e = " + e);
            throw new RequestNotValid();
        }

    }
}
