package jamiesmyth.mobilecomputingapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Scores {
    @SerializedName("goalsHomeTeam")
    @Expose
    private String goalsHomeTeam;
    @SerializedName("goalsAwayTeam")
    @Expose
    private String goalsAwayTeam;

    public String getGoalsHomeTeam()
    {
        return goalsHomeTeam;
    }

    public void setGoalsHomeTeam(String goalsHomeTeam)
    {
        this.goalsHomeTeam = goalsHomeTeam;
    }

    public String getGoalsAwayTeam()
    {
        return goalsAwayTeam;
    }

    public void setGoalsAwayTeam(String goalsAwayTeam)
    {
        this.goalsAwayTeam = goalsAwayTeam;
    }

}
