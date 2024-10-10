package org.example.common.util;

import org.example.common.exception.exp.InvalidArgumentException;
import org.springframework.util.Assert;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.ZoneOffset.UTC;

public final class CommonUtils {

  public static final UUID MIM_CAFE = UUID.fromString("a0b7b312-36a0-4503-93b5-f4ca7b3f9688");
  public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public static final String LOWER = UPPER.toLowerCase(Locale.ROOT);

  public static final String DIGITS = "0123456789";

  public static final String ALPHA_NUMERIC = UPPER + LOWER + DIGITS;

  private static final SecureRandom SECURE_RANDOM = new SecureRandom();

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("mmHHddMMyy");

  /**
   * Returns a pseudo-random number between 100_000 (inclusive) and 900_000 (exclusive)
   *
   * @return 5 digit pseudo-random number
   */
  public static int generateRandomDigits() {
    return 10_000 + SECURE_RANDOM.nextInt(90_000);
  }

  public static boolean allDigits(final String text) {
    return !text.matches("^.*[^0-9].*$");
  }

  public static boolean allLetters(final String text) {
    return !text.matches("^.*[^a-zA-Z].*$");
  }

  public static String generateRandomNumericString() {
    return DATE_TIME_FORMATTER.format(ZonedDateTime.now(UTC)) + generateRandomDigits();
  }

  public static String generateRandomString(
      final int length, final String symbols, final Random random) {
    Assert.notNull(random, "Random cannot be null");

    if (length < 1) {
      throw new IllegalArgumentException("Length should be 1 or more");
    }

    if (symbols.length() < 2) {
      throw new IllegalArgumentException("Symbol count cannot be less than 2");
    }

    final char[] characters = symbols.toCharArray();
    final char[] buffer = new char[length];

    for (int i = 0; i < length; i++) {
      buffer[i] = characters[random.nextInt(characters.length)];
    }

    return new String(buffer);
  }

  public static String generateSecureString(final int length) {
    return generateRandomString(length, UPPER + DIGITS, SECURE_RANDOM);
  }

  public static String removeLeadingPlus(String phoneNumber) {
    final Pattern pattern = Pattern.compile("^\\+");
    final Matcher matcher = pattern.matcher(phoneNumber);
    return matcher.replaceFirst("");
  }

  public static String requireValidEmail(String email) {
    Objects.requireNonNull(email, "email must not be null");

    if (!validateEmail(email)) {
      throw new InvalidArgumentException(String.format("[%s] email is invalid", email));
    }

    return email;
  }

  public static boolean validateEmail(String email) {
    final String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
    final Matcher matcher = pattern.matcher(email);

    return matcher.matches();
  }

  public static boolean validatePhoneNumber(final String phoneNumber) {
    final String MOBILE_NUMBERS_PATTERN =
        "^(\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$";
    return Pattern.matches(MOBILE_NUMBERS_PATTERN, phoneNumber);
  }

  public static boolean validateUzPhoneNumber(String phoneNumber) {
    final String UZ_PHONE_NUMBER_PATTERN = "^\\+998\\d{9}$";
    final Pattern pattern = Pattern.compile(UZ_PHONE_NUMBER_PATTERN, Pattern.CASE_INSENSITIVE);
    final Matcher matcher = pattern.matcher(phoneNumber);

    return matcher.matches();
  }

  public static Timestamp getTimeStampFromInstant(final Instant instant) {
    LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    return Timestamp.valueOf(ldt);
  }


}
