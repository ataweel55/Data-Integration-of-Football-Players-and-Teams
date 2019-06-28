package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import org.apache.commons.lang3.StringUtils;


public class Player extends AbstractRecord<Attribute> implements Matchable {

    /**
    <player>
		<Player_id>PlayersDbPedia_1006</Player_id>
		<First_Name>Abdoul</First_Name>
		<Last_Name>Kader Kobré</Last_Name>
        <Birthday>1991-01-01</Birthday>
        <Height>170</Height>
        <Weight>163</Weight>
        <Nationality> bla bla</Nationality>
		<Team>
			<Club_name>Majestic FC</Club_name>
			<League>Burkina_Faso</League>
		</Team>
	</player>
     */

    private static final long serialVersionUID = 1L;

    public Player(String identifier, String provenance) {
        super(identifier, provenance);
    }
//    protected String id;
//    protected String provenance;
    private String fName;
    private String lName;
    private String nationality;
    private LocalDateTime birthday;
    private String height;
   	private String  weight;
    private double acceleration;
    private double agility;
    private Team team;


    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public double getAgility() {
        return agility;
    }

    public void setAgility(double agility) {
        this.agility = agility;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }



    private Map<Attribute, Collection<String>> provenance = new HashMap<>();
    private Collection<String> recordProvenance;

    public void setRecordProvenance(Collection<String> provenance) {
        recordProvenance = provenance;
    }

    public Collection<String> getRecordProvenance() {
        return recordProvenance;
    }

    public void setAttributeProvenance(Attribute attribute,
                                       Collection<String> provenance) {
        this.provenance.put(attribute, provenance);
    }

    public Collection<String> getAttributeProvenance(String attribute) {
        return provenance.get(attribute);
    }

    public String getMergedAttributeProvenance(Attribute attribute) {
        Collection<String> prov = provenance.get(attribute);

        if (prov != null) {
            return StringUtils.join(prov, "+");
        } else {
            return "";
        }
    }

    public static final Attribute F_NAME = new Attribute("fName");
    public static final Attribute L_NAME = new Attribute("lName");
    public static final Attribute NATIONALITY = new Attribute("nationality");
    public static final Attribute HEIGHT = new Attribute("height");
    public static final Attribute WEIGHT = new Attribute("weight");
    @Override
    public boolean hasValue(Attribute attribute) {
        if(attribute== F_NAME)
            return getfName() != null && !getfName().isEmpty();
        else if(attribute== L_NAME)
            return getlName() != null && !getlName().isEmpty();
        else if(attribute== NATIONALITY)
            return getNationality() != null && !getNationality().isEmpty();
        else if(attribute== HEIGHT)
            return getHeight() != null && !getHeight().isEmpty();
        else if(attribute== WEIGHT)
            return getWeight() != null && !getWeight().isEmpty();
        else
            return false;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", provenance='" + provenance + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", nationality='" + nationality + '\'' +
                ", birthday=" + birthday +
                ", height=" + height +
                ", weight=" + weight +
                ", acceleration=" + acceleration +
                ", agility=" + agility +
                ", team=" + team +
                '}';
    }

    @Override
    public int hashCode() {
        return getIdentifier().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Player){
            return this.getIdentifier().equals(((Player) obj).getIdentifier());
        }else
            return false;
    }
}

