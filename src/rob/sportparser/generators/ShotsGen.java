package rob.sportparser.generators;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import rob.sportparser.Match;
import rob.sportparser.Player;
import rob.sportparser.event.Event;
import rob.sportparser.event.shot.Shot;
import rob.sportparser.event.shot.ShotDirection;
import rob.sportparser.event.shot.ShotResultLocation;

public class ShotsGen {
	
	private ArrayList<Match> matches;
	private ArrayList<Player> players;
	
	public ShotsGen(ArrayList<Match> matches, ArrayList<Player> players) {
		this.matches = matches;
		this.players = players;
	}

	/**
	 * Prints all shots
	 */
	public void printShots() {
		for(Match m : matches) {
			for(Event e : m.getEvents()) {
				if(e instanceof Shot) {
					//if(((Shot) e).getDirection() == ShotDirection.OFF_TARGET ) {
						System.out.println(e.toString());
					//}					
				}
			}
		}
	}
	
	/**
	 * Saves all shots to file
	 */
	public void saveShots() {
        try {
    		BufferedWriter writer = null;
            writer = new BufferedWriter(new FileWriter("d://commentary/shots.csv"));
            for(int i=0; i < matches.size(); i++) {
            	Match m = matches.get(i);
            	
            	if(i%20==0) {
            		System.out.println("Reading match "+i+" of "+matches.size());
            	}
            	
    			for(Event e : m.getEvents()) {
    				if(e instanceof Shot) {
    					writer.write(e.toString()+"\n");
    					//System.out.println(e.toString());				
    				}
    			}
    		}
            writer.close();
            System.out.println("Shots saved");
        }
        catch (IOException e) {
        }

	}
	
	public void calculateShotResultLocations() {
		HashMap<Player, Map<ShotResultLocation, Integer>> playerShots = new HashMap<>();
		for(Player p : players) {
			Map<ShotResultLocation, Integer> shots = new EnumMap<ShotResultLocation, Integer>(ShotResultLocation.class);
			for(ShotResultLocation srl : ShotResultLocation.values()) {
				shots.put(srl, 0); //add all values to map
			}			
			playerShots.put(p,shots);
		}
		
		for(Match m : matches) {
			for(Event e : m.getEvents()) {
				if(e instanceof Shot) {
					Shot s = (Shot) e;
					Player p = s.getPlayer();
					Map<ShotResultLocation, Integer> shots = playerShots.get(p);
					int value = shots.get(s.getResultLocation()) + 1;
					shots.put(s.getResultLocation(), value);							
				}
			}
		}
		
		System.out.print("name,");
		for(ShotResultLocation srl : ShotResultLocation.values()) {
			System.out.print(srl + ",");
		}
		System.out.println();
		
		for(Player p : playerShots.keySet()) {
			System.out.print(p + ",");
			Map<ShotResultLocation, Integer> shots = playerShots.get(p);
			for(int value : shots.values()) {
				System.out.print(value + ",");
			}			
			System.out.println();
		}
	}
	
	/**
	 * Counts the number of shots for each player, broken down by type
	 * @author Rob
	 */
	class ShotHolder {
		int goal = 0, on = 0, off = 0, blocked = 0;
	}
	public void totalShots() {
		int total = 0;
		HashMap<Player, ShotHolder> shots = new HashMap<>();
		for(Match m : matches) {
			for(Event e : m.getEvents()) {
				if(e instanceof Shot) {
					ShotHolder sh;
					if (shots.containsKey(e.getPlayer())) {
						sh = shots.get(e.getPlayer());				
					} else {
						sh = new ShotHolder();
					}
					addShot((Shot) e, e.getPlayer(), sh);
					shots.put(e.getPlayer(), sh);
					total++;
					
					if(((Shot) e).getResultLocation() == ShotResultLocation.UNKNOWN) {
						System.out.println("unknown");
					}
				}
			}
		}
		System.out.println("name,goal,on,off,blocked");
		for(Player p : shots.keySet()) {
			System.out.println(p + "," + shots.get(p).goal + "," + shots.get(p).on + "," + shots.get(p).off + "," + shots.get(p).blocked);
		}
		System.out.println();
		System.out.println("total: "+total);
	}
	/**
	 * Adds shots to a holding class
	 * @param s Shot
	 * @param p Player
	 * @param sh ShotHolder
	 */
	private void addShot(Shot s, Player p, ShotHolder sh) {
		if(s.isGoal()) {
			sh.goal++;
		} else if(s.getDirection() == ShotDirection.ON_TARGET) {
			sh.on++;
		} else if(s.getDirection() == ShotDirection.OFF_TARGET) {
			sh.off++;
		} else if(s.getDirection() == ShotDirection.BLOCKED) {
			sh.blocked++;
		}
	}
}
