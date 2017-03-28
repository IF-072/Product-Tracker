package com.softserve.if072.common.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Created by Home on 28.03.2017.
 */
public class ProductDeserializer extends JsonDeserializer {

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
//        ObjectCodec oc = jsonParser.getCodec();
//        JsonNode node = oc.readTree(jsonParser);
//
//        int id = (Integer) ((IntNode) node.get("id")).numberValue();
//        String name = node.get("name").asText();
//       Image image = node.get("image").g;
//
//        private Image image;
//        private User user;
//
//        private Category category;
//
//        private Unit unit;
//        private boolean isEnabled;
//        private List<Store> stores;
//
//        return new Product(null, node.get("username").getTextValue(), node.get("password").getTextValue());
  return null;
 }
}
