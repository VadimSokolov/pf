package pf.test;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Unittest {
	public static final double PI = 3.14159265359;
	public static final double SQRTTWOPI =  2.50662827463;
	public static void main(String[] args) {
		List<Double> weight = new ArrayList<Double>();
		List<Double> prior = new ArrayList<Double>();
		int N = 100000;
		Random r = new  Random();
		double rn = 0.0;
			
		for (int i = 0; i<N; i++) {
			rn = r.nextDouble() * 4;
			weight.add(1/SQRTTWOPI * Math.exp(-rn*rn/2));
			prior.add(rn);
		}
		List<Double> resampled = SamplingWheel.Sample(weight, prior);
		int count = 0;
		for (int i = 0; i<N; i++)
		{
			if (Math.abs(resampled.get(i)) < 1 ) {
				count += 1;
			}
				
		}
		//System.out.println(Double.toString(100*(Math.abs(count - N*0.682)/N)));
		assert (100*(Math.abs(count - N*0.682))/N <2);
		System.out.println("SamplingWheel test passed");		
		System.out.println("ALL TESTS PASSED");
	}

}
