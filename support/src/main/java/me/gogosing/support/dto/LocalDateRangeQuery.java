package me.gogosing.support.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import me.gogosing.support.converter.DefaultDateTimeFormat;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

@Schema(description = "날짜 범위 검색 모델")
@ParameterObject
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class LocalDateRangeQuery implements Serializable {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @JsonFormat(pattern = DefaultDateTimeFormat.DATE_FORMAT_PATTERN)
  @Parameter(description = "yyyy-MM-dd 형식의 검색 시작일 (goe)")
  private LocalDate startDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @JsonFormat(pattern = DefaultDateTimeFormat.DATE_FORMAT_PATTERN)
  @Parameter(description = "yyyy-MM-dd 형식의 검색 종료일 (loe)")
  private LocalDate endDate;

  public LocalDateRangeQuery() {
    this(6);
  }

  public LocalDateRangeQuery(final Integer defaultPeriodDays) {
    this.startDate = LocalDate.now().minusDays(defaultPeriodDays);
    this.endDate = LocalDate.now();
  }

  @JsonIgnore
  public LocalDateTime getStartDateTime() {
    return Optional.ofNullable(this.startDate)
        .map(LocalDate::atStartOfDay)
        .orElse(null);
  }

  @JsonIgnore
  public LocalDateTime getEndDateTime() {
    return Optional.ofNullable(this.endDate)
        .map(endDate -> endDate.atTime(LocalTime.MAX))
        .orElse(null);
  }
}
