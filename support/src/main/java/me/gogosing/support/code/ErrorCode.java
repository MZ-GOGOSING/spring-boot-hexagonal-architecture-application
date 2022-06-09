package me.gogosing.support.code;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ErrorCode {

  SUCCESS("2000", "OK"),

  /* 1001 ~ 2000 logic error*/
  INVALID_PARAMETER("1004", "유효성 확인 실패"),

  /* 9001 ~  system error*/
  SYSTEM_ERROR("9001", "시스템 오류"),
  BAD_REQUEST_ERROR("9002", "부적절한 요청 오류"),
  UNAUTHORIZED_ERROR("9003", "인증 오류"),
  NO_SUCH_ENTITY_ERROR("9004", "존재하지 않는 엔티티 오류"),
  ENTITY_EXISTS_ERROR("9007", "이미 존재하는 데이터입니다."),
  ENTITY_SAVE_ERROR("9008", "데이터 저장에 실패하였습니다."),
  ENTITY_DELETION_ERROR("9009", "데이터 삭제에 실패하였습니다."),
  NO_SUCH_ITEM_CODE_ERROR("9010", "ItemCode가 존재하지 않습니다."),
  UNKNOWN_ERROR("9999", "알 수 없는 오류");

  private final String code;
  private final String defaultMessage;

  ErrorCode(String code, String defaultMessage) {
    this.code = code;
    this.defaultMessage = defaultMessage;
  }

}
