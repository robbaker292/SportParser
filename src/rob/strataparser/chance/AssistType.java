package rob.strataparser.chance;

public enum AssistType {

	CORNER("corner"),
	CORNER_WON("corner won"),
	CROSS_HIGH("cross high"),
	CROSS_LOW("cross low"),
	FREEKICK("freekick"),
	FREEKICK_WON("freekick won"),
	DANGEROUS_MOMENT("danger moment"),
	OPEN_PLAY_PASS("pass"),
	PENALTY_EARNED("pen earned"),
	SHOT_DEFLECTION("shot deflection"),
	SHOT_OPPOSITION_REBOUND("shot oppo rebound"),
	SHOT_WOODWORK_REBOUND("shot woodwork rebound"),
	THROW_IN("throw in"),
	TURNOVER("turnover");
	
	private String description;

	private AssistType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return getDescription();
	}
}
