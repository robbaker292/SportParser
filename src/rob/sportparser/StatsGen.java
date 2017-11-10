package rob.sportparser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import rob.sportparser.builders.MatchBuilder;
import rob.sportparser.builders.PlayerBuilder;
import rob.sportparser.event.Event;
import rob.sportparser.event.shot.Shot;
import rob.sportparser.expectedgoals.ExpectedGoalsGen;
import rob.sportparser.generators.ShotsGen;


public class StatsGen {
	
	private ArrayList<Match> matches = new ArrayList<>();
	private ArrayList<Player> players = new ArrayList<>();
	
	/**
	 * Main method
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main (String[] args) throws IOException, InterruptedException {
		new StatsGen();
	}

	public StatsGen() throws IOException {
				
		MatchBuilder.buildMatches(matches);
		//MatchBuilder.buildAllLeagueMatches(matches);
		PlayerBuilder.buildPlayers(players);
		for(Match m : matches) {
			new DataExtractor(m.getId(), m.getEvents(), players, m);
			
			//System.out.println(m.getLineup().values());
		}

		//TODO change these for different analysis
		ShotsGen sg = new ShotsGen(matches, players);
		//sg.calculateShotResultLocations();		
		
		//sg.saveShots();
		ExpectedGoalsGen ex = new ExpectedGoalsGen(matches);
		//ex.calculateExpectedGoals();
		//ex.saveExpectedGoalsClassifiers();
		ex.loadExpectedGoalsClassifiers();
		getPlayerGoalsVsXG(ex);
		/*
		for(Match m : matches) {
			double xG = ex.getExpectedGoalMatch(m);
			System.out.println(m.getId() + "\t" + m.getNumberGoals() + "\t" + xG);
		}
		*/
		//totalFouls(matches);		
		
		//sg.totalShots();
		
		//sg.printShots();
		/*HashMap<Player, Integer> starts = calculateMinutes();
		for(Player p : starts.keySet()) {
			System.out.println(p + "," + starts.get(p));
		}*/
	}	
	
	/**
	 * Counts the number of starting appearances for each player
	 */
	public HashMap<Player, Integer> calculateStarts() {
		HashMap<Player, Integer> starts = new HashMap<Player, Integer>();
		for(Match m : matches) {
			for(LineupData ld : m.getLineup().values()) {
				//todo count if starting and add to hashmap
				Player p = ld.getPlayer();
				if(ld.isStarting()) {
					if (starts.containsKey(p)) {
						starts.put(p, starts.get(p)+1);
					} else {
						starts.put(p,1);
					}					
				}			
			}
		}		
		return starts;
	}
	
	/**
	 * Counts the number of minutes for each player
	 */
	public HashMap<Player, Integer> calculateMinutes() {
		HashMap<Player, Integer> mins = new HashMap<Player, Integer>();
		for(Match m : matches) {
			for(LineupData ld : m.getLineup().values()) {
				//todo count if starting and add to hashmap
				Player p = ld.getPlayer();
				if (mins.containsKey(p)) {
					mins.put(p, mins.get(p) + ld.getMinutes());
				} else {
					mins.put(p, ld.getMinutes());
				}							
			}
		}		
		return mins;
	}
	
	/**
	 * Lists the players and the number of goals and expected goals, they should have scored
	 */
	public void getPlayerGoalsVsXG(ExpectedGoalsGen ex) {
		HashMap<Player, Integer> goals = new HashMap<>();
		HashMap<Player, Double> xG = new HashMap<>();
		HashMap<Player, Integer> minutes = calculateMinutes();
		
		for(Match m : matches) {
			for(Event e : m.getEvents()) {
				if(e instanceof Shot) { //only shots!
					Shot s = (Shot) e;
					
					//find player, etc
					Player p = s.getPlayer();
					if(goals.containsKey(p)) {
						if(s.isGoal()) {
							goals.put(p, goals.get(p) + 1);
						}
						xG.put(p, goals.get(p) + ex.getExpectedGoalNumber(s));
						//update
					} else {
						//add to both
						if(s.isGoal()) {
							goals.put(p, 1);
						} else {
							goals.put(p, 0);
						}
						xG.put(p, ex.getExpectedGoalNumber(s));							
					}						
					
				}
			}
		}
		
		System.out.println("player,goals,xG,mins,goalsPer90,xGPer90");
		for(Player p : goals.keySet()) {
			double xGPer90 = (90 * xG.get(p)) / (double) minutes.get(p);
			double goalsPer90 = (90 * goals.get(p)) / (double) minutes.get(p);
			
			System.out.println(p.getNames()[0] + "," + goals.get(p) + "," + xG.get(p) + "," + minutes.get(p) + "," + goalsPer90 + "," + xGPer90 + ",");
		}
		//output		
	}	
}
