package com.github.barrettotte.fopsb.model;

import java.util.ArrayList;
import java.util.List;

public class RepoSearch {
    
    private Integer repoCount;
    private PageInfo pageInfo;
    private List<Repo> repos;

    public RepoSearch() {
        this.pageInfo = new PageInfo();
        this.repos = new ArrayList<>();
    }

    public Integer getRepoCount() {
        return this.repoCount;
    }

    public void setRepoCount(final Integer repoCount) {
        this.repoCount = repoCount;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(final PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<Repo> getRepos() {
        return this.repos;
    }

    public void setRepos(final List<Repo> repos) {
        this.repos = repos;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RepoSearch{");
        sb.append("repoCount=").append(this.repoCount);
        sb.append("pageInfo=").append(this.pageInfo.toString());
        sb.append("repos=Repo[").append(this.repos.size()).append("]");
        sb.append('}');
        return sb.toString();
    }
}
