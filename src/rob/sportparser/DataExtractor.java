package rob.sportparser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import rob.sportparser.event.Corner;
import rob.sportparser.event.Event;
import rob.sportparser.event.Foul;
import rob.sportparser.event.card.Card;
import rob.sportparser.event.card.CardType;
import rob.sportparser.event.shot.Shot;
import rob.sportparser.event.shot.ShotBodyPart;
import rob.sportparser.event.shot.ShotDirection;
import rob.sportparser.event.shot.ShotResultLocation;
import rob.sportparser.event.shot.ShotTakenLocation;

public class DataExtractor {
	
	private ArrayList<Event> events;
	private ArrayList<Player> players;
	private Match match;
	
	/**
	 * Constructor
	 * @param id The id of the match to be obtained
	 * @throws IOException
	 */
	public DataExtractor(String id, ArrayList<Event> events, ArrayList<Player> players, Match match) throws IOException {
		this.players = players;
		this.events = events;
		this.match = match;
		
		//saveCommentary(id);
		//saveTeams(id, match.isHome());
		
		//TODO
		loadCommentary(id);
		loadTeams(id);
		
		//TODO load match details, home away etc
		//add these to match
		//find out squad of the gills side
		//add this to match
		//all in another method, obvs
		
		//sort array
		Collections.sort(events, new Comparator<Event>(){
		    @Override
			public int compare(Event e1, Event e2) {
		        if(e1.getTime() == e2.getTime()) {
		        	return e1.getInjuryTime() - e2.getInjuryTime();
		        } else {
		        	return e1.getTime() - e2.getTime();
		        }
		    }
		});
	}
	
	/**
	 * Saves a commentary to disk
	 * @param id The ID of the match to save
	 */
	public static void saveCommentary(String id) {
		String commentaryUrl = "https://www.sportinglife.com/football/live/" + id + "/commentary";
        try {
        	Document doc = Jsoup.connect(commentaryUrl)
        			.userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
        			.get();
    		BufferedWriter writer = null;
            writer = new BufferedWriter(new FileWriter("d://commentary/" + id + ".html"));
            writer.write(doc.toString());
            writer.close();
        }
        catch (IOException e) {
        }
	}
	
	/**
	 * Saves a result page to disk
	 * @param id The ID of the match to save
	 */
	public static void saveResultsPage(String id, String prefix) {
		String commentaryUrl = "https://www.sportinglife.com/football/results/competitions/sky-bet-championship/10/2017-" + id;
        try {
        	Document doc = Jsoup.connect(commentaryUrl)
        			.userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
        			.get();
    		BufferedWriter writer = null;
            writer = new BufferedWriter(new FileWriter("d://commentary/results-" + prefix + id + ".html"));
            writer.write(doc.toString());
            writer.close();
        }
        catch (IOException e) {
        }
	}
	
	/**
	 * Load a reslt page from disk
	 * @param id The ID of the match to save
	 * @throws IOException 
	 */
	public static ArrayList<String> loadResultsPage(String id, String prefix) throws IOException {
		ArrayList<String> ids = new ArrayList<>();
		File in = new File("d://commentary/results-" +  prefix + id + ".html");
		Document doc = Jsoup.parse(in, null);
		
		//System.out.println(doc.title());
		
		Elements items = doc.getElementsByClass("topRow"); //the row for each result
		for(Element row : items) {
			Element link = row.child(1);
			String url = link.attr("href").substring(15,20);
			ids.add(url);
			//System.out.println(url);
		}
		return ids;
	}
	
	/**
	 * Saves a team data to disk
	 * @param id The ID of the match to save
	 * @param home If Gills are the home side
	 */
	private void saveTeams(String id, boolean home) {
		String commentaryUrl = "https://www.sportinglife.com/football/live/" + id + "/teams/";
		if(home) {
			commentaryUrl += "home";
		} else {
			commentaryUrl += "away";
		}
        try {
        	Document doc = Jsoup.connect(commentaryUrl)
        			.userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
        			.get();
    		BufferedWriter writer = null;
            writer = new BufferedWriter(new FileWriter("d://commentary/" + id + "-teams.html"));
            writer.write(doc.toString());
            writer.close();
        }
        catch (IOException e) {
        }
	}
	
