package me.gogosing.support.validation.board;

import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import me.gogosing.support.code.board.BoardCategory;

public class BoardCategorySubsetValidator implements ConstraintValidator<BoardCategorySubset, BoardCategory> {

    private BoardCategory[] subset;

    @Override
    public void initialize(BoardCategorySubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(BoardCategory value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}
