package rob.sportparser.generators;

import java.util.ArrayList;
import java.util.HashMap;
import rob.sportparser.Match;
import rob.sportparser.Player;
import rob.sportparser.event.Event;
import rob.sportparser.event.Foul;

public class FoulsGen {
	
	private ArrayList<Match> matches;
	
	public FoulsGen(ArrayList<Match> matches) {
		this.matches = matches;
	}

	/**
	 * Counts the number of fouls each player made
	 */
	public void totalFouls() {
		HashMap<Player, Integer> fouls = new HashMap<>();
		for(Match m : matches) {
			for(Event e : m.getEvents()) {
				if(e instanceof Foul) {
					if (fouls.containsKey(e.getPlayer())) {
						fouls.put(e.getPlayer(), fouls.get(e.getPlayer())+1);
					} else {
						fouls.put(e.getPlayer(), 1);
					}
				}
			}
		}
		for(Player p : fouls.keySet()) {
			System.out.println(p + "," + fouls.get(p));
		}
	}
}
