package tw.com.remecomic.util;

public class Points {
	Double x ;
	Double y;
	
	public Points(Double x, Double y) {
		this.x = x;
		this.y = y;
	}
	
	public static Points point(Double x, Double y) {
		Points point = new Points(x,y);
		return point;
		
	}
	
	
	 public String toString() {
	        return "(" + x + "," + y + ")";
	    }
	

}
