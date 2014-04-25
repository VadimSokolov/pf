package pf.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SamplingWheel {
	public static List<Double> Sample(List<Double> weights, List<Double> particles)
	{
		List<Double> res = new ArrayList<Double>();
		int N = weights.size();
		int index = (int)(Math.random() * N);
		double beta = 0.0;
		double mw = Collections.max(weights);
		for (int i = 0; i<N; i++)
		{
			beta += Math.random() * 2.0 * mw;
			while (beta > weights.get(index))
			{
				beta -= weights.get(index);
				index = (index+1) % N;				
			}
			res.add(particles.get(index));
		}
		return res;
	}
}
