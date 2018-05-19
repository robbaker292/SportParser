package rob.strataparser;

public class Player {

	private String[] names;
	private int number;
	private int id;
	private int minutes;

	/**
	 * @param names A list of names this player may have
	 * @param number squad number
	 * @param id Unique id
	 */
	public Player(String[] names, int number, int id) {
		this.names = names;
		this.number = number;
		this.id = id;
		this.minutes = 0;
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
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the minutes
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * @param minutes the minutes to set
	 */
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	/**
	 * @param addition the amount to add to minutes
	 */
	public void addMinutes(int addition) {
		this.minutes += addition;
	}

	@Override
	public String toString() {
			return names[0];
	}
}
