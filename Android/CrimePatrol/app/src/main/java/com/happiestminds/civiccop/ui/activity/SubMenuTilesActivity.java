package com.happiestminds.civiccop.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.happiestminds.civiccop.R;
import com.happiestminds.civiccop.ui.adapter.CustomAdapter;


public class SubMenuTilesActivity extends Activity {
    GridView grid;
    ListView listView;
    ImageView img1, img2, img3, img4;
    String[] web = {
            "",
            " ",
            " ",
            " ",


    };
    int[] imageId = {
            R.drawable.s_a,
            R.drawable.s_b,
            R.drawable.s_c,
            R.drawable.s_d


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.grids_sub_menu);

        grid = (GridView) findViewById(R.id.grid_sub_menu);
        final CustomAdapter adapter = new CustomAdapter(this, web, imageId);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(SubMenuTilesActivity.this, ComplaintActivity.class);
                i.putExtra("POSITION", position);
                startActivity(i);

            }
        });

       /* setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        img1 =(ImageView) findViewById(R.id.img_dr);
        img2 =(ImageView) findViewById(R.id.img_report);
        img3 =(ImageView) findViewById(R.id.img_guide);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenuTilesActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenuTilesActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenuTilesActivity.this, GuideActivity.class);
                startActivity(i);
            }
        });*/

        return;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        // or = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        setRequestedOrientation(orientation);

    }
}
