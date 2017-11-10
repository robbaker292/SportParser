package rob.sportparser.event.card;
/**
 * What card was given
 * 
 * @author Rob
 *
 */
public enum CardType {
	YELLOW("yellow"),
	SECOND_YELLOW("2nd yellow"),
	RED("red");
	
	private String description;

	private CardType(String description) {
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
