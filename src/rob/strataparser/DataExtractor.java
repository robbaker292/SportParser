package rob.strataparser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import rob.sportparser.event.Event;
import rob.strataparser.builders.ChanceBuilder;
import rob.strataparser.builders.PlayerBuilder;
import rob.strataparser.chance.*;
import rob.strataparser.exporters.ChanceExporter;
import rob.strataparser.holders.AssistHolder;

public class DataExtractor {
	
	private static ArrayList<Match> matches = new ArrayList<>();
	private static ArrayList<Player> players = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		System.out.println("starting");
		
		PlayerBuilder.buildPlayers(players);
		ChanceBuilder cb = new ChanceBuilder(players);
		cb.parseChances(matches);
		ArrayList<Chance> allChances = new ArrayList<>();
		for(Match m : matches) {
			//sort array
			Collections.sort(m.getChances(), new Comparator<Chance>(){
			    @Override
				public int compare(Chance e1, Chance e2) {
			        return e1.getTime().compareTo(e2.getTime());
			    }
			});
			allChances.addAll(m.getChances());
			//System.out.println();
			//System.out.println(m);
			for(Chance c : m.getChances()) {
				//System.out.println(c);
			}
			
		}
		
		//Match m = Util.findMatch(matches, "2468486");
		//ChanceExporter.export(m.getChances());
		//ChanceExporter.export(allChances);
		
		int num = 944;
		Player p = Util.getPlayer(players, num);
		System.out.println(p);
		System.out.println(allChances.size());
		System.out.println(allChances);
		//HashMap<Player, AssistHolder> results = groupChancesByAssist(allChances, true);
		//ChanceExporter.exportTotals(results);
		ChanceExporter.export(findAssists(allChances, p), "assists-"+num);
		ChanceExporter.export(findChances(allChances, p), "chances-"+num);
	}

	/**
	 * Finds all chances where the relevant player took it
	 * @param chances The list of chances
	 * @param p The player
	 * @return The list of chances assisted by that player
	 */
	private static ArrayList<Chance> findChances(ArrayList<Chance> chances, Player p) {
		ArrayList<Chance> output = new ArrayList<>();
		for(Chance c : chances) {
			if (c.getPlayer() == p) {
				output.add(c);
			}
		}
		return output;
	}

	/**
	 * Finds all chances where the relevant player provided the primary assist
	 * @param chances The list of chances
	 * @param p The player
	 * @return The list of chances assisted by that player
	 */
	private static ArrayList<Chance> findAssists(ArrayList<Chance> chances, Player p) {
		ArrayList<Chance> output = new ArrayList<>();
		for(Chance c : chances) {
			if (c.getAssist() != null && c.getAssist().getPlayer() == p) {
				output.add(c);
			}
		}
		return output;
	}
	
	private static HashMap<Player, AssistHolder> groupChancesByAssist(ArrayList<Chance> chances, boolean includeSecondary) {
		HashMap<Player, AssistHolder> results = new HashMap<>();
		for(Chance c : chances) {
			if(c.getAssist() != null) {
				Player p = c.getAssist().getPlayer();
				AssistHolder ah = results.get(p);
				if (ah == null) {
					//new holder needed
					ah = new AssistHolder(p);
					results.put(p, ah);
				}
				if(c.getOutcome() == ChanceOutcome.GOAL) {
					ah.incrementSuccessfulAssists();
				} else {
					ah.incrementAttemptedAssists();
				}
				
				//if there is a secondary assist and we want them
				if(includeSecondary && c.getSecondaryAssist() != null) {
					p = c.getSecondaryAssist().getPlayer();
					ah = results.get(p);
					if (ah == null) {
						//new holder needed
						ah = new AssistHolder(p);
						results.put(p, ah);
					}
					if(c.getOutcome() == ChanceOutcome.GOAL) {
						ah.incrementSuccessfulSecondaryAssists();
					} else {
						ah.incrementAttemptedSecondaryAssists();
					}
				}
			}
		}
		return results;
	}
}
