package arnav.premierleague.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import arnav.premierleague.R;
import arnav.premierleague.TeamModel.Links_;
import arnav.premierleague.TeamModel.Team;
import arnav.premierleague.TeamModel.TeamsModel;
import arnav.premierleague.Utils.AppController;

public class TeamsActivity extends AppCompatActivity {


    TeamsModel responseObj;
    List<Team> teamList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, "http://api.football-data.org/v1/competitions/398/teams", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("success", "response  " + response.toString());
                        String responsestr = new String(String.valueOf(response));
                        Gson gson = new Gson();
                        responseObj = gson.fromJson(responsestr, TeamsModel.class);

                        teamList = responseObj.getTeams();
                        populateViews(teamList);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("fail", "error" + error.toString());


                    }
                });

        AppController.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    private void populateViews(List<Team> teamList){
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.teamsContainer);
        linearLayout.removeAllViews();

        for (int i=0;i<teamList.size();i++){

            View inflatedLayout = getLayoutInflater().inflate(R.layout.team_card,null);

            TextView teamName = (TextView) inflatedLayout.findViewById(R.id.teamName);
            ImageView teamImage = (ImageView) inflatedLayout.findViewById(R.id.teamImage);

            teamName.setText(teamList.get(i).getName());

            linearLayout.addView(inflatedLayout);

            final Team teamSending = teamList.get(i);
            inflatedLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToTeamActivity(teamSending);
                }
            });
        }
    }

    private void goToTeamActivity(Team team){
        Links_ links_ = team.getLinks();
        String fixtureLink = links_.getFixtures().getHref();
        String playersLink = links_.getPlayers().getHref();

        Intent intent = new Intent(TeamsActivity.this, HomeActivity.class);
        intent.putExtra("fixtureLink",fixtureLink);
        intent.putExtra("playerLink",playersLink);
        startActivity(intent);
    }

}
