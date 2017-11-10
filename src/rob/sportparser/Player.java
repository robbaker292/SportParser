package rob.sportparser;

public class Player {

	private String[] names;
	private int number;

	/**
	 * @param names A list of names this player may have
	 * @param number
	 */
	public Player(String[] names, int number) {
		this.names = names;
		this.number = number;
	}
	
	/**
	 * @return the names
	 */
	public String[] getNames() {
		return names;
	}
	
	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	
	@Override
	public String toString() {
			return names[0];
	}
}
