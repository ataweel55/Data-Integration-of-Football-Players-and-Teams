package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

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
    }


    public Player createModelFromElement(Node node, String provenanceInfo) {

        String id = getValueFromChildElement(node, "Player_id");
        // create the object with id and provenance information

        Player player = new Player(id, provenanceInfo);

        //fill attributes
        player.setfName(getValueFromChildElement(node, "First_Name"));
        player.setlName(getValueFromChildElement(node, "Last_Name"));
        player.setNationality(getValueFromChildElement(node, "Nationality"));

        //converting string to double

//        try { player.setHeight(Double.parseDouble(getValueFromChildElement(node, "Height")));} catch (Throwable any) { any.printStackTrace(); }
//        try { player.setWeight(Double.parseDouble(getValueFromChildElement(node, "Weight")));} catch (Throwable any) { any.printStackTrace(); }
//        try { player.setAgility(Double.parseDouble(getValueFromChildElement(node, "Agility")));} catch (Throwable any) { any.printStackTrace(); }
//        try { player.setAcceleration(Double.parseDouble(getValueFromChildElement(node, "Acceleartion")));} catch (Throwable any) { any.printStackTrace(); }
        // load the list of actors


        player.setTeam(readTeamData(node, id, provenanceInfo));

        // convert the date string into a DateTime object
        try {
            String date = getValueFromChildElement(node, "Birthday");
            if (date != null && !date.isEmpty()) {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern("yyyy-MM-dd")
                        .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
                        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                        .toFormatter(Locale.ENGLISH);
                LocalDateTime dt = LocalDateTime.parse(date, formatter);
                player.setBirthday(dt);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

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