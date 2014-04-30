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
		double x1,y1,dx,dy, heading1, speed1; 
		double d = state.ground_speed*dt; //distance traveled
        dy = -d * Math.sin(2*Math.PI-Math.PI*state.heading/180);
        dx = d * Math.cos(2*Math.PI-Math.PI*state.heading/180);
		y1 = state.y + (dy*180)/(Utils.R*Math.PI*Math.cos(state.x*Math.PI/180));
		x1 = state.x + (dx*180)/(Utils.R*Math.PI);
		heading1 = state.heading + r.nextGaussian()*0.034; //2 degree;
		speed1 = state.ground_speed + r.nextGaussian()*1;// 1 m/s
		//we assume ground speed and angular velocity are not changing (we do not measure acceleration and angular acceleration) 
		return new BoatState(x1, y1, heading1, speed1);		
	}
	@Override
	public Particle<BoatState,BoatMeasurement> ApplyFilter(Collection<BoatMeasurement> m) {
		//propagate
		BoatState state1 = Propagate(dt);
		double weight1 = 1.0;
		for (BoatMeasurement item : m)
		{
			weight1 *= this.Likelihood(item,Boat.gps_std*Boat.gps_std);
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