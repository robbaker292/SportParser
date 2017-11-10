package rob.sportparser.event.shot;
/**
 * What body part was used for this shot
 * 
 * @author Rob
 *
 */
public enum ShotBodyPart {
	RIGHT("right footed"),
	LEFT("left footed"),
	HEADER("header"),
	UNKNOWN("unknown");
	
	private String description;

	private ShotBodyPart(String description) {
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