	/**
	 * Load the commentary data
	 * @param id The ID of the match to be obtained
	 * @throws IOException
	 */
	private void loadCommentary(String id) throws IOException {
		File in = new File("d://commentary/" + id + ".html");
		Document doc = Jsoup.parse(in, null);
		
		//System.out.println(doc.title());
		
		Elements items = doc.getElementsByClass("event");
		for(Element e : items) {
			//System.out.println(e.text());
			String time = e.child(0).text();
			String text = e.child(2).text();
			
			if(text.contains("Gillingham") && time.length()!=0) {
			//if(time.length()!=0) { //TODO for xG
				//only care about the Gills
				parseCommentaryItem(time, text);
			}
		}		
	}
	
	/**
	 * Parses an individual commentary item and converts into a event
	 * @param timeText The text for the time
	 * @param text The text for the event
	 */
	private void parseCommentaryItem(String timeText, String text) {
		//System.out.print(timeText);
		//System.out.println(text);
		int time = parseTime(timeText);
		int injuryTime = parseInjuryTime(timeText);
		//System.out.print(time+"+"+injuryTime);
		Player player = findPlayer(text, false); //TODO false
		
		if(text.contains("Corner")) {
			//corner
			events.add(new Corner(player, time, injuryTime));
		} else if(text.contains("Attempt") || text.contains("Goal!") || text.contains("Penalty missed!")) {
			//shot
			
			ShotTakenLocation stl = parseShotTakenLocation(text);
			ShotDirection sd = null;
			ShotResultLocation srl = null;
			if(text.contains("blocked")) {
				sd = ShotDirection.BLOCKED;
				srl = ShotResultLocation.BLOCKED;
			} else if(text.contains("missed")) {
				sd = ShotDirection.OFF_TARGET;
				srl = parseOffTargetShot(text);
			} else if(text.contains("saved")){
				sd = ShotDirection.ON_TARGET;
				srl = parseOnTargetShot(text, false);
			}
			
			ShotBodyPart sbp = null;
			if(text.contains("right footed")) {
				sbp = ShotBodyPart.RIGHT;
			} else if(text.contains("left footed")) {
				sbp = ShotBodyPart.LEFT;
			} else if(text.contains("header")) {
				sbp = ShotBodyPart.HEADER;
			} else {
				sbp = ShotBodyPart.UNKNOWN;
			}
			
			boolean goal = false;
			Player assist = null;
			if(text.contains("Goal!")) {
				//srl = parseOnTargetShot(text, true); //TODO splits the result up by goal or no goal
				
				srl = parseOnTargetShot(text, false);
				goal = true;
				String assistString = text.substring(text.indexOf("Assisted") + 3 , text.length());
				assist = findPlayer(assistString, false); //TODO false
				if(sd == null) {
					sd = ShotDirection.ON_TARGET; //we assume the goal was from an on target shot, unless otherwise stated
				}
			}
			
			if(player != null) {
				events.add(new Shot(match, player, time, injuryTime, sbp, sd, goal, assist, srl, stl));
			}			
		} else if(text.contains("Foul")) {
			//foul
			events.add(new Foul(player, time, injuryTime));			
		} else if (text.contains("card")) {
			
			//cards
			CardType cardType = null;
			if(text.contains("second yellow")) {
				cardType = CardType.SECOND_YELLOW;
			} else if(text.contains("yellow card")) {
				cardType = CardType.YELLOW;
			} else if(text.contains("red card")) {
				cardType = CardType.RED;
			}			
			events.add(new Card(player, time, injuryTime, cardType));			
		}
		//TODO and others
	}
	
	/**
	 * Parses the normal time section of the time Text
	 * @param time the time text to be parsed
	 * @return The new time
	 */
	private int parseTime(String time) {
		time = time.substring(0,time.length()-1);		
		int plusIndex = time.indexOf("+");
		if (plusIndex != -1)		{
			time = time.substring(0, plusIndex);
		}		
		//System.out.println(time);
		return Integer.parseInt(time);
		//return 0;
	}
	
