import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Tester {
	public static void main(String args[]) {
		Calendar start = Calendar.getInstance();
		Calendar newyear = Calendar.getInstance();
		start.set(Calendar.DAY_OF_MONTH, 31);
		start.set(Calendar.HOUR, 9);
		newyear.set(Calendar.YEAR, 2023);
		newyear.set(Calendar.MONTH, 1);
		newyear.set(Calendar.DAY_OF_MONTH, 1);
		newyear.set(Calendar.MINUTE, 0);
		newyear.set(Calendar.HOUR, 0);
		
		System.out.println(start.getTime());
		System.out.println(newyear.getTime());
		System.out.println(getDateKey(start));
		System.out.println(getDateKey(newyear));
		System.out.println(getDateKey(newyear) - getDateKey(start));
		
		System.out.println((getDateKey(newyear) % 10000) / 100);
		System.out.println(getDateKey(newyear) % 10);
	}
	
	private static int getDateKey(Calendar c) {
		return (c.get(Calendar.YEAR) * 10000) + ((c.get(Calendar.MONTH) + 1) * 100) + c.get(Calendar.DAY_OF_MONTH);
	}
}
