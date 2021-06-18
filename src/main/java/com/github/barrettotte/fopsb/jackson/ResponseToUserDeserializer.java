package com.github.barrettotte.fopsb.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.barrettotte.fopsb.model.Language;
import com.github.barrettotte.fopsb.model.Repo;
import com.github.barrettotte.fopsb.model.User;

public class ResponseToUserDeserializer extends JsonDeserializer<User> {
    
    @Override
    public User deserialize(final JsonParser parser, final DeserializationContext context) throws IOException, JsonProcessingException {
        final ObjectCodec codec = parser.getCodec();
        final JsonNode root = codec.readTree(parser);

        final JsonNode userNode = root.get("data").get("viewer");
        final User user = new User();
        user.setUsername(userNode.get("login").textValue());
        user.setName(userNode.get("name").textValue());
        userNode.get("repositories").get("nodes").forEach(repoNode -> {
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
            user.getRepos().add(repo);
        });
        return user;
    }
}