	/**
	 * Parses the injury time section of the time Text
	 * @param time the time text to be parsed
	 * @return The new injury time
	 */
	private int parseInjuryTime(String time) {
		time = time.substring(0,time.length()-1);		
		int plusIndex = time.indexOf("+");
		if (plusIndex != -1)		{
			return Integer.parseInt(time.split("\\+")[1]); //remove everything before the +
		}		
		return 0;
	}
	
	/**
	 * Parses the text for where a shot was taken
	 * @param text The text to parse
	 * @return The enum value for this shot
	 */
	private ShotTakenLocation parseShotTakenLocation(String text) {
		ShotTakenLocation stl = null;
		if(text.contains("long range on the left")) {
			stl = ShotTakenLocation.LONG_LEFT;
			
		} else if(text.contains("long range on the right")) {
			stl = ShotTakenLocation.LONG_RIGHT;
			
		} else if(text.contains("outside the box")) {
			stl = ShotTakenLocation.OUTSIDE;
			
		} else if(text.contains("centre of the box")) {
			stl = ShotTakenLocation.CENTRE;
			
		} else if(text.contains("right side of the box")) {
			stl = ShotTakenLocation.RIGHT;
			
		} else if(text.contains("left side of the box")) {
			stl = ShotTakenLocation.LEFT;
			
		} else if(text.contains("right side of the six yard box")) {
			stl = ShotTakenLocation.RIGHT_SIX;
			
		} else if(text.contains("left side of the six yard box")) {
			stl = ShotTakenLocation.LEFT_SIX;
			
		} else if(text.contains("difficult angle on the left")) {
			stl = ShotTakenLocation.ANGLE_LEFT;
			
		} else if(text.contains("difficult angle on the right")) {
			stl = ShotTakenLocation.ANGLE_RIGHT;
		
		} else if(text.contains("very close range")) {
			stl = ShotTakenLocation.VERY_CLOSE;
			
		} else if(text.contains("more than 35 yards")) {
			stl = ShotTakenLocation.MORE_THAN_35;
			
		} else if(text.contains("more than 40 yards")) {
			stl = ShotTakenLocation.MORE_THAN_40;
			
		//} else if(text.contains("from a free kick")) {
		//	stl = ShotTakenLocation.FREEKICK;
			
		} else if(text.contains("converts the penalty") || text.contains("Penalty missed")) {
			stl = ShotTakenLocation.PENALTY;
			
		} else {
			stl = ShotTakenLocation.UNKNOWN;
		}		
		return stl;
	}
	
	/**
	 * Parses the text for an off target shot
	 * @param text The text to parse
	 * @return The enum value for this shot
	 */
	private ShotResultLocation parseOffTargetShot(String text) {
		ShotResultLocation srl = null;
		if(text.contains("close, but misses to the left")) {
			srl = ShotResultLocation.CLOSE_LEFT;
			
		} else if(text.contains("close, but misses to the right")) {
			srl = ShotResultLocation.CLOSE_RIGHT;
			
		} else if(text.contains("misses to the left")) {
			srl = ShotResultLocation.LEFT;
			
		} else if(text.contains("misses to the right")) {
			srl = ShotResultLocation.RIGHT;
			
		} else if(text.contains("just a bit too high")) {
			srl = ShotResultLocation.CLOSE_HIGH;
			
		} else if(text.contains("too high")) {
			srl = ShotResultLocation.HIGH;
			
		} else if(text.contains("high and wide to the left")) {
			srl = ShotResultLocation.HIGH_WIDE_LEFT;
			
		} else if(text.contains("high and wide to the right")) {
			srl = ShotResultLocation.HIGH_WIDE_RIGHT;
			
		} else if(text.contains("close, but misses the top right corner")) {
			srl = ShotResultLocation.CLOSE_TOP_RIGHT;
			
		} else if(text.contains("close, but misses the top left corner")) {
			srl = ShotResultLocation.CLOSE_TOP_LEFT;
			
		} else if(text.contains("hits the left post")) {
			srl = ShotResultLocation.LEFT_POST;
			
		} else if(text.contains("hits the right post")) {
			srl = ShotResultLocation.RIGHT_POST;			
			
		} else if(text.contains("hits the bar")) {
			srl = ShotResultLocation.CROSSBAR;

		} else {
			srl = ShotResultLocation.UNKNOWN;
		}		
		return srl;
	}
	
