package tw.com.remecomic.money.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GiveYouEverything {

    public static String generateRandomNumber() {
        Random random = new Random();

        int randomNumber = random.nextInt(1000000);

        return String.valueOf(randomNumber);
    }

    public static String generateDateAndTime() {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        return now.format(formatter);
    }

    public static String generateTimeMills() {
        long currentTimeMillis = System.currentTimeMillis();

        return Long.toString(currentTimeMillis);
    }
}
