package utils;

/**
 *
 */
public class TimeUtils {
	public static int timeToSeconds(String time) {
		String[] splitted = time.split(":");
		int hours = Integer.parseInt ( splitted[0].trim() );
		int mins = Integer.parseInt ( splitted[1].trim() );
		int secs = Integer.parseInt ( splitted[2].trim() );
		return 60 * ((60 * hours) + mins) + secs;
	}
	
	public static String secondsToTime(int seconds) {
		int hours = (seconds / 3600) % 24;
		seconds %= 3600;
		int minutes = seconds / 60;
		seconds %= 60;
		return hours + ":" + minutes + ":" + seconds;
	}
}
