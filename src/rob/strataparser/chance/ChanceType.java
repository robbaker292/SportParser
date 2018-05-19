package rob.strataparser.chance;

public enum ChanceType {
	OPEN_PLAY("open play"),
	DANGEROUS_MOMENT("dangerous moment"),
	DIRECT_CORNER("corner"),
	DIRECT_FREEKICK("direct freekick"),
	FREEKICK("free kick"),
	PENALTY("penalty"),
	PENALTY_EARNED("pen earned"),
	SHOT_DEFELCTION("shot deflection");
	
	private String description;

	private ChanceType(String description) {
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
