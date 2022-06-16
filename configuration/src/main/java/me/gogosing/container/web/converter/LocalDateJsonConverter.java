package me.gogosing.container.web.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.gogosing.support.converter.DefaultDateTimeConverter;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDateJsonConverter {

    public static class Serializer extends JsonSerializer<LocalDate> {

        @Override
        public void serialize(
            final LocalDate localDate,
            final JsonGenerator jsonGenerator,
            final SerializerProvider serializerProvider
        ) throws IOException {
            jsonGenerator.writeString(DefaultDateTimeConverter.convertDate(localDate));
        }
    }

    public static class Deserializer extends JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(
            final JsonParser jsonParser,
            final DeserializationContext deserializationContext
        ) throws IOException {
            return DefaultDateTimeConverter.convertDate(jsonParser.getText());
        }
    }
}
