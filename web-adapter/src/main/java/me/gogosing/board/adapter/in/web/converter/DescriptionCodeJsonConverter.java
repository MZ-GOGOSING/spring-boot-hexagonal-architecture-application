package me.gogosing.board.adapter.in.web.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import java.io.IOException;
import me.gogosing.support.code.DescriptionCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class DescriptionCodeJsonConverter {

  private DescriptionCodeJsonConverter() {

  }

  private static final String FIELD_CODE = "code";
  private static final String FIELD_TEXT = "text";

  public static class Serializer extends JsonSerializer<DescriptionCode> {

    @Override
    public void serialize(DescriptionCode code, JsonGenerator jsonGenerator,
        SerializerProvider serializerProvider) throws IOException {
      jsonGenerator.writeStartObject();
      jsonGenerator.writeStringField(FIELD_CODE, code.getCode());
      jsonGenerator.writeStringField(FIELD_TEXT, code.getDescription());
      jsonGenerator.writeEndObject();
    }
  }

  public static class Deserializer extends JsonDeserializer<Enum> implements
      ContextualDeserializer {

    private Class<Enum> targetClass;

    public Deserializer() {
    }

    public Deserializer(Class<Enum> targetClass) {
      this.targetClass = targetClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt,
        BeanProperty property) {
      return new Deserializer((Class<Enum>) ctxt.getContextualType().getRawClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Enum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {
      String code = null;
      if (jsonParser.currentToken() == JsonToken.VALUE_STRING) {
        code = jsonParser.getValueAsString();
      } else {
        for (JsonToken jsonToken = jsonParser.nextToken(); jsonToken != JsonToken.END_OBJECT;
            jsonToken = jsonParser.nextToken()) {
          if (jsonToken == JsonToken.FIELD_NAME && FIELD_CODE.equals(jsonParser.getCurrentName())) {
            code = jsonParser.nextTextValue();
          }
        }
      }

      return StringUtils.isEmpty(code) ? null : Enum.valueOf(targetClass, code);
    }
  }

}
