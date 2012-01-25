package tools;

import java.util.Calendar;
import java.util.Date;

public class DateTools {
	public static Calendar dateToCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
	}
}
