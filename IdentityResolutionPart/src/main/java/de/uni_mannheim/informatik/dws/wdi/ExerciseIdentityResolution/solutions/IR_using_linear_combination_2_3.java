package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.solutions;

import java.io.File;

import org.apache.logging.log4j.Logger;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking.PlayerBlockingKeyByTitleGenerator;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators.PlayerTeamComparatorLevenstein;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.PlayerXMLReader;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;

public class IR_using_linear_combination_2_3
{
	/*
	 * Logging Options:
	 * 		default: 	level INFO	- console
	 * 		trace:		level TRACE     - console
	 * 		infoFile:	level INFO	- console/file
	 * 		traceFile:	level TRACE	- console/file
	 *  
	 * To set the log level to trace and write the log to winter.log and console, 
	 * activate the "traceFile" logger as follows:
	 *     private static final Logger logger = WinterLogManager.activateLogger("traceFile");
	 *
	 */

	private static final Logger logger = WinterLogManager.activateLogger("trace");
	
    public static void main( String[] args ) throws Exception
    {
		// loading data

		// since this part is required for the parts lower in this class, which we have not yet defined for our case.
		System.out.println("*\n*\tLoading datasets\n*");
		
		// load the datasets
		HashedDataSet<Player, Attribute> PlayerComplete = new HashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/PlayerComplete.xml"), "/players/player", PlayerComplete);
		
		HashedDataSet<Player, Attribute> PlayersDbPedia = new HashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/PlayersDbPedia.xml"), "/players/player", PlayersDbPedia);

		// create a blocker (blocking strategy)
		StandardRecordBlocker<Player, Attribute> blocker = new StandardRecordBlocker<Player, Attribute>(new PlayerBlockingKeyByTitleGenerator());
		//NoBlocker<Player, Attribute> blocker = new NoBlocker<>();
		//SortedNeighbourhoodBlocker<Player, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new PlayerBlockingKeyByTitleGenerator(), 1);
		blocker.setMeasureBlockSizes(true);
		
		//Write debug results to file:
		blocker.collectBlockSizeData("data/output/debugResultsBlocking2_3.csv", 100);
		
		
		// loading the gold standards (training)
		MatchingGoldStandard gsTraining = new MatchingGoldStandard();
		gsTraining.loadFromCSVFile(new File("data/goldstandard/gold_standard_player_training.csv"));
		
		//Create a matching rule
		LinearCombinationMatchingRule<Player, Attribute> matchingRule = new LinearCombinationMatchingRule<>(0.2);


		//Add Comparators to matchingrule
				//matchingRule.addComparator(new PlayerTeamComparatorLevenstein(), 0.2);
				//matchingRule.addComparator(new PlayerFNameComparatorJaccard(), 0.2);
				//matchingRule.addComparator(new PlayerLNameComparatorJaccard(), 0.6);
				//matchingRule.addComparator(new PlayerFNameComparatorFirstLetter(), 0.3);
				matchingRule.addComparator(new PlayerTeamComparatorLevenstein(), 0.2);
				
				// Initialise Matching Engine
		MatchingEngine<Player, Attribute> engine = new MatchingEngine<>();
		
		//running engine for 2 & 3
		System.out.println("*\n*\tRunning identity resolution 2_3\n*");
		Processable<Correspondence<Player, Attribute>> correspondences = engine.runIdentityResolution(
				PlayerComplete, PlayersDbPedia,  null, matchingRule,
				blocker);
		
		// write the correspondences to the output file
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/Player_Corr2_3.csv"), correspondences);

		
		//Evaluate the results
		//load gold standards
		System.out.println("*\n*\tLoading gold standard\n*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File(
			"data/goldstandard/gold_standard_player_testing.csv"));
		
		System.out.println("*\n*\tEvaluating result\n*");
//		// evaluate your result
		MatchingEvaluator<Player, Attribute> evaluator = new MatchingEvaluator<Player, Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences,
				gsTest);
		
		logger.info("Player2 <-> Player3");
		logger.info(String.format("Precision: %.4f", perfTest.getPrecision()));
		logger.info(String.format("Recall: %.4f", perfTest.getRecall()));
		logger.info(String.format("F1: %.4f", perfTest.getF1()));
		
		//activate the debug report
		matchingRule.activateDebugReport("data/output/debugResultsMatchingRule2_3.csv", -1, gsTraining);

		

    }
}
