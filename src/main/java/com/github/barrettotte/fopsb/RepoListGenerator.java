package com.github.barrettotte.fopsb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.barrettotte.fopsb.jackson.ResponseToUserDeserializer;
import com.github.barrettotte.fopsb.model.User;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class RepoListGenerator {

    public static void main(String[] args) {

        // TODO: read from file
        final String gqlQuery = "query{viewer{login name repositories(first:50,isFork:false){nodes{name stargazers{totalCount} forkCount languages(first:3,orderBy:{field:SIZE,direction:DESC}){nodes{name color}}}}}}";
        
        final Map<String, String> reqBodyMap = new HashMap<String, String>() {{
            put("query", gqlQuery);
        }};

        final ObjectMapper objectMapper = new ObjectMapper();

        final SimpleModule module = new SimpleModule();
        module.addDeserializer(User.class, new ResponseToUserDeserializer());
        // TODO: add custom serializer
        objectMapper.registerModule(module);

        String reqBody;
        try{
            reqBody = objectMapper.writeValueAsString(reqBodyMap);
        } catch(final IOException e) {
            System.out.printf("Error mapping request body.\n%s\n", e);
            e.printStackTrace();
            return;
        }

        // TODO: read from properties file
        final String token = "xxx";
        
        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.github.com/graphql"))
            .setHeader("Authorization", "Bearer " + token)
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(reqBody))
            .build();
        
        // TODO: move to function
        HttpResponse<String> response;
        try{
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch(final Exception e) {
            System.out.printf("Error occurred receiving HTTP response.\n%s\n", e);
            e.printStackTrace();
            return;
        }

        // TODO: move to function
        String userJson;
        try{
            final User user = objectMapper.readValue(response.body(), User.class);
            userJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
            
            System.out.println(userJson);
        } catch(final IOException e) {
            System.out.printf("Error occurred with object mapper.\n%s\n", e);
            e.printStackTrace();
            return;
        }

        // TODO: custom serialize to XML and pass to XSLT


        // try {
        //     final String resources = "src/main/resources/";
        //     final File xsltFile = new File(resources + "xslt/repos.xslt");
        //     final File xmlFile = new File(resources + "data/test_data.xml");

        //     FopUtils.generateFO(xsltFile, xmlFile, resources + "output/repos.fo");
        //     FopUtils.generatePDF(xsltFile, xmlFile, resources + "output/repos.pdf");
        // } catch(final Exception e) {
        //     e.printStackTrace();
        // }
    }
}