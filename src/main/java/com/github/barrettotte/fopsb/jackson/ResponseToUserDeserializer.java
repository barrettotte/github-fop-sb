package com.github.barrettotte.fopsb.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.barrettotte.fopsb.model.User;

public class ResponseToUserDeserializer extends JsonDeserializer<User> {

    @Override
    public User deserialize(final JsonParser parser, final DeserializationContext context) throws IOException, JsonProcessingException {
        final ObjectCodec codec = parser.getCodec();
        final JsonNode root = codec.readTree(parser);

        final JsonNode userNode = root.get("data").get("user");
        final User user = new User();
        user.setUsername(userNode.get("login").textValue());
        user.setName(userNode.get("name").textValue());
        return user;
    }
}
