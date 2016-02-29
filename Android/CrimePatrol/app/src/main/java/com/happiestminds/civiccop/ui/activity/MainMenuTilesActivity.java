package com.happiestminds.civiccop.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.happiestminds.civiccop.R;
import com.happiestminds.civiccop.ui.adapter.CustomAdapter;

public class MainMenuTilesActivity extends Activity {
    GridView grid;
    ListView listView;
    ImageView img1, img2, img3, img4;
    String[] web = {
            "",
            " ",
            " ",
            " ",
            " ",
            " ",


    };
    int[] imageId = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
            R.drawable.f,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grids);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        grid = (GridView) findViewById(R.id.grid_menu);
        final CustomAdapter adapter = new CustomAdapter(this, web, imageId);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //  Toast.makeText(getActivity(), "You Clicked at " + VideoUrl[position], Toast.LENGTH_SHORT).show();

                if (position == 0) {
                    Intent i = new Intent(MainMenuTilesActivity.this, SubMenuTilesActivity.class);
                    i.putExtra("URL", position);
                    startActivity(i);
                } else if (position == 1) {
                    Intent i = new Intent(MainMenuTilesActivity.this, ComplaintActivity.class);
                    i.putExtra("URL", position);
                    startActivity(i);
                } else if (position == 2) {
                    Intent i = new Intent(MainMenuTilesActivity.this, Reports.class);
                    //i.putExtra("URL", position);
                    startActivity(i);
                } else if (position == 3) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:123456789"));
                    if (ActivityCompat.checkSelfPermission(MainMenuTilesActivity.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(callIntent);
                } else if (position == 4) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:123456789"));
                    startActivity(callIntent);
                } else if (position == 5) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:123456789"));
                    startActivity(callIntent);
                }

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

        return ;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        // or = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        setRequestedOrientation(orientation);

    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainMenuTilesActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}
