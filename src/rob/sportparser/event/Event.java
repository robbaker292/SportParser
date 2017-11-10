package rob.sportparser.event;

import rob.sportparser.Player;

/**
 * Abstract class for Event.
 * Stores the time of the event (45 or 90 if in injury time), 
 * 	and the injury time, so that 45+1 is treated differently from 46
 * 	as well as the player involved.
 * 
 * @author Rob
 *
 */
public abstract class Event {

	private int time, injuryTime;
	private Player player;
	
	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}
	/**
	 * @return the injuryTime
	 */
	public int getInjuryTime() {
		return injuryTime;
	}
	/**
	 * @param injuryTime the injuryTime to set
	 */
	public void setInjuryTime(int injuryTime) {
		this.injuryTime = injuryTime;
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
	 * String representation of the event
	 */
	@Override
	public abstract String toString();
}
