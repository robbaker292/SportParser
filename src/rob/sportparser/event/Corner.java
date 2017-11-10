package rob.sportparser.event;

import rob.sportparser.Player;

/**
 * Corners
 */
public class Corner extends Event {

	public Corner(Player player, int time, int injuryTime) {
		this.setPlayer(player);
		this.setTime(time);
		this.setInjuryTime(injuryTime);
	}

	@Override
	public String toString() {
		if(this.getInjuryTime()==0) {
			return "Corner at " + this.getTime() +  "min";
		} else {
			return "Corner at " + this.getTime() + "+" + this.getInjuryTime() +  "min";
		}
	}

}
