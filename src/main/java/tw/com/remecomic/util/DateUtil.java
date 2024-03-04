package tw.com.remecomic.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {
	
	public static Object parseDate(String inputDate) throws ParseException {
        Map<String, String> monthMap = new HashMap<>();
        monthMap.put("Jan", "01");
        monthMap.put("Feb", "02");
        monthMap.put("Mar", "03");
        monthMap.put("Apr", "04");
        monthMap.put("May", "05");
        monthMap.put("Jun", "06");
        monthMap.put("Jul", "07");
        monthMap.put("Aug", "08");
        monthMap.put("Sep", "09");
        monthMap.put("Oct", "10");
        monthMap.put("Nov", "11");
        monthMap.put("Dec", "12");

        String monthName = inputDate.substring(0, 3);
        String monthNumber = monthMap.get(monthName);
        if (monthNumber == null) {
            throw new ParseException("Invalid month name: " + monthName, 0);
        }

        //String reformattedDate = inputDate.replace(monthName, monthNumber).replace("/", "-");
        String date = inputDate.substring(4,6);
        String year = inputDate.substring(7,11);
        String reformattedDate = String.format("%s-%s-%s", year,monthNumber,date);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(formatter.parse(reformattedDate));
        return formatter.parse(reformattedDate);
    }

}
