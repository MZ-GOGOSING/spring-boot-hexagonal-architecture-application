package me.gogosing.board.adapter.in.web.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDateTime;
import me.gogosing.support.converter.DefaultDateTimeConverter;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class LocalDateTimeJsonConverter {

    private LocalDateTimeJsonConverter() {

    }

    public static class Serializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(DefaultDateTimeConverter.convertDateTime(dateTime));
        }
    }

    public static class Deserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return DefaultDateTimeConverter.convertDateTime(jsonParser.getText());
        }
    }
}
