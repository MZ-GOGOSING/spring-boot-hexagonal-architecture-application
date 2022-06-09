package me.gogosing.support.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

@Getter
@Setter
public class InvalidArguments implements Serializable {

  private static final long serialVersionUID = 1L;

  private final String field;
  private final String message;

  public InvalidArguments(final FieldError fieldError) {
    this.field = fieldError.getField();
    this.message = fieldError.getDefaultMessage();
  }

}
