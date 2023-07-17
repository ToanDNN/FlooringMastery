package main.com.sg.flooring.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface UserIO {
    void print(String message);
    double readDouble(String prompt);
    double readDouble(String prompt, double min, double max);
    float readFloat(String prompt);
    float readFloat(String prompt, float min, float max);
    int readInt(String prompt);
    int readInt(String prompt, int min, int max);
    long readLong(String prompt);
    long readLong(String prompt, long min, long max);
    BigDecimal readBigDecimal(String prompt, int round);
    BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max);
    LocalDate readDate(String prompt);
    LocalDate readDateGeneral(String prompt);
    LocalDate readDate(String prompt, LocalDate start, LocalDate end);
    String readString(String prompt);

    String readString(String prompt, int max);
}
