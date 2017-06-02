package jamiesmyth.mobilecomputingapplication;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GSONModel implements Serializable {

    @SerializedName("fixtures")
    @Expose
    private List<Fixtures> fixtures;


    public List<Fixtures> getFixtures() {
        return fixtures;
    }

    public void setFixtures(List<Fixtures> fixtures) {
        this.fixtures = fixtures;
    }


}


