package me.gogosing.support.assertion;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import java.math.BigInteger;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.Assert;

public class AssertHelper extends Assert {

    public static void isPositive(final Short number, final String message) {
        final var nullSafeNumber = defaultIfNull(number, NumberUtils.SHORT_ZERO);
        AssertHelper.isPositive(nullSafeNumber.intValue(), message);
    }

    public static void isPositive(final Integer number, final String message) {
        final var nullSafeNumber = defaultIfNull(number, NumberUtils.INTEGER_ZERO);
        AssertHelper.isPositive(nullSafeNumber.longValue(), message);
    }

    public static void isPositive(final Float number, final String message) {
        final var nullSafeNumber = defaultIfNull(number, NumberUtils.FLOAT_ZERO);
        AssertHelper.isPositive(nullSafeNumber.doubleValue(), message);
    }

    public static void isPositive(final Double number, final String message) {
        final var nullSafeNumber = defaultIfNull(number, NumberUtils.DOUBLE_ZERO);
        AssertHelper.isPositive(nullSafeNumber.longValue(), message);
    }

    public static void isPositive(final Long number, final String message) {
        final var nullSafeNumber = defaultIfNull(number, NumberUtils.LONG_ZERO);
        AssertHelper.isPositive(BigInteger.valueOf(nullSafeNumber), message);
    }

    public static void isPositive(final BigInteger number, final String message) {
        final var nullSafeNumber = defaultIfNull(number, BigInteger.ZERO);
        Assert.state(nullSafeNumber.signum() > 0, message);
    }
}
