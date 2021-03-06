
package arnav.premierleague.TeamModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links {

    @SerializedName("self")
    @Expose
    private Self self;
    @SerializedName("competition")
    @Expose
    private Competition competition;

    /**
     * 
     * @return
     *     The self
     */
    public Self getSelf() {
        return self;
    }

    /**
     * 
     * @param self
     *     The self
     */
    public void setSelf(Self self) {
        this.self = self;
    }

    /**
     * 
     * @return
     *     The competition
     */
    public Competition getCompetition() {
        return competition;
    }

    /**
     * 
     * @param competition
     *     The competition
     */
    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

}
