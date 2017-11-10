package rob.sportparser.expectedgoals;

import rob.sportparser.event.shot.ShotBodyPart;
import rob.sportparser.event.shot.ShotDirection;
import rob.sportparser.event.shot.ShotResultLocation;
import rob.sportparser.event.shot.ShotTakenLocation;

/**
 * Handles the combinations used to determine the xG value of a shot.
 * Also used to calculate this value through the goalCount and noGoalCount.
 * 
 * @author Rob
 *
 */
public class ExpectedGoalHolder {
	
	private ShotTakenLocation takenLocation;
	private int noGoalCount = 0;
	private int goalCount = 0;
	private double expectedGoal = 0;

	/**
	 * @param takenLocation
	 */
	public ExpectedGoalHolder(ShotTakenLocation takenLocation) {
		this.takenLocation = takenLocation;
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
	 * @return the noGoalCount
	 */
	public int getNoGoalCount() {
		return noGoalCount;
	}

	/**
	 * @param noGoalCount the noGoalCount to set
	 */
	public void setNoGoalCount(int noGoalCount) {
		this.noGoalCount = noGoalCount;
	}

	/**
	 * @return the goalCount
	 */
	public int getGoalCount() {
		return goalCount;
	}

	/**
	 * @param goalCount the goalCount to set
	 */
	public void setGoalCount(int goalCount) {
		this.goalCount = goalCount;
	}

	/**
	 * @return the expectedGoal
	 */
	public double getExpectedGoal() {
		return expectedGoal;
	}

	/**
	 * @param expectedGoal the expectedGoal to set
	 */
	public void setExpectedGoal(double expectedGoal) {
		this.expectedGoal = expectedGoal;
	}
	
	/**
	 * Increments the no goal count
	 */
	public void incrementNoGoalCount() {
		noGoalCount++;
	}
	
	/**
	 * Increments the  goal count
	 */
	public void incrementGoalCount() {
		goalCount++;
	}
	
	@Override
	public String toString() {
		return takenLocation + "," + goalCount + "," + noGoalCount+","+expectedGoal+",";
	}

}
