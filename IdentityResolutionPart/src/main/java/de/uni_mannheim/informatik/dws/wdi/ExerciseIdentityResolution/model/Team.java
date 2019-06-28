package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.io.Serializable;


import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Team extends AbstractRecord<Attribute> implements Serializable {


    /**
     * <Team>
     * <Club_name>Majestic FC</Club_name>
     * <League>Burkina_Faso</League>
     * </Team>
     */

    private static final long serialVersionUID = 1L;
    private String clubName;
    private String league;

    public Team(String identifier, String provenance) {
        super(identifier, provenance);
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    @Override
    public int hashCode() {
        int result = 31 + ((clubName == null) ? 0 : clubName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Team other = (Team) obj;
        if (clubName == null) {
            if (other.clubName != null)
                return false;
        } else if (!clubName.equals(other.clubName))
            return false;
        return true;
    }

    public final Attribute CLUBNAME = new Attribute("Clubname");
    public static final Attribute LEAGUE = new Attribute("League");


    @Override
    public boolean hasValue(Attribute attribute) {
        if(attribute==CLUBNAME)
            return clubName!=null;
        else if(attribute==LEAGUE)
            return league!=null;
        return false;
    }

}