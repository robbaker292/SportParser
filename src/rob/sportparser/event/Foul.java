package rob.sportparser.event;

import rob.sportparser.Player;

public class Foul extends Event {
	
	/**
	 * 
	 * @param player
	 * @param time
	 * @param injuryTime
	 */
	public Foul(Player player, int time, int injuryTime) {
		this.setPlayer(player);
		this.setTime(time);
		this.setInjuryTime(injuryTime);
	}

	@Override
	public String toString() {
		String timeString = this.getTime()+"";
		if(this.getInjuryTime()!=0){
			timeString += this.getInjuryTime();
		}
		return "Foul at " + timeString + " from " + this.getPlayer();
	}

}
