package com.example.connertolley.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by connertolley on 6/30/17.
 */

public class BaseActivity extends AppCompatActivity {

    int group1Id = 1;
    int homeId = Menu.FIRST;
    int profileId = Menu.FIRST +1;
    int searchId = Menu.FIRST +2;
    int dealsId = Menu.FIRST +3;
    int helpId = Menu.FIRST +4;
    int contactusId = Menu.FIRST +5;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(group1Id, homeId, homeId, "Cooling Tips");
        menu.add(group1Id, helpId, helpId, "Help");
        menu.add(group1Id, contactusId, contactusId, "Contact Developer");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Intent intent = new Intent(this, CoolingTipsActivity.class);
                startActivity(intent);
                return true;
            case 2:
                return true;
            case 3:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
