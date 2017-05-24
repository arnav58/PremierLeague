package arnav.premierleague.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import arnav.premierleague.R;

public class HomeActivity extends AppCompatActivity {

    RelativeLayout fixtureLayout;
    RelativeLayout resultsLayout;
    RelativeLayout playersLayout;

    String fixturesLink;
    String playersLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent i = getIntent();
        fixturesLink = i.getStringExtra("fixtureLink");
        playersLink = i.getStringExtra("playerLink");


        fixtureLayout = (RelativeLayout) findViewById(R.id.rl_fixtures);
        resultsLayout = (RelativeLayout) findViewById(R.id.rl_results);
        playersLayout = (RelativeLayout) findViewById(R.id.rl_players);

        fixtureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,FixturesActivity.class);
                intent.putExtra("fixtureLink",fixturesLink);
                startActivity(intent);
            }
        });

        resultsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ResultsActivity.class);
                intent.putExtra("fixtureLink",fixturesLink);
                startActivity(intent);
            }
        });

        playersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PlayersActivity.class);
                intent.putExtra("playerLink",playersLink);
                startActivity(intent);
            }
        });

    }
}
