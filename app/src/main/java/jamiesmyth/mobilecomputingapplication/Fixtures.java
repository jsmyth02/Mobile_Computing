package jamiesmyth.mobilecomputingapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Fixtures implements Serializable {
    @SerializedName("awayTeamName")
    @Expose
    private String awayTeamName;
    @SerializedName("homeTeamName")
    @Expose
    private String homeTeamName;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("result")
    @Expose
    private Scores scores;

    public String getAwayTeamName ()
    {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName)
    {
        this.awayTeamName = awayTeamName;
    }


    public String getHomeTeamName()
    {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName)
    {
        this.homeTeamName = homeTeamName;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public Scores getScores()
    {
        return scores;
    }

    public void setScores(Scores scores)
    {
        this.scores = scores;
    }


}
