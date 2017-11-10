package rob.sportparser.expectedgoals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import rob.sportparser.Match;
import rob.sportparser.event.Event;
import rob.sportparser.event.shot.Shot;
import rob.sportparser.event.shot.ShotBodyPart;
import rob.sportparser.event.shot.ShotDirection;
import rob.sportparser.event.shot.ShotResultLocation;
import rob.sportparser.event.shot.ShotTakenLocation;

/**
 * Generates, saves and loads the expected goal calculations 
 * @author Rob
 *
 */
public class ExpectedGoalsGen {

	ArrayList<Match> matches = new ArrayList<>();
	ArrayList<ExpectedGoalHolder> holders = new ArrayList<>();
	
	public ExpectedGoalsGen(ArrayList<Match> matches) {
		this.matches = matches;
	}
	
	/**
	 * Calculates the expected goals from all the shots given.
	 * NOTE do not use to determine the xG from a series of shots in one game, this is for determining the base values
	 */
	public void calculateExpectedGoals() {
		System.out.println("generating xG");
		//for each shot
		int totalGoals = 0;
		int totalMisses = 0;
		for(Match m : matches) {
			for(Event e : m.getEvents()) {
				if(e instanceof Shot) { //only shots!
					Shot s = (Shot) e;
					
					//if(s.getDirection() != ShotDirection.ON_TARGET) {
					//	continue;
					//}
					
					ExpectedGoalHolder holder = findHolder(s.getTakenLocation());
					if(s.isGoal()) {
						holder.incrementGoalCount();
						totalGoals++;
					} else {
						holder.incrementNoGoalCount();
						totalMisses++;
					}				
				}			
			}
		}
		for(ExpectedGoalHolder h : holders) {
			if(h.getTakenLocation() == ShotTakenLocation.UNKNOWN) {
				h.setExpectedGoal(totalGoals / (double) (totalGoals + totalMisses)	);
				//have to assume goals from freekicks (which are unknowns), have the average xG
			} else {
				h.setExpectedGoal(h.getGoalCount() / (double) (h.getGoalCount() + h.getNoGoalCount())	);
			}						
		}
		
		//for each shot
			//find holder that matches:
				//header/not header
				//on/off/blocked
				//shot result location
				//shot taken location
				
				//if goal, add 1 to goal counter for this holder, if no goal, add 1 to no goal
		
		//for each shot holder, output xG
		
	}
	
	/**
	 * Returns the holder that matches the shot parameters. If none is found, create one.
	 * 
	 * @param bodyPart
	 * @param direction
	 * @param resultLocation
	 * @param takenLocation
	 * @return
	 */
	private ExpectedGoalHolder findHolder(ShotTakenLocation takenLocation) {
		for(ExpectedGoalHolder h : holders) {
			if(h.getTakenLocation()==takenLocation) {
				return h;
			}
		}
		ExpectedGoalHolder egh = new ExpectedGoalHolder(takenLocation);
		holders.add(egh);
		return egh ;
	}
	
	/**
	 * Save the holders for later use
	 */
	public void saveExpectedGoalsClassifiers() {
        try {
    		BufferedWriter writer = null;
            writer = new BufferedWriter(new FileWriter("d://commentary/xg.csv"));
            writer.write("type,goals,nogoals,xg,\n");
            for(ExpectedGoalHolder h : holders) {
    			writer.write(h.toString()+"\n");
    		}
            writer.close();
            System.out.println("Expected Goals saved");
        }
        catch (IOException e) {
        }
	}
	
	/**
	 * Load the holders for current use
	 */
	public void loadExpectedGoalsClassifiers() {
		System.out.println("Reading Expected goals");
		//load classifiers and build up holders
		try(BufferedReader br = new BufferedReader(new FileReader("d://commentary/xg.csv"))) {
			br.readLine(); //skip title line
		    String line = br.readLine();
		    while (line != null) {
		    	String[] arr = line.split(",");
		    	
		    	ShotTakenLocation stl = ShotTakenLocation.valueOf(arr[0]);
		    	int goals = Integer.parseInt(arr[1]);
		    	int misses = Integer.parseInt(arr[2]);
		    	double xG = Double.parseDouble(arr[3]);
		    	
				ExpectedGoalHolder egh = new ExpectedGoalHolder(stl);
				egh.setGoalCount(goals);
				egh.setNoGoalCount(misses);
				egh.setExpectedGoal(xG);
				holders.add(egh);
		    	
		        line = br.readLine();
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Reading Expected complete");		
	}
	
	/**
	 * Determine the xG of the given Shot
	 * @param s The shot
	 * @return The xG of this shot
	 */
	public double getExpectedGoalNumber(Shot s) {
		//for the given shot, calculate its expected goal
		ExpectedGoalHolder holder = findHolder(s.getTakenLocation());
		return holder.getExpectedGoal();
	}
	
	public double getExpectedGoalMatch(Match m) {
		double result = 0.0;
		for(Event e : m.getEvents()) {
			if(e instanceof Shot) { //only shots!
				Shot s = (Shot) e;
				result += getExpectedGoalNumber(s);
			}
		}
		return result;
	}
	
}
