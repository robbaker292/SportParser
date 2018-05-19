package rob.strataparser;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import rob.strataparser.chance.Chance;

public class Match {

	private String id, homeTeam, awayTeam;
	private OffsetDateTime kickoff;
	private ArrayList<Chance> chances = new ArrayList<>();
	
	public Match(String id, String homeTeam, String awayTeam, OffsetDateTime kickoff) {
		this.id = id;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.kickoff = kickoff;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the homeTeam
	 */
	public String getHomeTeam() {
		return homeTeam;
	}

	/**
	 * @param homeTeam the homeTeam to set
	 */
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	/**
	 * @return the awayTeam
	 */
	public String getAwayTeam() {
		return awayTeam;
	}

	/**
	 * @param awayTeam the awayTeam to set
	 */
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	/**
	 * @return the kickoff
	 */
	public OffsetDateTime getKickoff() {
		return kickoff;
	}

	/**
	 * @param kickoff the kickoff to set
	 */
	public void setKickoff(OffsetDateTime kickoff) {
		this.kickoff = kickoff;
	}
	
	public boolean equals(Object m) {
		if(m instanceof Match) {
			return ((Match) m).id == this.id;
		} else {
			return false;
		}
	}
	
	public String toString() {
		return homeTeam + ' ' + awayTeam;
	}

	public ArrayList<Chance> getChances() {
		return chances;
	}

	public void setChances(ArrayList<Chance> chances) {
		this.chances = chances;
	}
	
	public void addChance(Chance chance) {
		chances.add(chance);
	}
}
