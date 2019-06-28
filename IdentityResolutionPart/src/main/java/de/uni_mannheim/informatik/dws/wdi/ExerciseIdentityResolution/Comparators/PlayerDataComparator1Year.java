package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Comparators;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.date.YearSimilarity;
import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;


public class PlayerDataComparator1Year implements Comparator<Player, Attribute> {
	
	private static final long serialVersionUID = 1L;
	private YearSimilarity sim = new YearSimilarity(1);
	private ComparatorLogger comparisonLog;
	
	@Override
	public double compare(
			Player record1,
			Player record2,
			Correspondence<Attribute, Matchable> schemaCorrespondences) {
    	
    	double similarity = sim.calculate(record1.getBirthday(), record2.getBirthday());
    	
		if(this.comparisonLog != null){
			this.comparisonLog.setComparatorName(getClass().getName());
		
			//this.comparisonLog.setRecord1Value(record1.getBirthday().toString());
			//this.comparisonLog.setRecord2Value(record2.getBirthday().toString());
			try { this.comparisonLog.setRecord2Value(record1.getBirthday().toString());} catch (Throwable any) { any.printStackTrace(); }
			try { this.comparisonLog.setRecord2Value(record2.getBirthday().toString());} catch (Throwable any) { any.printStackTrace(); }
			
    	
			this.comparisonLog.setSimilarity(Double.toString(similarity));
		}
		return similarity;

	}
	
	@Override
	public ComparatorLogger getComparisonLog() {
		return this.comparisonLog;
	}

	@Override
	public void setComparisonLog(ComparatorLogger comparatorLog) {
		this.comparisonLog = comparatorLog;
	}

}
