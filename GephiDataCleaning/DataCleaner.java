import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataCleaner {

	public ArrayList<String> readtoList(String csvFile) {

        ArrayList<String> allNames = new ArrayList<String>();

		try {
			File file = new File(csvFile);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line = "";

			String[] tempArr;
			while ((line = br.readLine()) != null) {
				tempArr = line.split(",");
				for (String tempStr : tempArr) {
					allNames.add(tempStr);
				}
				System.out.println();
			}
			br.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

    return allNames;
	}

    public ArrayList<String> getNamePairs(String csvFile) {
        ArrayList<String> namePairs = new ArrayList<String>(); 

        try {
			File file = new File(csvFile);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line = "";

			String[] tempArr;
			while ((line = br.readLine()) != null) {
				tempArr = line.split(",");
				String name1Status = tempArr[0];
				String[] tempname1split = name1Status.split(":");
				String name1 = tempname1split[0];
				String name2Status = tempArr[1];
				String[] tempname2split = name2Status.split(":");
				String name2 = tempname2split[0];
				String namePair = name1 + "&" + name2;
				namePairs.add(namePair);
			}
			br.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

    return namePairs;
	}

    public static void main(String[] args) {
		// csv file to read
		String csvFile = "C:/Users/Owner/Downloads/Master Spreadsheet- Networks - RAW-TOTAL (For Code) (1).csv";
        DataCleaner trial = new DataCleaner();
		ArrayList<String> resultofAllNames = trial.readtoList(csvFile);
        NodesandEdges builderofIDs = new NodesandEdges();
        Map<String, String> idDict = builderofIDs.buildIDs(resultofAllNames);
		Map<String, String> statusDict = builderofIDs.statusDictBuilder(resultofAllNames);
		
		//Prints names, ID tags, and status(author, publisher, printer, bookseller) delimited with comma (no ugly dictionary)
		builderofIDs.printIDs(idDict, statusDict);
		System.out.println();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println();
		

        NodesandEdges tryRelationships = new NodesandEdges();
        DataCleaner namePairsTester = new DataCleaner();
        ArrayList<String> namePairs = namePairsTester.getNamePairs(csvFile);
        Map<String, List<String>> relationshipDict = tryRelationships.buildRelationships(namePairs);

        NodesandEdges getSourceTargetWeight = new NodesandEdges();
		//Add something to mark each list as being Source, Target, and Weight(based off of collaboration frequency)
        getSourceTargetWeight.printSourceTargetWeight(relationshipDict, idDict);
        
        
	}
}