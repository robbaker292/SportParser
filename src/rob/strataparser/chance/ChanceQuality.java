package rob.strataparser.chance;

public enum ChanceQuality {
	FAIRLY_GOOD("fairly good"),
	GOOD("good"),
	VERY_GOOD("very good"),
	GREAT("great"),
	SUPERB("superb"),
	POOR("poor"),
	PENALTY("penalty");
	
	private String description;

	private ChanceQuality(String description) {
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
