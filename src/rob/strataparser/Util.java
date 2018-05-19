package rob.strataparser;

import java.util.ArrayList;

public class Util {
	/**
	 * Determines what player is in the piece of text
	 * @param text The text to search
	 * @param player If it doesn't matter about what player it is
	 * @return
	 */
	public static Player findPlayer(ArrayList<Player> players, String text, boolean dummy) {
		if(dummy) {
			return players.get(0);
		}
		
		for(Player player : players) {
			for(String name : player.getNames()) {
				if(text.contains(name)) {
					return player;
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets a match given a id
	 * @param matchId The match id
	 * @return The match, or null if none found
	 */
	public static Match findMatch(ArrayList<Match> matches, String matchId) {
		for(Match match : matches) {
			if (match.getId().equals(matchId)) {
				return match;
			}
		}
		return null;
	}
	
	/**
	 * Gets a player from their id
	 * @param id the player id
	 * @return the player, or null if none found
	 */
	public static Player getPlayer(ArrayList<Player> players, int id) {
		for(Player p : players) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}
}
