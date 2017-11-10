package rob.sportparser.builders;

import java.io.IOException;
import java.util.ArrayList;

import rob.sportparser.DataExtractor;
import rob.sportparser.Match;

public class MatchBuilder {

	public static void buildMatches(ArrayList<Match> matches) {
		
		matches.add(new Match("34237", false));
		matches.add(new Match("34248", true));
		matches.add(new Match("34262", false));
		matches.add(new Match("34273", true));
		matches.add(new Match("34282", true));
		matches.add(new Match("34334", false));
		matches.add(new Match("34331", false));
		matches.add(new Match("34319", true));
		matches.add(new Match("34340", false));
		matches.add(new Match("34351", true));
		matches.add(new Match("34353", false));
		matches.add(new Match("34366", true)); 
		matches.add(new Match("34376", false));
		matches.add(new Match("34412", true));
		matches.add(new Match("34414", true));
		matches.add(new Match("34418", false));
	}
	
	/**
	 * Builds from all league matches
	 * @throws IOException
	 */
	public static void buildAllLeagueMatches(ArrayList<Match> matches) throws IOException {
		System.out.println("Loading result pages");
		for(int i = 8; i < 11; i++) {
			String id = i+"";
			if(i < 10) {
				id = "0"+id;
			}
						
			//DataExtractor.saveResultsPage(id, "LC-"); //TODO only when there's a new month
			
			ArrayList<String> ids = DataExtractor.loadResultsPage(id, "LC-");
			ids.addAll(DataExtractor.loadResultsPage(id, "L2-"));
			ids.addAll(DataExtractor.loadResultsPage(id, ""));
			
			for(String matchId : ids) {
				//DataExtractor.saveCommentary(matchId); //TODO only when there's new results
				matches.add(new Match(matchId, true));
			}
		}
		System.out.println("Loading result pages complete");
	}
}
