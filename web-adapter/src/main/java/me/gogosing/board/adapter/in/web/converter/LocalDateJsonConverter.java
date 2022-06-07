package me.gogosing.board.adapter.in.web.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDate;
import me.gogosing.support.converter.DefaultDateTimeConverter;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class LocalDateJsonConverter {

    private LocalDateJsonConverter() {

    }

    public static class Serializer extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(DefaultDateTimeConverter.convertDate(localDate));
        }
    }

    public static class Deserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return DefaultDateTimeConverter.convertDate(jsonParser.getText());
        }
    }
}
