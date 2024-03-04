package tw.com.remecomic.util;

import java.util.List;

public class Calculate {
	
	public static double standardDeviation(List<Double> numbers) {
	    if (numbers == null || numbers.isEmpty()) {
	        return 0;
	    }
	    Double mean = mean(numbers);
	    
	    double squaredDifferenceSum = 0.0;
	    for (double num : numbers) {
	        squaredDifferenceSum += Math.pow(num - mean, 2);
	    }
	    double variance = squaredDifferenceSum / numbers.size();

	    // Step 3: Calculate the standard deviation
	    return Math.sqrt(variance);
	}
	
	public static double mean(List<Double> numbers) {
	    if (numbers == null || numbers.isEmpty()) {
	        return 0;
	    }

	    double sum = 0.0;
	    for (double num : numbers) {
	        sum += num;
	    }
	    double mean = sum / numbers.size();	    
	    return  mean;
	    
	}
	
	public static double slope(double x1, double y1, double x2, double y2) {
		Double division;
        if (x2 == x1) {
        	division = 1.0;
        }else {
        	division = (Double) (x2 - x1);
        }
        double slope = (y2 - y1) / division ;
        return Math.round(slope*1000.0)/1000.0;
    }

	public static double distance(double x1, double y1, double x2, double y2) {
		double dist = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
		return Math.round(dist*1000.0)/1000.0;
    }

}
