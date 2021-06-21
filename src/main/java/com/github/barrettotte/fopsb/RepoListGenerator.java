package com.github.barrettotte.fopsb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.barrettotte.fopsb.jackson.ResponseToUserDeserializer;
import com.github.barrettotte.fopsb.model.User;
import com.github.barrettotte.fopsb.utils.FileUtils;
import com.github.barrettotte.fopsb.utils.FopUtils;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

public class RepoListGenerator {

    public static void main(String[] args) {

        String gqlQuery;
        String token;
        try{
            token = FileUtils.getResourceFileAsString("dev.env");
            gqlQuery = FileUtils.getResourceFileAsString("gql/repos.gql");
        } catch(final IOException e) {
            System.out.printf("Error reading from resources.\n%s\n", e);
            e.printStackTrace();
            return;
        }

        final Map<String, String> reqBodyMap = new HashMap<String, String>() {{
            put("query", gqlQuery);
        }};

        final ObjectMapper jsonMapper = new ObjectMapper();
        final XmlMapper xmlMapper = new XmlMapper();

        final SimpleModule module = new SimpleModule();
        module.addDeserializer(User.class, new ResponseToUserDeserializer());
        jsonMapper.registerModule(module);

        String reqBody;
        try{
            reqBody = jsonMapper.writeValueAsString(reqBodyMap);
        } catch(final IOException e) {
            System.out.printf("Error mapping request body.\n%s\n", e);
            e.printStackTrace();
            return;
        }
        
        
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
        User user;
        String userJson;
        try{
            user = jsonMapper.readValue(response.body(), User.class);
            userJson = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
            // System.out.println(userJson);
        } catch(final IOException e) {
            System.out.printf("Error occurred with JSON mapper.\n%s\n", e);
            e.printStackTrace();
            return;
        }

        // TODO: custom serialize to XML and pass to XSLT
        String xml;
        try{
            xml = xmlMapper.writeValueAsString(user);
            System.out.println(xml);
        } catch(final JsonProcessingException e) {
            System.out.printf("Error occurred with XML mapper.\n%s\n", e);
            e.printStackTrace();
            return;
        }


        try {
            final String resources = "src/main/resources/";
            final File xsltFile = new File(resources + "xslt/repos.xslt");
            // final File xmlFile = new File(resources + "data/test_data.xml");

            // FopUtils.generateFO(xsltFile, xmlFile, resources + "output/repos.fo");
            // FopUtils.generatePDF(xsltFile, xmlFile, resources + "output/repos.pdf");

            final StreamSource xmlSrc = new StreamSource(new StringReader(xml));
            final OutputStream out = new FileOutputStream("src/main/resources/output/repos.pdf");
        try {
            final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            final Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, fopFactory.newFOUserAgent(), out);

            final TransformerFactory factory = TransformerFactory.newInstance();
            final Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            transformer.transform(xmlSrc, new SAXResult(fop.getDefaultHandler()));
        } finally {
            out.close();
        }
        
            

        } catch(final Exception e) {
            e.printStackTrace();
        }
    }
}