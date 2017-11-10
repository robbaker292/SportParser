package rob.sportparser;
/**
 * Stores the minutes played by each player in a match
 * @author Rob
 *
 */
public class LineupData {

	private Player player;
	private boolean starting;
	private int minutes;
	private Match match;
	
	/**
	 * @param player
	 * @param starting
	 * @param minutes
	 */
	public LineupData(Match match, Player player, boolean starting, int minutes) {
		this.setMatch(match);
		this.player = player;
		this.starting = starting;
		this.minutes = minutes;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the starting
	 */
	public boolean isStarting() {
		return starting;
	}

	/**
	 * @param starting the starting to set
	 */
	public void setStarting(boolean starting) {
		this.starting = starting;
	}

	/**
	 * @return the minutes
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * @param minutes the minutes to set
	 */
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	/**
	 * @return the match
	 */
	public Match getMatch() {
		return match;
	}

	/**
	 * @param match the match to set
	 */
	public void setMatch(Match match) {
		this.match = match;
	}
	
	@Override
	public String toString() {
		return getPlayer() + " (" + getMinutes() + ")";
	}
	
}
