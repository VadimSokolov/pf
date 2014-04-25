package pf.test;

//The implementation is partially based on this paper
//http://www.guoqiangmao.com/Publications_files/Mao06Design.pdf
public class BoatState {
	
	public BoatState(double _x, double _y, double _heading, double _dheading, double _ground_speed)
	{
		x = _x;
		y = _y;
		heading = _heading;
		dheading = _dheading;
		ground_speed = _ground_speed;
		length = 10.9;
	}
	//dt is time step
	public BoatState Propagate(double dt)
	{
		double x1, y1, heading1; 
		double d = ground_speed*dt; //distance traveled
		double alpha = dheading*dt;
		double theta = heading;
		double beta = d/this.length * Math.tan(alpha);
		if (Math.abs(beta) >= 0.001)
		{
			double R = this.length/Math.tan(alpha);
			x1 = x - Math.sin(theta)*R + Math.sin(theta+beta)*R;
			y1 = y + Math.cos(theta)*R - Math.cos(theta+beta)*R;
			
			
		}
		else {
			x1 = x + d*Math.cos(theta);
			y1 =  y + d*Math.sin(theta);
		}
		heading1 = (theta+beta)%(2.0*Math.PI);
		//we assume ground speed and angular velocity are not changing (we do not measure acceleration and angular acceleration) 
		return new BoatState(x1, y1, heading1,dheading, ground_speed);
		
	}


	
	public double x;
	public double y;
	public double heading; // orientation of the boat
	public double dheading; //angular speed
	public double length;
	public double ground_speed;

}
