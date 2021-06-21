package com.github.barrettotte.fopsb.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName="repo")
public class Repo {

    @JacksonXmlProperty(isAttribute = true)
    private String name;
    
    @JacksonXmlProperty(isAttribute = true)
    private Integer stars;

    @JacksonXmlProperty(isAttribute = true)
    private Integer forks;

    @JacksonXmlElementWrapper(localName = "languages")
    @JacksonXmlProperty(localName = "language")
    private List<Language> topLangs;

    public Repo() {
        this.topLangs = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getStars() {
        return this.stars;
    }
    
    public void setStars(final Integer stars) {
        this.stars = stars;
    }

    public Integer getForks() {
        return this.forks;
    }

    public void setForks(final Integer forks) {
        this.forks = forks;
    }

    public List<Language> getTopLangs() {
        return this.topLangs;
    }

    public void setTopLangs(final List<Language> topLangs) {
        this.topLangs = topLangs;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Repo{");
        sb.append("name=\"").append(this.name).append('"');
        sb.append("stars=").append(this.stars);
        sb.append("forks=").append(this.forks);
        sb.append(", topLangs=Language[").append(this.topLangs.size()).append(']');
        sb.append('}');
        return sb.toString();
    }
}
