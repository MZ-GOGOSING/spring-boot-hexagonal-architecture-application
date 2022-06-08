package me.gogosing.support.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.gogosing.support.converter.DefaultDateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 기간 필터링 조건 모델.
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class LocalDateRangeQuery implements Serializable {

  /**
   * 필터링 적용 시작일.
   */
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @JsonFormat(pattern = DefaultDateTimeFormat.DATE_FORMAT_PATTERN)
  private LocalDate startDate;

  /**
   * 필터링 적용 종료일.
   */
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @JsonFormat(pattern = DefaultDateTimeFormat.DATE_FORMAT_PATTERN)
  private LocalDate endDate;


  public LocalDateRangeQuery(final int defaultPeriodDays) {
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
