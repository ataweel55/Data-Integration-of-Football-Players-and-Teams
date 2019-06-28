/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * {@link XMLFormatter} for {@link Player}s.
 *
 */
public class PlayerXMLFormatter extends XMLFormatter<Player> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("players");
	}

	@Override
	public Element createElementFromRecord(Player record, Document doc) {
		Element player = doc.createElement("player");

		player.appendChild(createTextElement("id", record.getIdentifier(), doc));


		player.appendChild(createTextElementWithProvenance("fName",
				record.getfName(),
				record.getMergedAttributeProvenance(Player.F_NAME), doc));
		player.appendChild(createTextElementWithProvenance("lName",
				record.getlName(),
				record.getMergedAttributeProvenance(Player.L_NAME), doc));
		player.appendChild(createTextElementWithProvenance("nationality",
				record.getNationality(),
				record.getMergedAttributeProvenance(Player.NATIONALITY), doc));
		player.appendChild(createTextElementWithProvenance("height",
				record.getHeight(),
				record.getMergedAttributeProvenance(Player.HEIGHT), doc));
		player.appendChild(createTextElementWithProvenance("weight",
				record.getWeight(),
				record.getMergedAttributeProvenance(Player.WEIGHT), doc));

		return player;
	}

	protected Element createTextElementWithProvenance(String name,
			String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}


}
