package com.github.barrettotte.fopsb.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.barrettotte.fopsb.model.Repo;
import com.github.barrettotte.fopsb.model.RepoSearch;
import com.github.barrettotte.fopsb.model.User;
import com.github.barrettotte.fopsb.utils.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GithubGqlService {

    private String userGql;
    private String reposGql;
    private String token;

    @Autowired
    private ObjectMapper objectMapper;

    public GithubGqlService() {}

    public GithubGqlService(final String token, final String userGql, final String reposGql) throws IOException {
        this.token = token;
        this.userGql = userGql;
        this.reposGql = reposGql;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setUserGql(final String gql) {
        this.userGql = gql;
    }

    public void setReposGql(final String gql) {
        this.reposGql = gql;
    }

    // Get a GitHub user
    public User getUser(final String username) throws IOException, InterruptedException {
        final Map<String, String> variables = new HashMap<String, String>() {{
            put("username", username);
        }};
        final Map<String, String> reqBodyMap = new HashMap<String, String>() {{
            put("query", userGql);
            put("variables", objectMapper.writeValueAsString(variables));
        }};
        final String reqBody = objectMapper.writeValueAsString(reqBodyMap);
        final HttpResponse<String> response = makeRequest(reqBody);

        final User user = objectMapper.readValue(response.body(), User.class);
        user.setRepos(getRepos(user.getUsername()));
        return user;
    }

    // get list of repositories for a GitHub user
    public List<Repo> getRepos(final String username) throws IOException, InterruptedException {
        final Map<String, String> variables = new HashMap<String, String>() {{
            put("queryString", String.format("user:%s is:public", username));
        }};
        final String query = reposGql;
        final List<Repo> repos = new ArrayList<>();
        RepoSearch repoSearch = searchRepo(query, variables);
        repos.addAll(repoSearch.getRepos());

        // loop through pages
        while (repoSearch.getPageInfo().getHasNextPage()) {
            variables.put("after", String.format("%s", repoSearch.getPageInfo().getEndCursor()));
            repoSearch = searchRepo(query, variables);
            repos.addAll(repoSearch.getRepos());
        }
        return repos;
    }

    // perform a repository search
    private RepoSearch searchRepo(final String query, final Map<String, String> variables) throws IOException, InterruptedException{
        final Map<String, String> reqBodyMap = new HashMap<String, String>() {{
            put("query", query);
            put("variables", objectMapper.writeValueAsString(variables));
        }};
        final String reqBody = objectMapper.writeValueAsString(reqBodyMap);
        final HttpResponse<String> response = makeRequest(reqBody);
        return objectMapper.readValue(response.body(), RepoSearch.class);
    }

    // make HTTP request to GitHub GraphQL API
    private HttpResponse<String> makeRequest(final String reqBody) throws IOException, InterruptedException {
        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.github.com/graphql"))
            .setHeader("Authorization", "Bearer " + this.token)
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(reqBody))
            .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
