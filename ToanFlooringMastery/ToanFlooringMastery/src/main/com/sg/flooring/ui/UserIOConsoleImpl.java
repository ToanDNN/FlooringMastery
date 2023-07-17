package main.com.sg.flooring.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static java.math.BigDecimal.ROUND_UP;

public class UserIOConsoleImpl implements UserIO{

    Scanner sc = new Scanner(System.in);
    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        print(prompt);
        return Double.parseDouble(sc.nextLine());
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double num;
        do {
            print(prompt);
            num = Double.parseDouble(sc.nextLine());
        } while (num < min || num > max);

        return num;
    }

    @Override
    public float readFloat(String prompt) {
        print(prompt);
        return Float.parseFloat(sc.nextLine());
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float num;
        do {
            print(prompt);
            num = Float.parseFloat(sc.nextLine());
        } while (num < min || num > max);

        return num;
    }

    @Override
    public int readInt(String prompt) {
        print(prompt);
        return Integer.parseInt(sc.nextLine());
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int num;
        do {
            print(prompt);
            num = Integer.parseInt(sc.nextLine());
        } while (num < min || num > max);

        return num;
    }

    @Override
    public long readLong(String prompt) {
        print(prompt);
        return Long.parseLong(sc.nextLine());
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long num;
        do {
            print(prompt);
            num = Long.parseLong(sc.nextLine());
        } while (num < min || num > max);

        return num;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, int round) {
        boolean isValid = false;
        BigDecimal result = null;
        do {
            String value = null;
            try {
                value = readString(prompt);
                result = new BigDecimal(value).setScale(round, ROUND_UP);
                isValid = true;
            } catch (NumberFormatException ex) {
                System.out.printf("The value '%s' is not a number.\n", value);
            }
        } while (!isValid);
        return result;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max) {
        return null;
    }

    @Override
    public LocalDate readDate(String prompt) {
        boolean isValid = false;
        LocalDate result = null;
        LocalDate currDate = LocalDate.now();
        do {
            String value = null;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
                value = readString(prompt);
                result = LocalDate.parse(value, formatter);
                if(result.isAfter(currDate) || result.isEqual(currDate)) {
                    isValid = true;
                } else {
                    System.out.printf("The value '%s' is not a valid date. (MMDDYYYY)\n", value);
                }
            } catch (DateTimeParseException ex) {
                System.out.printf("The value '%s' is not a valid date. (MMDDYYYY)\n", value);
            }
        } while (!isValid);

        return result;
    }

    @Override
    public LocalDate readDateGeneral(String prompt) {
        boolean isValid = false;
        LocalDate result = null;
        LocalDate currDate = LocalDate.now();
        do {
            String value = null;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
                value = readString(prompt);
                result = LocalDate.parse(value, formatter);
                if(result.isAfter(currDate) || result.isEqual(currDate)) {
                    isValid = true;
                } else {
                    System.out.printf("The value '%s' is not a valid date. (MMDDYYYY)\n", value);
                }
            } catch (DateTimeParseException ex) {
                System.out.printf("The value '%s' is not a valid date. (MMDDYYYY)\n", value);
            }
        } while (!isValid);

        return result;
    }

    @Override
    public LocalDate readDate(String prompt, LocalDate start, LocalDate end) {
        return null;
    }


    @Override
    public String readString(String prompt) {
        print(prompt);
        return sc.nextLine();
    }
    @Override
    public String readString(String prompt, int max) {
        boolean isValid = false;
        String result = "";
        do {
            result = readString(prompt);
            if (result.length() <= max) {
                isValid = true;
            } else {
                System.out.printf("The entry must be %s letters or less.\n", max);
            }
        } while (!isValid);
        return result;
    }
}
