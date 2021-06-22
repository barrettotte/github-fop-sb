package com.github.barrettotte.fopsb.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.barrettotte.fopsb.model.Language;
import com.github.barrettotte.fopsb.model.PageInfo;
import com.github.barrettotte.fopsb.model.Repo;
import com.github.barrettotte.fopsb.model.RepoSearch;

public class ResponseToRepoSearchDeserializer extends JsonDeserializer<RepoSearch> {
    
    @Override
    public RepoSearch deserialize(final JsonParser parser, final DeserializationContext context) throws IOException, JsonProcessingException {
        final ObjectCodec codec = parser.getCodec();
        final JsonNode root = codec.readTree(parser);

        final JsonNode searchNode = root.get("data").get("search");
        final RepoSearch repoSearch = new RepoSearch();
        repoSearch.setRepoCount(searchNode.get("repositoryCount").asInt());

        final JsonNode pageInfoNode = searchNode.get("pageInfo");
        final PageInfo pageInfo = new PageInfo();
        pageInfo.setStartCursor(pageInfoNode.get("startCursor").asText());
        pageInfo.setHasNextPage(pageInfoNode.get("hasNextPage").asBoolean());
        pageInfo.setEndCursor(pageInfoNode.get("endCursor").asText());
        repoSearch.setPageInfo(pageInfo);

        searchNode.get("edges").forEach(node -> {
            final JsonNode repoNode = node.get("node");
            final Repo repo = new Repo();
            repo.setName(repoNode.get("name").asText());
            repo.setStars(repoNode.get("stargazers").get("totalCount").asInt(0));
            repo.setForks(repoNode.get("forkCount").asInt(0));

            if (repoNode.has("languages")) {
                repoNode.get("languages").get("nodes").forEach(langNode -> {
                    final Language lang = new Language();
                    lang.setName(langNode.get("name").asText());
                    lang.setColorHex(langNode.get("color").asText());
                    repo.getTopLangs().add(lang);
                });
            }
            repoSearch.getRepos().add(repo);
        });
        return repoSearch;
    }
}
