package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.solution;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.*;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.*;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.*;
import de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEngine;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEvaluator;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionStrategy;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleDataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleHashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class PlayerApp
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
		// Load the Data into FusibleDataSet
		System.out.println("*\n*\tLoading datasets\n*");

		FusibleDataSet<Player, Attribute> ds1 = new FusibleHashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/Playerxlsx.xml"), "/players/player", ds1);
		ds1.printDataSetDensityReport();

		FusibleDataSet<Player, Attribute> ds2 = new FusibleHashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/PlayerComplete.xml"), "/players/player", ds2);
		ds2.printDataSetDensityReport();

		FusibleDataSet<Player, Attribute> ds3 = new FusibleHashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/PlayersDbPedia.xml"), "/players/player", ds3);
		ds3.printDataSetDensityReport();

		FusibleDataSet<Player, Attribute> ds5 = new FusibleHashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/input/newplayerTeamData.xml"), "/players/player", ds5);
		ds5.printDataSetDensityReport();

		// load correspondences
		System.out.println("*\n*\tLoading correspondences\n*");
		CorrespondenceSet<Player, Attribute> correspondences = new CorrespondenceSet<>();
		correspondences.loadCorrespondences(new File("data/correspondences/2_3_nodup.csv"),ds2, ds3);
		correspondences.loadCorrespondences(new File("data/correspondences/2_5_nodup.csv"),ds2, ds5);
		correspondences.loadCorrespondences(new File("data/correspondences/1_5_nodup.csv"),ds1, ds5);

		// setting scores
		ds1.setScore(3.0);
		ds3.setScore(1.0);
		ds2.setScore(2.0);
		ds5.setScore(4.0);

		// Date (e.g. last update)
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
		        .appendPattern("yyyy-MM-dd")
		        .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
		        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
		        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
		        .toFormatter(Locale.ENGLISH);

		ds1.setDate(LocalDateTime.parse("2016-01-01", formatter));
		ds2.setDate(LocalDateTime.parse("2017-01-01", formatter));
		ds3.setDate(LocalDateTime.parse("2018-01-01", formatter));
		ds5.setDate(LocalDateTime.parse("2018-06-01", formatter));


		// write group size distribution
		correspondences.printGroupSizeDistribution();

		// defining the fusion strategy
		DataFusionStrategy<Player, Attribute> strategy = new DataFusionStrategy<>(new FusiblePlayerFactory());
		// writing debug results to file
		strategy.activateDebugReport("data/output/debugResultsPlayerDatafusion.csv", 100);
		
		// adding attribute fusers
		strategy.addAttributeFuser(Player.F_NAME, new FNameFuserLongestString(),new FNameEvaluationRule());
		strategy.addAttributeFuser(Player.L_NAME,new LNameFuserVoting(), new LNameEvaluationRule());
		strategy.addAttributeFuser(Player.NATIONALITY,new NationalityFuserVoting(),new NationalityEvaluationRule());
		strategy.addAttributeFuser(Player.HEIGHT,new HeightFuserFavourSource(),new HeightEvaluationRule());
		strategy.addAttributeFuser(Player.WEIGHT,new WeightFuserMostRecent(),new WeightEvaluationRule());


		// creating the fusion engine
		DataFusionEngine<Player, Attribute> engine = new DataFusionEngine<>(strategy);

		// printing consistency report
		engine.printClusterConsistencyReport(correspondences, null);

		// running the fusion
		System.out.println("*\n*\tRunning data fusion\n*");
		FusibleDataSet<Player, Attribute> fusedDataSet = engine.run(correspondences, null);

		// writing the result
		new PlayerXMLFormatter().writeXML(new File("data/output/fusedPlayers.xml"), fusedDataSet);

		// loading the gold standard
		System.out.println("*\n*\tEvaluating results\n*");
		DataSet<Player, Attribute> gs = new FusibleHashedDataSet<>();
		new PlayerXMLReader().loadFromXML(new File("data/goldstandard/playersGoldStandard.xml"), "/players/player", gs);

		for(Player p : gs.get()) {
			System.out.println(String.format("gs: %s", p.getIdentifier()));
		}
		// evaluating
		DataFusionEvaluator<Player, Attribute> evaluator = new DataFusionEvaluator<>(strategy);
		
		double accuracy = evaluator.evaluate(fusedDataSet, gs, null);

		System.out.println(String.format("Accuracy: %.2f", accuracy));
    }
}
