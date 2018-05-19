package rob.strataparser.exporters;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import rob.strataparser.Player;
import rob.strataparser.chance.Chance;
//pitch length 420
//width from -136 to 136
import rob.strataparser.holders.AssistHolder;

public class ChanceExporter {

	public static void export(ArrayList<Chance> chances, String name) {
		try (
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(name+".csv"));
	
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
	                    .withHeader("name", "num", "outcome", "x", "y", "ax", "ay"));
        ) {
			for (Chance c : chances) {
				int ax = -150;
				int ay = -150;
				if(c.getAssist() != null) {
					ax = c.getAssist().getX();
					ay = c.getAssist().getY();
				}
				
				csvPrinter.printRecord(c.getPlayer().getNames()[0], c.getPlayer().getNumber(), c.getOutcome(), c.getX(), c.getY(), ax, ay);	
			}
	      	
	        csvPrinter.flush();            
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void exportTotals(HashMap<Player, AssistHolder> assists) {
		
		try (
	        BufferedWriter writer = Files.newBufferedWriter(Paths.get("assists.csv"));
		
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
            		.withHeader("name", "attempted", "successful", "attempted-secondary", "successful-secondary","mins"));
		) {
			for(AssistHolder ah : assists.values()) {
				csvPrinter.printRecord(ah.getPlayer(), ah.getAttemptedAssists(), ah.getSuccessfulAssists(), 
						ah.getAttemptedSecondaryAssists(), ah.getSuccessfulSecondaryAssists(), ah.getPlayer().getMinutes());	
			} 	
		    csvPrinter.flush();            
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
