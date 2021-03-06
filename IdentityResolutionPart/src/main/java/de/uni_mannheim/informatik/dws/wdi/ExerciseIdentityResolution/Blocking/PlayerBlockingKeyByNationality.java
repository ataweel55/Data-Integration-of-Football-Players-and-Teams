package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.Blocking;

import de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model.Player;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

public class PlayerBlockingKeyByNationality extends
RecordBlockingKeyGenerator<Player, Attribute> {

	private static final long serialVersionUID = 1L;

	public void generateBlockingKeys(Player record, Processable<Correspondence<Attribute, Matchable>> correspondences,
			DataIterator<Pair<String, Player>> resultCollector) {
		String[] tokens  = record.getNationality().split(" ");

		String blockingKeyValue = "";

		for(int i = 0; i < tokens.length; i++) {
			blockingKeyValue += tokens[i].toUpperCase();
		}

		resultCollector.next(new Pair<>(blockingKeyValue, record));
	}



}
