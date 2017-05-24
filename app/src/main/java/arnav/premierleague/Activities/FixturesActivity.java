package arnav.premierleague.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

import arnav.premierleague.FixtureModel.Fixture;
import arnav.premierleague.FixtureModel.FixtureModel;
import arnav.premierleague.R;
import arnav.premierleague.TeamModel.Team;
import arnav.premierleague.Utils.AppController;

public class FixturesActivity extends AppCompatActivity {

    FixtureModel responseObj;
    List<Fixture> fixtures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);

        Intent i = getIntent();

        String fixtureLink = i.getStringExtra("fixtureLink");

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, fixtureLink, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("success", "response  " + response.toString());
                        String responsestr = new String(String.valueOf(response));
                        Gson gson = new Gson();
                        responseObj = gson.fromJson(responsestr, FixtureModel.class);

                        fixtures = responseObj.getFixtures();
                        populateViews(fixtures);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("fail", "error" + error.toString());


                    }
                });

        AppController.getInstance(this).addToRequestQueue(jsObjRequest);

    }

    private void populateViews(List<Fixture> fixtureList){
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.fixtureContainer);
        linearLayout.removeAllViews();

        for (int i=0;i<fixtureList.size();i++){

            Log.d("populateViews", ""+fixtureList.get(i).getMatchday());
            Log.d("populateViews", fixtureList.get(i).getStatus());

            if (!"FINISHED".equalsIgnoreCase(fixtureList.get(i).getStatus())) {



               Log.d("populateViews","added: "+fixtureList.get(i).getHomeTeamName() +" vs "+fixtureList.get(i).getAwayTeamName());

                View inflatedLayout = getLayoutInflater().inflate(R.layout.fixtures_card, null);

                TextView homeTeam = (TextView) inflatedLayout.findViewById(R.id.first_team_name);
                TextView awayTeam = (TextView) inflatedLayout.findViewById(R.id.second_team_name);


                homeTeam.setText(fixtureList.get(i).getHomeTeamName());
                awayTeam.setText(fixtureList.get(i).getAwayTeamName());

                linearLayout.addView(inflatedLayout);

            }
        }
    }
}
