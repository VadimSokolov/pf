package pf.test;

import java.util.Collection;



public class Boat implements Particle<BoatState,BoatMeasurement> {
	
	BoatState state;
	double weight;
	double dt = 0.5;//sec
	
	public Boat(BoatState s, double w)
	{
		this.state = s;
		this.weight = w;
	}

	@Override
	public Particle<BoatState,BoatMeasurement> ApplyFilter(Collection<BoatMeasurement> m) {
		//propagate
		BoatState state1 = state.Propagate(dt);
		double weight1 = 1.0;
		for (BoatMeasurement item : m)
		{
			weight1 *= this.Likelihood(item);
		}
		return new Boat(state1, weight1);
	}
	
	public double Likelihood(BoatMeasurement m)
	{
		double l = 1.0;
		l *= dnorm(state.x, m.lat, 0.001);
		l *= dnorm(state.y, m.lon, 0.001);
		return l;		
	}
	public double dnorm(double x, double mean, double std)
	{
		double res;
		res = Math.exp(-Math.pow(x-mean,2)/(2*std*std));
		return res;
		
	}

}


