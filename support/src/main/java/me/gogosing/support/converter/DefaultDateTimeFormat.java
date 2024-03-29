package me.gogosing.support.converter;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.format.DateTimeFormatter;

//@formatter:off
public class DefaultDateTimeFormat {

  private DefaultDateTimeFormat() {
  }

  public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
  public static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

  public static final DateTimeFormatter DATE_FORMAT = ofPattern(DATE_FORMAT_PATTERN);
  public static final DateTimeFormatter DATE_FORMAT_EXCEL = ofPattern("dd/MM/yy");
  public static final DateTimeFormatter DATE_FORMAT_SAP = ofPattern("yyyyMMdd");
  public static final DateTimeFormatter TIME_FORMAT = ofPattern("HH:mm:ss");
  public static final DateTimeFormatter DATE_TIME_FORMAT = ofPattern(DATE_TIME_FORMAT_PATTERN);
  public static final DateTimeFormatter DATE_TIME_TIMEZONE_FORMAT = ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
  public static final DateTimeFormatter DATE_TIME_FORMAT_UPLOAD = ofPattern("yyyyMMddHHmmss");
  public static final DateTimeFormatter DATE_FORMAT_MAIL = ofPattern("yyyy.MM.dd");

}
