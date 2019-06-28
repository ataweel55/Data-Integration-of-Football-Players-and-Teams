package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import java.util.Locale;
import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import org.w3c.dom.NodeList;


public class PlayerXMLReader extends XMLMatchableReader<Player, Attribute> {

    protected void initialiseDataset(DataSet<Player, Attribute> dataset) {
        super.initialiseDataset(dataset);

        dataset.addAttribute(Player.F_NAME);
        dataset.addAttribute(Player.L_NAME);
        dataset.addAttribute(Player.NATIONALITY);
        dataset.addAttribute(Player.HEIGHT);
        dataset.addAttribute(Player.WEIGHT);
    }


    public Player createModelFromElement(Node node, String provenanceInfo) {

        String id = getValueFromChildElement(node, "Player_id");

        Player player = new Player(id, provenanceInfo);

        //filling attributes
        player.setfName(getValueFromChildElement(node, "First_Name"));
        player.setlName(getValueFromChildElement(node, "Last_Name"));
        player.setNationality(getValueFromChildElement(node, "Nationality"));
        player.setHeight(getValueFromChildElement(node, "Height"));
        player.setWeight(getValueFromChildElement(node, "Weight"));
        
        player.setTeam(readTeamData(node, id, provenanceInfo));

        return player;
    }

    private Team readTeamData(Node node, String id, String provenanceInfo) {
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if ("Team".equals(childNode.getNodeName())) {
                Team team = new Team(id, provenanceInfo);
                team.setClubName(getValueFromChildElement(childNode, "Club_name"));
                team.setLeague(getValueFromChildElement(childNode, "League"));
                return team;
            }
        }
        return null;
    }
}