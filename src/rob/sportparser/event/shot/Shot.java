package rob.sportparser.event.shot;

import rob.sportparser.Match;
import rob.sportparser.Player;
import rob.sportparser.event.Event;

public class Shot extends Event {
	
	private Match match;
	private ShotBodyPart bodyPart;
	private ShotDirection direction;
	private boolean goal;
	private Player assist;
	private ShotResultLocation resultLocation;
	private ShotTakenLocation takenLocation;

	public Shot(Match match, Player player, int time, int injuryTime, ShotBodyPart bodyPart, ShotDirection direction, boolean goal, 
			Player assist, ShotResultLocation resultLocation, ShotTakenLocation takenLocation) {
		this.setPlayer(player);
		this.setTime(time);
		this.setInjuryTime(injuryTime);
		this.setBodyPart(bodyPart);
		this.setDirection(direction);
		this.setGoal(goal);
		this.setAssist(assist);
		this.setResultLocation(resultLocation);
		this.setTakenLocation(takenLocation);
		this.setMatch(match);
	}

	/**
	 * @return the bodyPart
	 */
	public ShotBodyPart getBodyPart() {
		return bodyPart;
	}

	/**
	 * @param bodyPart the bodyPart to set
	 */
	public void setBodyPart(ShotBodyPart bodyPart) {
		this.bodyPart = bodyPart;
	}

	/**
	 * @return the direction
	 */
	public ShotDirection getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(ShotDirection direction) {
		this.direction = direction;
	}

	/**
	 * @return the goal
	 */
	public boolean isGoal() {
		return goal;
	}

	/**
	 * @param goal the goal to set
	 */
	public void setGoal(boolean goal) {
		this.goal = goal;
	}

	/**
	 * @return the assist
	 */
	public Player getAssist() {
		return assist;
	}

	/**
	 * @param assist the assist to set
	 */
	public void setAssist(Player assist) {
		this.assist = assist;
	}

	/**
	 * @return the resultLocation
	 */
	public ShotResultLocation getResultLocation() {
		return resultLocation;
	}

	/**
	 * @param resultLocation the resultLocation to set
	 */
	public void setResultLocation(ShotResultLocation resultLocation) {
		this.resultLocation = resultLocation;
	}

	/**
	 * @return the takenLocation
	 */
	public ShotTakenLocation getTakenLocation() {
		return takenLocation;
	}

	/**
	 * @param takenLocation the takenLocation to set
	 */
	public void setTakenLocation(ShotTakenLocation takenLocation) {
		this.takenLocation = takenLocation;
	}

	/**
	 * @return the match
	 */
	public Match getMatch() {
		return match;
	}

	/**
	 * @param m the match to set
	 */
	public void setMatch(Match match) {
		this.match = match;
	}

	public String toFullString() {
		String timeString = this.getTime()+"";
		if(this.getInjuryTime()!=0){
			timeString += "+"+this.getInjuryTime();
		}
		String shotString = "Shot";
		String assistString = "";
		if(goal) {
			shotString = "Goal";
			if(this.getAssist() != null) {
				assistString = " (assist " + this.getAssist() + ")";
			}			
		}
		String missText = "";
		if(resultLocation != null) {
			missText = " (" + this.getResultLocation() + ")";
		}
		return shotString + " at " + timeString + " from " + this.getPlayer() + " (" + this.getBodyPart() + ", " 
			+ this.getDirection() + ")" + assistString + missText + " (" + takenLocation + ")";

	}
	
	@Override
	public String toString() {
		String timeString = this.getTime()+"";
		if(this.getInjuryTime()!=0){
			timeString += "+"+this.getInjuryTime();
		}
		String shotString = "SHOT";
		if(goal) {
			shotString = "GOAL";		
		}
		return match.getId() + "," + shotString + "," + timeString + "," + this.getBodyPart() + "," 
			+ this.getDirection() + "," + this.getResultLocation() + "," + takenLocation + ",";

	}

}
