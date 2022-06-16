package me.gogosing.container.web.bind;

import java.time.LocalTime;
import me.gogosing.support.converter.DefaultDateTimeConverter;
import org.springframework.core.convert.converter.Converter;

public class LocalTimeParamBinder implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(final String time) {
        return DefaultDateTimeConverter.convertTime(time);
    }

}
