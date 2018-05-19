package rob.strataparser.chance;

public enum ChanceOutcome {
	DEFENDED("defended"),
	MISS("miss"),
	SAVE("save"),
	PENALTY_AWARDED("pen awarded"),
	WOODWORK("woodwork"),
	GOAL("goal"),
	OWN_GOAL("own_goal");
	
	private String description;

	private ChanceOutcome(String description) {
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
