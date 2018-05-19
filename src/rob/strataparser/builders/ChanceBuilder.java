package rob.strataparser.builders;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import rob.strataparser.Match;
import rob.strataparser.Player;
import rob.strataparser.Util;
import rob.strataparser.chance.Assist;
import rob.strataparser.chance.AssistType;
import rob.strataparser.chance.Chance;
import rob.strataparser.chance.ChanceBodyPart;
import rob.strataparser.chance.ChanceIcon;
import rob.strataparser.chance.ChanceOutcome;
import rob.strataparser.chance.ChanceQuality;
import rob.strataparser.chance.ChanceType;

public class ChanceBuilder {
	private ArrayList<Player> players;

	/**
	 * @param matches
	 */
	public ChanceBuilder(ArrayList<Player> players) {
		this.players = players;
	}
	
	public void parseChances(ArrayList<Match> matches) throws IOException {
		
		File csvData = new File("chances_from_2017-07-01.csv");
		CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.RFC4180);
		for (CSVRecord csvRecord : parser) {
			if(csvRecord.getRecordNumber() == 1) {
				continue;
			}
			
			//skip non Gills games
			if(!csvRecord.get(9).equals("Gillingham")) {
				continue;
			}
			
			String matchId = csvRecord.get(2);
			DateTimeFormatter fmt = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
			OffsetDateTime date = OffsetDateTime.parse(csvRecord.get(3) + 'T' + csvRecord.get(4) + 'Z', fmt);
			
			Match m = Util.findMatch(matches, matchId);
			if (m == null) {
				m = new Match(matchId, csvRecord.get(5), csvRecord.get(6), date);
				matches.add(m);
			}			
			
			ChanceIcon icon = parseChanceIcon(csvRecord.get(7));
			ChanceQuality quality = parseChanceQuality(csvRecord.get(8));
			ChanceType type = parseChanceType(csvRecord.get(10));
			String time = csvRecord.get(11);
			Player player = Util.findPlayer(players, csvRecord.get(12), false);
			if (player == null) {
				continue;
			}
			
			int x = 0;
			try {
				x = (int) Math.round(Double.parseDouble(csvRecord.get(13)));
			} catch (Exception e) {
				x = Integer.MIN_VALUE;
			}
			int y = 0;
			try {
				y = (int) Math.round(Double.parseDouble(csvRecord.get(14)));
			} catch (Exception e) {
				y = Integer.MIN_VALUE;
			}
			ChanceBodyPart bodyPart = parseChanceBodyPart(csvRecord.get(15));
			ChanceOutcome outcome = parseChanceOutcome(csvRecord.get(20));
			
			if (outcome == null) {
				if(icon == ChanceIcon.GOAL) {
					outcome = ChanceOutcome.GOAL;
				} else if (icon == ChanceIcon.OWN_GOAL) {
					outcome = ChanceOutcome.OWN_GOAL;
				}
			}
			
			Assist a = parseAssist(csvRecord.get(21), csvRecord.get(22), csvRecord.get(23), csvRecord.get(24));
			Assist a2 = parseAssist(csvRecord.get(25), csvRecord.get(26), Integer.MAX_VALUE+"", Integer.MAX_VALUE+"");
			
			Chance c = new Chance(csvRecord.get(9), quality, icon, type, time, player, x, y, bodyPart, outcome, a, a2);
			m.addChance(c);
			
		    //System.out.println(csvRecord);
		}
		
	}
	
	private Assist parseAssist(String playerString, String typeString, String xStr, String yStr) {
		if(playerString.equals("-") || typeString.equals("-")) {
			return null;
		}
		//System.out.println(playerString + " " + typeString + " " + xStr + " " + yStr);
		try {
			Player p = Util.findPlayer(players, playerString, false);
			AssistType type = parseAssistType(typeString);
			int x = (int) Math.round(Double.parseDouble(xStr));
			int y = (int) Math.round(Double.parseDouble(yStr));
			Assist a = new Assist(p, type, x, y);
			//System.out.println(a);
			return a;
		} catch (NumberFormatException e) {
			return null;
		}		
	}
	
	private AssistType parseAssistType(String input) {
		switch (input.toLowerCase()) {
			case "corner" :
				return AssistType.CORNER;
			case "corner won" :
				return AssistType.CORNER_WON;
			case "cross high" :
				return AssistType.CROSS_HIGH;
			case "cross low" :
				return AssistType.CROSS_LOW;
			case "dangerous moment" :
				return AssistType.DANGEROUS_MOMENT;
			case "freekick" :
				return AssistType.FREEKICK;
			case "freekick won" :
				return AssistType.FREEKICK_WON;
			case "open play pass" :
				return AssistType.OPEN_PLAY_PASS;
			case "penalty earned" :
				return AssistType.PENALTY_EARNED;
			case "shot (deflection)" :
				return AssistType.SHOT_DEFLECTION;
			case "shot (opposition rebound)" :
				return AssistType.SHOT_OPPOSITION_REBOUND;
			case "shot (woodwork rebound)" :
				return AssistType.SHOT_WOODWORK_REBOUND;
			case "throw in" :
				return AssistType.THROW_IN;
			case "turnover" :
				return AssistType.TURNOVER;
			default :
				return null;
		}
	}
	
	private ChanceIcon parseChanceIcon(String input) {
		switch(input) {
			case "goal" :
				return ChanceIcon.GOAL;
			case "owngoal" :
				return ChanceIcon.OWN_GOAL;	
			case "penawarded" :
				return ChanceIcon.PENALTY_AWARDED;	
			case "penmissed" :
				return ChanceIcon.PENALTY_MISSED;	
			default :
				return null;
		}		
	}
	
	private ChanceQuality parseChanceQuality(String input) {
		switch(input) {
			case "Fairly Good":
			case "fairlygoodchance":
				return ChanceQuality.FAIRLY_GOOD;
			case "Good":
			case "goodchance":
				return ChanceQuality.GOOD;
			case "Great":
			case "greatchance":
				return ChanceQuality.GREAT;
			case "Poor":
			case "poorchance":
				return ChanceQuality.POOR;
			case "Superb":
			case "superbchance":
				return ChanceQuality.SUPERB;
			case "Very Good":
			case "verygoodchance":
				return ChanceQuality.VERY_GOOD;
			case "Penalty":
				return ChanceQuality.PENALTY;
			default : return null;
		}
	}
	
	private ChanceType parseChanceType(String input) {
		switch(input) {
			case "Dangerous Moment" :
				return ChanceType.DANGEROUS_MOMENT;
			case "Direct Corner" :
				return ChanceType.DIRECT_CORNER;	
			case "Direct Free Kick" :
				return ChanceType.DIRECT_FREEKICK;	
			case "Free Kick" :
				return ChanceType.FREEKICK;
			case "Open Play" :
				return ChanceType.OPEN_PLAY;
			case "Penalty" :
				return ChanceType.PENALTY;
			case "Penalty Earned" :
				return ChanceType.PENALTY_EARNED;
			case "Shot (Deflection)" :
				return ChanceType.SHOT_DEFELCTION;
			default :
				return null;
		}		
	}
	
	private ChanceBodyPart parseChanceBodyPart(String input) {
		switch(input) {
			case "Head" :
				return ChanceBodyPart.HEAD;
			case "Left" :
				return ChanceBodyPart.LEFT;	
			case "Right" :
				return ChanceBodyPart.RIGHT;	
			case "Other" :
			default:
				return ChanceBodyPart.OTHER;	
		}		
	}
	
	private ChanceOutcome parseChanceOutcome(String input) {
		switch(input) {
			case "Defended" :
				return ChanceOutcome.DEFENDED;
			case "Miss" :
			case "Missed" :
				return ChanceOutcome.MISS;	
			case "Save" :
			case "Saved" :
				return ChanceOutcome.SAVE;		
			case "Penalty Awarded" :
				return ChanceOutcome.PENALTY_AWARDED;
			case "Woodwork" :
				return ChanceOutcome.WOODWORK;
			default:
				return null;	
		}		
	}
}
