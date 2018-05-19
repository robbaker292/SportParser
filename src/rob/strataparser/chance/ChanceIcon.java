package rob.strataparser.chance;

public enum ChanceIcon {
	GOAL("goal"),
	OWN_GOAL("own goal"),
	PENALTY_AWARDED("pen awarded"),
	PENALTY_MISSED("pen missed");
	
	private String description;

	private ChanceIcon(String description) {
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
