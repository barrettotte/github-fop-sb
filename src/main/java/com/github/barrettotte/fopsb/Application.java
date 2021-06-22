package com.github.barrettotte.fopsb;

// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.module.SimpleModule;
// import com.fasterxml.jackson.dataformat.xml.XmlMapper;
// import com.github.barrettotte.fopsb.gql.GithubGqlRequester;
// import com.github.barrettotte.fopsb.jackson.ResponseToRepoSearchDeserializer;
// import com.github.barrettotte.fopsb.jackson.ResponseToUserDeserializer;
// import com.github.barrettotte.fopsb.model.RepoSearch;
// import com.github.barrettotte.fopsb.model.User;
// import com.github.barrettotte.fopsb.utils.FileUtils;
// import com.github.barrettotte.fopsb.utils.FopUtils;

// import org.apache.fop.apps.Fop;
// import org.apache.fop.apps.FopFactory;
// import org.apache.xmlgraphics.util.MimeConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.io.OutputStream;
// import java.io.StringReader;
// import java.net.URI;
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
// import java.util.HashMap;
// import java.util.Map;

// import javax.xml.transform.Transformer;
// import javax.xml.transform.TransformerFactory;
// import javax.xml.transform.sax.SAXResult;
// import javax.xml.transform.stream.StreamSource;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        // String token;
        // try{
        //     token = FileUtils.getResourceFileAsString("dev.env");
        // } catch(final IOException e) {
        //     System.out.printf("Error reading from resources.\n%s\n", e);
        //     e.printStackTrace();
        //     return;
        // }

        // try (final OutputStream out = new FileOutputStream("src/main/resources/output/repos.pdf")){
        //     final XmlMapper xmlMapper = new XmlMapper();
        //     final GithubGqlRequester github = new GithubGqlRequester(token);
        //     final User user = github.getCurrentUser();

        //     final File xsltFile = FileUtils.getResourceFile("xslt/repos.xslt");
        //     final String xml = xmlMapper.writeValueAsString(user);
        //     // final String xml = FileUtils.getResourceFileAsString("data/test_data.xml");
        //     // System.out.println(xml);

        //     // FopUtils.generateFO(xsltFile, xmlFile, resources + "output/repos.fo");
        //     // FopUtils.generatePDF(xsltFile, xmlFile, resources + "output/repos.pdf");

        //     final StreamSource xmlStream = new StreamSource(new StringReader(xml));
        //     final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        //     final Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, fopFactory.newFOUserAgent(), out);

        //     final TransformerFactory factory = TransformerFactory.newInstance();
        //     final Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
        //     transformer.transform(xmlStream, new SAXResult(fop.getDefaultHandler()));
        // } catch(final Exception e) {
        //     e.printStackTrace(); // TODO: replace with logger
        // }
    }
}