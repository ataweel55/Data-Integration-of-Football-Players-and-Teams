package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.time.LocalDateTime;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;


public class Player implements Matchable {

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

    protected String id;
    protected String provenance;
    private String fName;
    private String lName;
    private String nationality;
    private  LocalDateTime birthday;
    private  double height;
    private double  weight;
    private double acceleration;
    private double agility;
    private Team team;

    //question, do i need to a team attribute
    //second,, in the movie constructor, why did we choosed those three attributes?
    ///this is the movie constructor for reference

    /**
    public Movie(String identifier, String provenance) {
		id = identifier;
		this.provenance = provenance;
		actors = new LinkedList<>();
     */
    public Player(String identifier, String provenance) {

        id = identifier;
        this.provenance = provenance;
    }

    @Override
    public String getIdentifier() { return id; }

    @Override
    public String getProvenance() {return provenance;}

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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
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

////// this is for the team class need to check
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
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

