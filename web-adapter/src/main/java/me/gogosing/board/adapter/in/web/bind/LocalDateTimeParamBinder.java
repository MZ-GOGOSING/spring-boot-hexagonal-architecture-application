package me.gogosing.board.adapter.in.web.bind;

import java.time.LocalDateTime;
import me.gogosing.support.converter.DefaultDateTimeConverter;
import org.springframework.core.convert.converter.Converter;

public class LocalDateTimeParamBinder implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(final String dateTime) {
        return DefaultDateTimeConverter.convertDateTime(dateTime);
    }
}





