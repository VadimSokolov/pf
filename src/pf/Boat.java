package pf;
import java.util.Random;


public class Boat
{
	public static double dheading_var = 0.1;
	public static double ground_speed_var = 0.5;
	public static double gps_var = 0.0001;
	public static double heading_var = 1;
	
	public static BoatState NoisBoatState (double _x, double _y, double _heading, double _dheading, double _ground_speed)
	{
		Random r = new  Random();
		return new BoatState(_x + r.nextGaussian()*Math.sqrt(Boat.gps_var), 
						 _y + r.nextGaussian()*Math.sqrt(Boat.gps_var),
						 _heading +r.nextGaussian()*Math.sqrt(Boat.heading_var),
						 _heading + r.nextGaussian()*Math.sqrt(Boat.heading_var),
						 _ground_speed + r.nextGaussian()*Math.sqrt(Boat.ground_speed_var));
	}
	
}





