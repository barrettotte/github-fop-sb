package com.github.barrettotte.fopsb.model;

import java.util.ArrayList;
import java.util.List;

public class Repo {

    private String name;
    private Integer stars;
    private Integer forks;
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
