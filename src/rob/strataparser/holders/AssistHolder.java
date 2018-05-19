package rob.strataparser.holders;

import rob.strataparser.Player;

public class AssistHolder {

	private Player player;
	private int successfulAssists, attemptedAssists;
	private int successfulSecondaryAssists, attemptedSecondaryAssists;
	
	/**
	 * @param player player
	 * @param successfulAssists
	 * @param attemptedAssists
	 */
	public AssistHolder(Player player) {
		this.player = player;
		this.successfulAssists = 0;
		this.attemptedAssists = 0;
		this.setSuccessfulSecondaryAssists(0);
		this.setAttemptedSecondaryAssists(0);
	}

	/**
	 * @return the p
	 */
	public Player getPlayer() {
		return player;
	}
	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player playerp) {
		this.player = player;
	}
	/**
	 * @return the successfulAssists
	 */
	public int getSuccessfulAssists() {
		return successfulAssists;
	}
	/**
	 * @param successfulAssists the successfulAssists to set
	 */
	public void setSuccessfulAssists(int successfulAssists) {
		this.successfulAssists = successfulAssists;
	}
	/**
	 * @return the attemptedAssists
	 */
	public int getAttemptedAssists() {
		return attemptedAssists;
	}
	/**
	 * @param attemptedAssists the attemptedAssists to set
	 */
	public void setAttemptedAssists(int attemptedAssists) {
		this.attemptedAssists = attemptedAssists;
	}
	
	public void incrementAttemptedAssists() {
		attemptedAssists++;
	}

	public void incrementSuccessfulAssists() {
		successfulAssists++;
	}

	/**
	 * @return the successfulSecondaryAssists
	 */
	public int getSuccessfulSecondaryAssists() {
		return successfulSecondaryAssists;
	}

	/**
	 * @param successfulSecondaryAssists the successfulSecondaryAssists to set
	 */
	public void setSuccessfulSecondaryAssists(int successfulSecondaryAssists) {
		this.successfulSecondaryAssists = successfulSecondaryAssists;
	}

	/**
	 * @return the attemptedSecondaryAssists
	 */
	public int getAttemptedSecondaryAssists() {
		return attemptedSecondaryAssists;
	}

	/**
	 * @param attemptedSecondaryAssists the attemptedSecondaryAssists to set
	 */
	public void setAttemptedSecondaryAssists(int attemptedSecondaryAssists) {
		this.attemptedSecondaryAssists = attemptedSecondaryAssists;
	}
	
	public void incrementAttemptedSecondaryAssists() {
		attemptedSecondaryAssists++;
	}

	public void incrementSuccessfulSecondaryAssists() {
		successfulSecondaryAssists++;
	}
}
