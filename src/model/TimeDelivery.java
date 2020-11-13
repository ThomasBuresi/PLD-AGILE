package model;

/**
 * Durations of the tour.
 * 
 * @author H4112
 *
 */
public class TimeDelivery {

	private int hour;
	private int minutes;
	
	public TimeDelivery(int hour, int minutes){
		this.hour= hour;
		this.minutes = minutes;
	}
	
	public TimeDelivery() {
		this.hour = 0;
		this.minutes = 0;
	}
	
	public TimeDelivery addhours(float timeToAdd) {
		int h = (int) timeToAdd;
		int m = (int) ((timeToAdd- h) * 60);
		m+= minutes;
		if (m>59) {
			h+= (int) m/60;
			m = m%60;
		}
		h+=this.hour;
		return  new TimeDelivery(h,m);
	}

	@Override
	public String toString() {
		return  hour +"h" +  minutes + "mins";
	}
	
	
}
