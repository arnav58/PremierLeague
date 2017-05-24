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
import arnav.premierleague.PlayersModel.Player;
import arnav.premierleague.PlayersModel.PlayersModel;
import arnav.premierleague.R;
import arnav.premierleague.TeamModel.Players;
import arnav.premierleague.Utils.AppController;

public class PlayersActivity extends AppCompatActivity {

    PlayersModel responseObj;
    List<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        Intent i = getIntent();

        String fixtureLink = i.getStringExtra("playerLink");

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, fixtureLink, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("success", "response  " + response.toString());
                        String responsestr = new String(String.valueOf(response));
                        Gson gson = new Gson();
                        responseObj = gson.fromJson(responsestr, PlayersModel.class);

                        players = responseObj.getPlayers();

                        populateViews(players);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("fail", "error" + error.toString());


                    }
                });

        AppController.getInstance(this).addToRequestQueue(jsObjRequest);


    }

    private void populateViews(List<Player> playerList) {

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.playersContainer);
        linearLayout.removeAllViews();

        for (int i = 0; i < playerList.size(); i++) {

            View inflatedLayout = getLayoutInflater().inflate(R.layout.player_card, null);

            TextView homeTeam = (TextView) inflatedLayout.findViewById(R.id.jerserNum);
            TextView awayTeam = (TextView) inflatedLayout.findViewById(R.id.playerName);

            homeTeam.setText(""+playerList.get(i).getJerseyNumber());
            awayTeam.setText(playerList.get(i).getName());

            linearLayout.addView(inflatedLayout);
        }
    }
}
