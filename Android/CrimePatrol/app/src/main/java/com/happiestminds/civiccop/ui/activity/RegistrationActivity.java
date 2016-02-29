package com.happiestminds.civiccop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.happiestminds.civiccop.R;
import com.happiestminds.civiccop.firebase.RootRef;
import com.happiestminds.civiccop.model.data.User;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Timer;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity {

    @Bind(R.id.gender)Spinner genderbtn;

    @Bind(R.id.editName)
    EditText fullName;

    @Bind(R.id.editMob)
    EditText primaryNumber;

    @Bind(R.id.editSecMob)
    EditText secondaryNumber;

    @Bind(R.id.editAddress)
    EditText address;

    @Bind(R.id.editEmail)
    EditText email;

    @Bind(R.id.pwd)
    EditText password;

    @Bind(R.id.pwd2)
    EditText password2;

    ArrayList<String> items=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        final Spinner genderbtn = (Spinner) findViewById(R.id.gender);
        Button register = (Button) findViewById(R.id.buttonReg);
        items.add("Male");
        items.add("Female");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderbtn.setAdapter(adapter);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (password.getText().toString().equals(password2.getText().toString())) {
                    final String emailVal = email.getText().toString();
                    final String passwordVal = password.getText().toString();

                    final User user = new User() {{
                        setFullName(fullName.getText().toString());
                        setAddress(address.getText().toString());
                        setGender(genderbtn.getSelectedItem().toString());
                        setPrimaryNumber(Integer.parseInt(primaryNumber.getText().toString()));
                        setSecondaryNumber(Integer.parseInt(secondaryNumber.getText().toString()));
                        setEmail(emailVal);
                    }};

                    RootRef.getInstance().createUser(emailVal, passwordVal, new Firebase.ResultHandler() {

                        @Override
                        public void onSuccess() {
                            RootRef.getInstance().authWithPassword(emailVal, passwordVal, new Firebase.AuthResultHandler() {
                                @Override
                                public void onAuthenticated(AuthData authData) {
                                    user.setUid(authData.getUid());
                                    RootRef.getUsersRoot().push().setValue(user, new Firebase.CompletionListener() {
                                        @Override
                                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {

                                        }
                                    });

                                    Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
                                    RegistrationActivity.this.startActivity(myIntent);
                                    RegistrationActivity.this.finish();
                                }

                                @Override
                                public void onAuthenticationError(FirebaseError firebaseError) {
                                    Log.d("FIREBASE", firebaseError.getMessage());
                                }
                            });
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {

                        }
                    });
                }


            }
        });

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

}
