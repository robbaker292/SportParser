package rob.sportparser;
import java.util.ArrayList;
import java.util.HashMap;

import rob.sportparser.event.Event;
import rob.sportparser.event.shot.Shot;

/**
 * Class to present a match
 * Will contain a list of players, etc.
 * @author Rob
 *
 */
public class Match {

	private String id;
	private boolean home;
	private ArrayList<Event> events = new ArrayList<>(); //list of events of this match
	private HashMap<Player, LineupData> lineup = new HashMap<>(); //lineup for this game

	/**
	 * Constructor to build the match
	 * 
	 * @param id The sportingLife ID of the match
	 * @param home If Gills are at home
	 */
	public Match(String id, boolean home) {
		this.id = id;
		this.home = home;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the home
	 */
	public boolean isHome() {
		return home;
	}

	/**
	 * @param home the home to set
	 */
	public void setHome(boolean home) {
		this.home = home;
	}

	/**
	 * @return the events
	 */
	public ArrayList<Event> getEvents() {
		return events;
	}

	/**
	 * @param events the events to set
	 */
	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}
	
	/**
	 * Adds an event to the lineup
	 * @param ld
	 */
	public void addLinuep(LineupData ld) {
		lineup.put(ld.getPlayer(),ld);
	}

	/**
	 * @return the lineup
	 */
	public HashMap<Player, LineupData> getLineup() {
		return lineup;
	}
	
	public int getNumberGoals() {
		int result = 0;
		for(Event e : events) {
			if(e instanceof Shot) { //only shots!
				Shot s = (Shot) e;
				if(s.isGoal()) {
					result++;
				}
			}
		}
		return result;
	}
	
}