	/**
	 * Parses the text for an on target shot
	 * @param text The text to parse
	 * @param goal If the shot is a goal
	 * @return The enum value for this shot
	 */
	private ShotResultLocation parseOnTargetShot(String text, boolean goal) {
		ShotResultLocation srl = null;
		if(text.contains("top right corner")) {
			if(goal) {
				srl = ShotResultLocation.GOAL_TOP_RIGHT;
			} else {
				srl = ShotResultLocation.TOP_RIGHT;
			}			
		} else if(text.contains("bottom right corner")) {
			if(goal) {
				srl = ShotResultLocation.GOAL_BOTTOM_RIGHT;
			} else {
				srl = ShotResultLocation.BOTTOM_RIGHT;
			}	
		} else if(text.contains("top left corner")) {
			if(goal) {
				srl = ShotResultLocation.GOAL_TOP_LEFT;
			} else {
				srl = ShotResultLocation.TOP_LEFT;
			}	
		} else if(text.contains("bottom left corner")) {
			if(goal) {
				srl = ShotResultLocation.GOAL_BOTTOM_LEFT;
			} else {
				srl = ShotResultLocation.BOTTOM_LEFT;
			}	
		} else if(text.contains("centre of the goal")) {
			if(goal) {
				srl = ShotResultLocation.GOAL_CENTRE;
			} else {
				srl = ShotResultLocation.CENTRE;
			}	
		} else {
			srl = ShotResultLocation.UNKNOWN;
		}		
		return srl;
	}
	
	/**
	 * Determines what player is in the piece of text
	 * @param text The text to search
	 * @param player If it doesn't matter about what player it is
	 * @return
	 */
	private Player findPlayer(String text, boolean dummy) {
		if(dummy) {
			return players.get(0);
		}
		//only search the text before Gillingham (if it appears) - this to get the correct goalscorer
		int plusIndex = text.indexOf("(Gillingham)");
		if (plusIndex != -1) {
			text = text.substring(0, plusIndex);
		}
		
		for(Player player : players) {
			for(String name : player.getNames()) {
				if(text.contains(name)) {
					return player;
				}
			}
		}
		return null;
	}
	
	/**
	 * Load the team data
	 * @param id The ID of the match to be obtained
	 * @throws IOException
	 */
	private void loadTeams(String id) throws IOException {
		File in = new File("d://commentary/" + id + "-teams.html");
		Document doc = Jsoup.parse(in, null);
		//footballTeamPlayerListItem
		System.out.println(doc.title());
		
		Elements items = doc.getElementsByClass("footballTeamPlayerListItem");
		parsePlayers(items, true);
		items = doc.getElementsByClass("playerListItem");
		parsePlayers(items, false);
	}
	
	/**
	 * Parses a player list as starting XI and bench are in different players on the site
	 * @param playerList
	 */
	private void parsePlayers(Elements playerList, boolean starting) {
		for(Element e : playerList) {
			int minutes = -1;
			
			String name = e.getElementsByClass("playerName").first().text();
			
			Element subEl = e.getElementsByClass("subIcon").first();
			if(subEl != null) {
				//player has been subbed
				String timeText = subEl.getElementsByClass("subTime").first().text();
				int time = parseTime(timeText);
				if(starting) {
					minutes = time;
				} else {
					minutes = 90 - time;
				}
				
			} else {
				//if not, they either played the full 90, or didn't feature at all
				if(starting) {
					minutes = 90;
				} else {
					minutes = 0;
				}
			}
			
			Player player = findPlayer(name, false); //TODO false
			LineupData ld = new LineupData(match, player, starting, minutes);
			match.addLinuep(ld);
		}
	}
}
