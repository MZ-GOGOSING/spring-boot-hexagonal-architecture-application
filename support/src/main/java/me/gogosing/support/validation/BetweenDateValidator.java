package me.gogosing.support.validation;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.ObjectUtils.allNotNull;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_MINUS_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import me.gogosing.support.dto.LocalDateRangeQuery;
import org.apache.commons.lang3.math.NumberUtils;

public class BetweenDateValidator implements ConstraintValidator<BetweenDate, LocalDateRangeQuery> {

	private static final String ANY_NULL_CONTAINS_MESSAGE = "시작일과 종료일 모두 지정되어야 합니다.";

	private static final String INVALID_TIME_MESSAGE = "시작일이 종료일 보다 미래일 수 없습니다.";

	private static final String INVALID_EXCEED_RANGE_MESSAGE = "최대 검색 가능 기간은 14일 입니다.";

	private boolean required;

	private int maximumDateRangeLimit;

	@Override
	public void initialize(BetweenDate constraint) {
		required = constraint.required();
		maximumDateRangeLimit = constraint.maximumDateRangeLimit();
	}

	@Override
	public boolean isValid(LocalDateRangeQuery value, ConstraintValidatorContext context) {
		if (!required || isNull(value)) {
			return Boolean.TRUE;
		}
		return valid(value, context);
	}

	private boolean valid(LocalDateRangeQuery source, ConstraintValidatorContext context) {
		Optional<String> invalidMessage = getInvalidMessage(source);

		if (invalidMessage.isEmpty()) {
			return Boolean.TRUE;
		}

		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(invalidMessage.get()).addConstraintViolation();

		return Boolean.FALSE;
	}

	private Optional<String> getInvalidMessage(LocalDateRangeQuery source) {
		return getValidationFunctionEntries()
			.entrySet()
			.stream()
			.filter(entry -> isFalse(entry.getKey().apply(source)))
			.map(Entry::getValue)
			.findFirst();
	}

	private Map<Function<LocalDateRangeQuery, Boolean>, String> getValidationFunctionEntries() {
		LinkedHashMap<Function<LocalDateRangeQuery, Boolean>, String> validationFunctionEntries = new LinkedHashMap<>();

		validationFunctionEntries.put(this::isAllNotNull, ANY_NULL_CONTAINS_MESSAGE);
		validationFunctionEntries.put(this::isValidTime, INVALID_TIME_MESSAGE);
		validationFunctionEntries.put(this::isValidRange, INVALID_EXCEED_RANGE_MESSAGE);

		return validationFunctionEntries;
	}

	private Boolean isAllNotNull(LocalDateRangeQuery value) {
		return allNotNull(value.getStartDate(), value.getEndDate());
	}

	private Boolean isValidTime(LocalDateRangeQuery value) {
		if (!isAllNotNull(value)) {
			return Boolean.FALSE;
		}

		LocalDate from = value.getStartDate();
		LocalDate to = value.getEndDate();

		int intervalValue = to.compareTo(from);
		int comparatorValue = NumberUtils.compare(intervalValue, INTEGER_ZERO);

		return comparatorValue > INTEGER_MINUS_ONE;
	}

	private Boolean isValidRange(LocalDateRangeQuery value) {
		if (!isAllNotNull(value)) {
			return Boolean.FALSE;
		}

		LocalDate from = value.getStartDate();
		LocalDate to = value.getEndDate();

		int intervalValue = to.compareTo(from);

		return intervalValue < maximumDateRangeLimit;
	}
}
