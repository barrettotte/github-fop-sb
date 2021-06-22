package com.github.barrettotte.fopsb.config;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.naming.ConfigurationException;
import javax.xml.transform.TransformerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.barrettotte.fopsb.jackson.ResponseToRepoSearchDeserializer;
import com.github.barrettotte.fopsb.jackson.ResponseToUserDeserializer;
import com.github.barrettotte.fopsb.model.RepoSearch;
import com.github.barrettotte.fopsb.model.User;
import com.github.barrettotte.fopsb.service.FopService;
import com.github.barrettotte.fopsb.service.GithubGqlService;
import com.github.barrettotte.fopsb.utils.FileUtils;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.xmlgraphics.util.MimeConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class AppConfig {
    
    @Value("${github-token}")
    private String token;

    @Bean
    public FopFactory getFopFactory() {
        return FopFactory.newInstance(new File(".").toURI());
    }

    @Bean
    public TransformerFactory getTransformerFactory() {
        return TransformerFactory.newInstance();
    }

    @Bean
    @Primary
    public FopService getFopService() throws IOException, URISyntaxException {
        final FopService service = new FopService();
        service.setReposXslt(FileUtils.getResourceFile("xslt/repos.xslt"));
        return service;
    }

    @Bean
    @Primary
    public GithubGqlService getGithubGqlService() throws IOException {
        final GithubGqlService service = new GithubGqlService();
        service.setToken(this.token);
        service.setUserGql(FileUtils.getResourceFileAsString("gql/user.gql"));
        service.setReposGql(FileUtils.getResourceFileAsString("gql/repos_all.gql"));
        return service;
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();

        final SimpleModule module = new SimpleModule();
        module.addDeserializer(User.class, new ResponseToUserDeserializer());
        module.addDeserializer(RepoSearch.class, new ResponseToRepoSearchDeserializer());

        objectMapper.registerModule(module);
        return objectMapper;
    }
}
