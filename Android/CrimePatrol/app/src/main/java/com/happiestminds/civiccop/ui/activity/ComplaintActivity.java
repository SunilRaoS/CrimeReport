package com.happiestminds.civiccop.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.happiestminds.civiccop.R;
import com.happiestminds.civiccop.firebase.RootRef;
import com.happiestminds.civiccop.model.data.Category;
import com.happiestminds.civiccop.model.data.Complaint;
import com.happiestminds.civiccop.util.AppUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComplaintActivity extends AppCompatActivity {

    Spinner mainCategory;
    Spinner subCategory;
    private EditText mTitle;
    private EditText mDescription;
    private Button mCreateComplaintsButton;

    Category mCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        int value = 0;
        if (extras != null) {
            value = extras.getInt("POSITION");
        }

        mainCategory = (Spinner) findViewById(R.id.complaintCat);
        subCategory = (Spinner) findViewById(R.id.complaintSubCat);
        mTitle = (EditText) findViewById(R.id.editTitle);
        mDescription = (EditText) findViewById(R.id.editDescription);

        String json = AppUtils.loadJSONFromAsset(this);
        final Category cat = AppUtils.jsonToObject(json);

        mCat = cat;

        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < cat.getItems().size(); i++)
            list.add(cat.getItems().get(i).getCat());

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainCategory.setAdapter(adapter);
        mainCategory.setSelection(value);

        //cat.getItems().get(0).getSubCat();
        mainCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                List<String> list3 = new ArrayList<String>(getSubItem(position));

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                        ComplaintActivity.this,
                        android.R.layout.simple_spinner_item,
                        list3);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subCategory.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mCreateComplaintsButton = (Button) findViewById(R.id.buttonSubmit);
        mCreateComplaintsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Date now = new Date();

                RootRef.getComplaintsRoot().push().setValue(new Complaint() {
                    {
                        setTitle(mTitle.getText().toString());
                        setDescription(mDescription.getText().toString());
                        setType(mainCategory.getSelectedItem().toString());
                        setSubType(subCategory.getSelectedItem().toString());
                        setCreatedBy(RootRef.authData.getUid());
                        setCreateDate(new DateFormat().format("dd/MM/yyyy", now).toString());
                        setCreateDate(new DateFormat().format("HH:mm:ss", now).toString());
                    }
                });

                Toast.makeText(ComplaintActivity.this, "Incident Logged Successfully",
                        Toast.LENGTH_LONG).show();

                new AlertDialog.Builder(ComplaintActivity.this)
                        .setTitle("Report Logged")
                        .setMessage("Incident Saved Successfully")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                            }
                        }).create().show();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()

                               {
                                   @Override
                                   public void onClick(View view) {
                                       Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                               .setAction("Action", null).show();
                                   }
                               }

        );
    }


    List<String> getSubItem(int pos) {
        return mCat.getItems().get(pos).getSubCat();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
