package rob.sportparser.event.shot;

/**
 * An enum to store the location the shot ends up
 * @author Rob
 *
 */
public enum ShotResultLocation {
	CLOSE_LEFT("close left"),
	CLOSE_RIGHT("close right"),
	LEFT("left"),
	RIGHT("right"),
	CLOSE_HIGH("close high"),
	HIGH("high"),
	HIGH_WIDE_LEFT("high and wide left"),
	HIGH_WIDE_RIGHT("high and wide right"),
	CLOSE_TOP_LEFT("close top left"),
	CLOSE_TOP_RIGHT("close top right"),
	
	TOP_LEFT("top left corner"),
	TOP_RIGHT("top right corner"),
	BOTTOM_LEFT("bottom left corner"),
	BOTTOM_RIGHT("bottom right corner"),
	CENTRE("centre of goal"),
	
	GOAL_TOP_LEFT("goal top left corner"),
	GOAL_TOP_RIGHT("goal top right corner"),
	GOAL_BOTTOM_LEFT("goal bottom left corner"),
	GOAL_BOTTOM_RIGHT("goal bottom right corner"),
	GOAL_CENTRE("goal centre of goal"),
	
	LEFT_POST("left post"),
	RIGHT_POST("right post"),
	CROSSBAR("hits the bar"),
	
	BLOCKED("blocked"),
	UNKNOWN("unknown");
	
	private String description;

	private ShotResultLocation(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	//@Override
	//public String toString() {
	//	return getDescription();
	//}
}
