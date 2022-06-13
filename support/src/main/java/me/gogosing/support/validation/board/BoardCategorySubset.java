package me.gogosing.support.validation.board;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import me.gogosing.support.code.board.BoardCategory;

@Target({METHOD, FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = BoardCategorySubsetValidator.class)
public @interface BoardCategorySubset {

    BoardCategory[] anyOf();

    String message() default "{anyOf} 중 하나이어야만 합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
