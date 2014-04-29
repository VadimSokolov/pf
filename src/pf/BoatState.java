package pf;

//The implementation is partially based on this paper
//http://www.guoqiangmao.com/Publications_files/Mao06Design.pdf
public class BoatState {
	
	public BoatState(double _x, double _y, double _heading, double _heading_prev, double _ground_speed)
	{
		x = _x;
		y = _y;
		heading = _heading;
		dheading = (_heading - _heading_prev)/0.5;
		ground_speed = _ground_speed;
		length = 10.9;
		heading_prev = _heading;
	}
	
	public double x;
	public double y;
	public double heading; // orientation of the boat
	double dheading; //angular speed
	public double length;
	public double ground_speed;
	double heading_prev; //heading measured at previous time step

}
