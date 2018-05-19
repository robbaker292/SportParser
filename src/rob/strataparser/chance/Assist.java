package rob.strataparser.chance;

import rob.strataparser.Player;

public class Assist {

	private Player player;
	private AssistType type;
	int x, y;
	
	/**
	 * @param player
	 * @param type
	 * @param x
	 * @param y
	 */
	public Assist(Player player, AssistType type, int x, int y) {
		this.player = player;
		this.type = type;
		this.x = x;
		this.y = y;
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
	 * @return the type
	 */
	public AssistType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(AssistType type) {
		this.type = type;
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
	
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append(player.getNames()[0] + " ");
		if(type != null) {
			output.append(type.getDescription() + " ");
		}
		output.append("x:" + x + " ");
		output.append("y:" + y + " ");
		return output.toString();
	}
}
