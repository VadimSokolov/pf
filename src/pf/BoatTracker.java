package pf;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
		int mn = time.length;
		//adjust the coordinates
		for (int i = 0; i < mn; i++)
		{
			latN[i] = latN[i]/100;
			lonE[i] = lonE[i]/100;
		}
		double current_time = 142020.5;//initial time
		int N = 500;
		ArrayList<BoatParticle> particles = new ArrayList<BoatParticle>(N); 
		BoatState s;
		File file = new File("pf-out.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		FileWriter fw = null;
		try {
			fw = new FileWriter(file.getAbsoluteFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(fw);
		//create initial particles
		for (int i = 0; i< N; i++)
		{
			s = Boat.NoisBoatState(latN[0], lonE[0], hdgT[0], 0, sowK[0]);
			particles.add(new BoatParticle(s,1));	
//			System.out.println(s.x + ","+s.y);
		}
		int current_ind = 1;
		ArrayList<BoatParticle> posterior_particles = new ArrayList<BoatParticle>(N); 
		ArrayList<BoatMeasurement> m = new ArrayList<BoatMeasurement>(1);
		BoatMeasurement melement = new BoatMeasurement();
		m.add(melement);
		while (current_time <=time[mn-1]+1)
		{
			
			if (current_ind < mn && time[current_ind] == current_time)
			{
				for (int i = 0; i< N; i++)
				{
//					try {
//						bw.write(current_time+","+particles.get(i).state.x + ","+particles.get(i).state.y+"\n");
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					melement.lat = latN[current_ind];
					melement.lon = lonE[current_ind];
					m.set(0, melement);
					posterior_particles.set(i, (BoatParticle)particles.get(i).ApplyFilter(m));
				}
				current_ind++;
			}
			else {
				for (int i = 0; i< N; i++)
				{
//					try {
//						bw.write(current_time+","+particles.get(i).state.x + ","+particles.get(i).state.y+"\n");
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					posterior_particles.add((BoatParticle)particles.get(i).Propagate());
				}				

			}
			double meanx = 0;
			double meany = 0;
			for (int i = 0; i< N; i++)
			{
				meanx += posterior_particles.get(i).state.x;
				meany += posterior_particles.get(i).state.y;

			}		
			try {
			bw.write(current_time+","+meanx + ","+meany+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
			current_time+=0.5;
			particles = posterior_particles;
		}
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		BoatTracker.TrackBoat();

	}

}
