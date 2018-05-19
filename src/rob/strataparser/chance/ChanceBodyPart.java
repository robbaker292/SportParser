package rob.strataparser.chance;

public enum ChanceBodyPart {
	HEAD("head"),
	LEFT("left"),
	RIGHT("right"),
	OTHER("other");
	
	private String description;

	private ChanceBodyPart(String description) {
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
