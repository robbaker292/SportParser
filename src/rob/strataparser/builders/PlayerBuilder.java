package rob.strataparser.builders;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import rob.strataparser.Player;
import rob.strataparser.Util;

public class PlayerBuilder {
	
	/**
	 * Build a list of players to parse from
	 * @throws IOException 
	 */
	public static void buildPlayers(ArrayList<Player> players) throws IOException{
		players.add(new Player(new String[]{"Stuart Nelson", "S. Nelson"},1,1));
		players.add(new Player(new String[]{"Luke O'Neill", "L. O'Neill"},2,2));
		players.add(new Player(new String[]{"Bradley Garmston", "B. Garmston"},3,3));
		players.add(new Player(new String[]{"Alex Lacey", "A. Lacey"},4,4));
		players.add(new Player(new String[]{"Max Ehmer", "M. Ehmer"},5,5));
		players.add(new Player(new String[]{"Gabriel Zakuani", "G. Zakuani"},6,6));
		players.add(new Player(new String[]{"Scott Wagstaff", "S. Wagstaff"},7,7));
		players.add(new Player(new String[]{"Jake Hessenthaler", "J. Hessenthaler"},8,8));
		players.add(new Player(new String[]{"Thomas Eaves","Tom Eaves", "T. Eaves"},9,9));
		players.add(new Player(new String[]{"Conor Wilkinson", "C. Wilkinson"},10,10));
		players.add(new Player(new String[]{"Lee Martin", "L. Martin"},11,11));
		players.add(new Player(new String[]{"Connor Ogilvie", "C. Ogilvie"},12,12));
		players.add(new Player(new String[]{"Tomas Holy", "T. Holy"},13,13));
		players.add(new Player(new String[]{"Josh Parker", "J. Parker"},14,14));
		players.add(new Player(new String[]{"Aaron Morris", "A. Morris"},15,15));
		players.add(new Player(new String[]{"Billy Bingham", "B. Bingham"},16,16));
		players.add(new Player(new String[]{"Noel Mbo", "N. Mbo"},17,17));
		players.add(new Player(new String[]{"Bradley Stevenson", "B. Stevenson"},18,18));
		players.add(new Player(new String[]{"Ben Nugent", "B. Nugent"},19,19));
		players.add(new Player(new String[]{"Darren Oldaker", "D. Oldaker"},20,20));
		players.add(new Player(new String[]{"Elliott List", "E. List"},21,21));
		players.add(new Player(new String[]{"Ben Chapman", "B. Chapman"},22,22));
		players.add(new Player(new String[]{"Aaron Simpson", "A. Simpson"},23,23));
		players.add(new Player(new String[]{"Gregory Cundle", "G. Cundle"},24,24));
		players.add(new Player(new String[]{"Finn O'Mara", "F. O'Mara"},25,25));
		players.add(new Player(new String[]{"Steve Arnold", "S. Arnold"},26,926));
		players.add(new Player(new String[]{"Liam Nash", "L. Nash"},27,27));
		players.add(new Player(new String[]{"Sean Clare", "S. Clare"},28,28));
		players.add(new Player(new String[]{"Jesse Starkey", "J. Starkey"},29,29));
		players.add(new Player(new String[]{"Tom Hadler", "T. Hadler"},30,30));
		players.add(new Player(new String[]{"Jack Tucker", "J. Tucker"},31,31));
		players.add(new Player(new String[]{"Mark Byrne", "M. Byrne"},33,33));
		players.add(new Player(new String[]{"Josh Wright", "J. Wright"},44,44));
		players.add(new Player(new String[]{"Callum Reilly", "C. Reilly"},26,26));
		players.add(new Player(new String[]{"Navid Nasseri", "N. Nasseri"},37,37));
		players.add(new Player(new String[]{"Tom Murphy", "T. Murphy"},39,39));
		players.add(new Player(new String[]{"Franc Moussa", "F. Moussa"},44,944));
		
		extractMinutes(players);
	}
	
	private static void extractMinutes(ArrayList<Player> players) throws IOException {
		File csvData = new File("minsplayed_from_2017-07-01.csv");
		CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.RFC4180);
		for (CSVRecord csvRecord : parser) {
			if(csvRecord.getRecordNumber() == 1) {
				continue;
			}
			
			//skip non Gills games
			if(!csvRecord.get(15).equals("Gillingham")) {
				continue;
			}
			Player p = Util.findPlayer(players, csvRecord.get(8), false);
			int mins;
			try {
				mins = (int) Math.round(Double.parseDouble(csvRecord.get(18)));
				p.addMinutes(mins);
			} catch (Exception e) {
				mins = Integer.MIN_VALUE;
			}
		}
	}
}
