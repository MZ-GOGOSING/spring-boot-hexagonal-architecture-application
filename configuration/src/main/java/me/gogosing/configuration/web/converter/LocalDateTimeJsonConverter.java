package me.gogosing.configuration.web.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.gogosing.support.converter.DefaultDateTimeConverter;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDateTimeJsonConverter {

    public static class Serializer extends JsonSerializer<LocalDateTime> {

        @Override
        public void serialize(
            final LocalDateTime dateTime,
            final JsonGenerator jsonGenerator,
            final SerializerProvider serializerProvider
        ) throws IOException {
            jsonGenerator.writeString(DefaultDateTimeConverter.convertDateTime(dateTime));
        }
    }

    public static class Deserializer extends JsonDeserializer<LocalDateTime> {

        @Override
        public LocalDateTime deserialize(
            final JsonParser jsonParser,
            final DeserializationContext deserializationContext
        ) throws IOException {
            return DefaultDateTimeConverter.convertDateTime(jsonParser.getText());
        }
    }
}
