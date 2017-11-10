package rob.sportparser.event.shot;

/**
 * An enum to store the location the shot was taken from
 * @author Rob
 *
 */
public enum ShotTakenLocation {

	LONG_LEFT("long range on the left"),
	LONG_RIGHT("long range on the right"),
	OUTSIDE("outside the box"),
	CENTRE("centre of the box"),
	RIGHT("right side of the box"),
	LEFT("left side of the box"),
	RIGHT_SIX("right side of the six yard box"),
	LEFT_SIX("left side of the six yard box"),
	ANGLE_LEFT("difficult angle on the left"),
	ANGLE_RIGHT("difficult angle on the right"),
	VERY_CLOSE("very close range"),
	MORE_THAN_35("more than 35 yards"),
	MORE_THAN_40("more than 40 yards"),
	FREEKICK("from a free kick"),
	PENALTY("the penalty"),
	UNKNOWN("unknown");
	
	private String description;

	private ShotTakenLocation(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
