package rob.sportparser.event.shot;
/**
 * What direction was the shot in
 * 
 * @author Rob
 *
 */
public enum ShotDirection {
	ON_TARGET("on target"),
	OFF_TARGET("off target"),
	BLOCKED("blocked");
	
	private String description;

	private ShotDirection(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	/*
	@Override
	public String toString() {
		return getDescription();
	}*/
}
