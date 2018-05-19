package rob.strataparser.chance;

import rob.strataparser.Player;

public class Chance {

	private String team;
	private ChanceQuality quality;
	private ChanceIcon icon;
	private ChanceType type;
	private String time;
	private Player player;
	private int x, y;
	private ChanceBodyPart bodyPart;
	private ChanceOutcome outcome;
	private Assist assist, secondaryAssist;
	/**
	 * @param team
	 * @param quality
	 * @param icon
	 * @param type
	 * @param time
	 * @param player
	 * @param x
	 * @param y
	 * @param bodyPart
	 * @param outcome
	 */
	public Chance(String team, ChanceQuality quality, ChanceIcon icon, ChanceType type, String time, Player player,
			int x, int y, ChanceBodyPart bodyPart, ChanceOutcome outcome, Assist assist, Assist secondaryAssist) {
		this.team = team;
		this.quality = quality;
		this.icon = icon;
		this.type = type;
		this.time = time;
		this.player = player;
		this.x = x;
		this.y = y;
		this.bodyPart = bodyPart;
		this.outcome = outcome;
		this.assist = assist;
		this.secondaryAssist = secondaryAssist;
	}
	/**
	 * @return the team
	 */
	public String getTeam() {
		return team;
	}
	/**
	 * @param team the team to set
	 */
	public void setTeam(String team) {
		this.team = team;
	}
	/**
	 * @return the quality
	 */
	public ChanceQuality getQuality() {
		return quality;
	}
	/**
	 * @param quality the quality to set
	 */
	public void setQuality(ChanceQuality quality) {
		this.quality = quality;
	}
	/**
	 * @return the icon
	 */
	public ChanceIcon getIcon() {
		return icon;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(ChanceIcon icon) {
		this.icon = icon;
	}
	/**
	 * @return the type
	 */
	public ChanceType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(ChanceType type) {
		this.type = type;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
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
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * @return the bodyPart
	 */
	public ChanceBodyPart getBodyPart() {
		return bodyPart;
	}
	/**
	 * @param bodyPart the bodyPart to set
	 */
	public void setBodyPart(ChanceBodyPart bodyPart) {
		this.bodyPart = bodyPart;
	}
	/**
	 * @return the outcome
	 */
	public ChanceOutcome getOutcome() {
		return outcome;
	}
	/**
	 * @param outcome the outcome to set
	 */
	public void setOutcome(ChanceOutcome outcome) {
		this.outcome = outcome;
	}
	
	/**
	 * @return the assist
	 */
	public Assist getAssist() {
		return assist;
	}
	/**
	 * @param assist the assist to set
	 */
	public void setAssist(Assist assist) {
		this.assist = assist;
	}
	
	
	/**
	 * @return the secondaryAssist
	 */
	public Assist getSecondaryAssist() {
		return secondaryAssist;
	}
	/**
	 * @param secondaryAssist the secondaryAssist to set
	 */
	public void setSecondaryAssist(Assist secondaryAssist) {
		this.secondaryAssist = secondaryAssist;
	}
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append(player.getNames()[0] + " ");
		output.append(time + " ");
		if(quality != null) {
			output.append(quality.getDescription() + " ");
		}
		if(icon != null) {
			output.append(icon.getDescription() + " ");	
		}
		if(type != null) {
			output.append(type.getDescription() + " ");
		}
		if(bodyPart != null) {
			output.append(bodyPart.getDescription() + " ");	
		}
		if(outcome != null) {
			output.append(outcome.getDescription() + " ");	
		}
		output.append("x:" + x + " ");
		output.append("y:" + y + " ");
		if(assist != null) {
			output.append("assist: " + assist.toString());
		}
		return output.toString();
	}
}
