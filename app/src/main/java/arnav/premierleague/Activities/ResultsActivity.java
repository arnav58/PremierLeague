package arnav.premierleague.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import arnav.premierleague.FixtureModel.Fixture;
import arnav.premierleague.FixtureModel.FixtureModel;
import arnav.premierleague.R;
import arnav.premierleague.Utils.AppController;

public class ResultsActivity extends AppCompatActivity {

    FixtureModel responseObj;
    List<Fixture> fixtures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

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
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.resultsContainer);
        linearLayout.removeAllViews();
        int count = 0;

        for (int i=0;i<fixtureList.size();i++){

            Log.d("populateViews", ""+fixtureList.get(i).getMatchday());
            Log.d("populateViews", fixtureList.get(i).getStatus());

            if("FINISHED".equalsIgnoreCase(fixtureList.get(i).getStatus())){
                count++;
            }
        }

        for (int x=count-1;x>=0;x--){
            Log.d("populateViews","added: "+fixtureList.get(x).getHomeTeamName() +" vs "+fixtureList.get(x).getAwayTeamName());

            View inflatedLayout = getLayoutInflater().inflate(R.layout.results_card, null);

            TextView homeTeam = (TextView) inflatedLayout.findViewById(R.id.first_team_name);
            TextView awayTeam = (TextView) inflatedLayout.findViewById(R.id.second_team_name);
            TextView results = (TextView) inflatedLayout.findViewById(R.id.results);

            homeTeam.setText(fixtureList.get(x).getHomeTeamName());
            awayTeam.setText(fixtureList.get(x).getAwayTeamName());
            String home = ""+fixtureList.get(x).getResult().getGoalsHomeTeam();
            home = ""+home.charAt(0);
            String away = ""+fixtureList.get(x).getResult().getGoalsAwayTeam();
            away = ""+away.charAt(0);
            results.setText(home+"-"+away);

            linearLayout.addView(inflatedLayout);

        }
    }

}
