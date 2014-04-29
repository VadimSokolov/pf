package pf;
import java.util.Collection;
import java.util.Random;

import pf.Boat;

public class BoatParticle implements Particle<BoatState,BoatMeasurement> {
	
	BoatState state;
	double weight;
	double dt = 0.5;//sec

	
	public BoatParticle(BoatState s, double w)
	{
		this.state = s;
		this.weight = w;
	}
	
	//dt is time step
	public Particle<BoatState,BoatMeasurement> Propagate()
	{
		return new BoatParticle(this.Propagate(0.5), this.weight);
		
	}	
	//dt is time step
	BoatState Propagate(double dt)
	{
		Random r = new Random();
		double x1, y1, heading1; 
		double d = state.ground_speed*dt; //distance traveled
		double alpha = state.dheading*dt + r.nextGaussian()*0.034; //2 degree;
		double theta = state.heading;
		double beta = d/state.length * Math.tan(alpha);
		if (Math.abs(beta) >= 0.001)
		{
			double R = state.length/Math.tan(alpha);
			x1 = state.x - Math.sin(theta)*R + Math.sin(theta+beta)*R;
			y1 = state.y + Math.cos(theta)*R - Math.cos(theta+beta)*R;			
		}
		else {
			x1 = state.x + d*Math.cos(theta);
			y1 =  state.y + d*Math.sin(theta);
		}
		heading1 = (theta+beta)%(2.0*Math.PI);
		//we assume ground speed and angular velocity are not changing (we do not measure acceleration and angular acceleration) 
		return new BoatState(x1, y1, heading1,state.heading, state.ground_speed);
		
	}
	@Override
	public Particle<BoatState,BoatMeasurement> ApplyFilter(Collection<BoatMeasurement> m) {
		//propagate
		BoatState state1 = Propagate(dt);
		double weight1 = 1.0;
		for (BoatMeasurement item : m)
		{
			weight1 *= this.Likelihood(item,Boat.gps_var);
		}
		return new BoatParticle(state1, weight1);
	}
	
	public double Likelihood(BoatMeasurement m, double var)
	{
		double l = 1.0;
		l *= Utils.dnorm(state.x, m.lat, var);
		l *= Utils.dnorm(state.y, m.lon, var);
		return l;		
	}

}	