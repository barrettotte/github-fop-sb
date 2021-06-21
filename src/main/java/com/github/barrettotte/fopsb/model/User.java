package com.github.barrettotte.fopsb.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName="user")
public class User {

    @JacksonXmlProperty(isAttribute = true)
    private String username;

    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlElementWrapper(localName="repositories")
    @JacksonXmlProperty(localName = "repo")
    private List<Repo> repos;

    public User() {
        this.repos = new ArrayList<>();
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username){ 
        this.username = username;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public List<Repo> getRepos() {
        return this.repos;
    }

    public void setRepos(final List<Repo> repos) {
        this.repos = repos;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("username=\"").append(this.username).append('"');
        sb.append(", name=\"").append(this.name).append('"');
        sb.append(", repos=Repo[").append(this.repos.size()).append(']');
        sb.append('}');
        return sb.toString();
    }
}
