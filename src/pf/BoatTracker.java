package pf;
import java.util.ArrayList;

import pf.Boat;



public class BoatTracker {
	
	public static void TrackBoat()
	{
//		time, lat, long, from, GPS, sensor
		int[] time = {142020, 142024, 142026, 142030, 142032, 142036, 142038, 142042, 142044, 142048, 142050, 142054, 142056, 142060, 142062};
		double[] latN = {4149.99, 4149.98, 4149.98, 4149.97, 4149.97, 4149.96, 4149.96, 4149.96, 4149.95, 4149.95, 4149.94, 4149.94, 4149.94, 4149.93, 4149.93};
		double[] lonE = {-08732.48, -08732.48, -08732.47, -08732.46, -08732.46, -08732.45, -08732.45, -08732.44, -08732.44, -08732.43, -08732.43, -08732.42, -08732.41, -08732.41, -08732.40};
		
//		speed, heading, from, boat, sensors
		int[] hdgT = {129, 128, 130, 129, 129, 129, 128, 129, 126, 126, 126, 127, 128, 127, 126};
		double[] sowK = {12.27, 12.52, 12.23, 12.31, 12.49, 12.45, 12.49, 12.31, 12.52, 12.49, 12.49, 12.42, 12.23, 12.42, 12.34};		
		int meas = 0;
		int mn = time.length;
		//adjust the coordinates
		for (int i = 0; i < mn; i++)
		{
			latN[i] = latN[i]/100;
			lonE[i] = lonE[i]/100;
		}
		double dt = 0.5;//sec
		double current_time = 142020;//initial time
		int N = 5000;
		ArrayList<BoatParticle> particles = new ArrayList<BoatParticle>(); 
		BoatState s;
	
		//create initial particles
		for (int i = 0; i< N; i++)
		{
			s = Boat.NoisBoatState(latN[0], lonE[0], hdgT[0], 0, sowK[0]);
			particles.add(new BoatParticle(s,1));	
			System.out.println(s.x + ","+s.y);
		}
		
	}

	public static void main(String[] args) {
		BoatTracker.TrackBoat();

	}

}
