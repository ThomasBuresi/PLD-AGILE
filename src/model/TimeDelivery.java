package model;

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
		int m = (int) (timeToAdd- h) * 60;
		return  new TimeDelivery(h,m);
	}
}
