package com.happiestminds.civiccop.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.happiestminds.civiccop.R;
import com.happiestminds.civiccop.firebase.LoginHandler;
import com.happiestminds.civiccop.firebase.RootRef;
import com.happiestminds.civiccop.model.data.User;
import com.happiestminds.civiccop.model.request.LoginData;
import com.happiestminds.civiccop.util.AppUtils;
import com.happiestminds.civiccop.util.Constants;
import com.happiestminds.civiccop.util.UIUtils;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import timber.log.Timber;

/**
 * Copyright 2016 (C) Happiest Minds Pvt Ltd..
 *
 * <P> Login Activity, with options of Socail Login Integration
 *
 * <P>Notes:
 * <P>Dependency:
 *
 * @authors Ravindra Kamble (ravindra.kambale@happiestminds.com)
 *          Sunil Rao S (sunil.sindhe@happiestminds.com)
 *
 * @created on: 4-Jan-2016
 */
public class LoginActivity extends BootstrapActivity implements TextView.OnEditorActionListener{

    EventBus bus;

    @Bind(R.id.et_email)EditText etUserEmail;
    @Bind(R.id.et_password)EditText etPassword;
    @Bind(R.id.btn_signin)Button btnSignin;
    @Bind(R.id.btn_forgotPassword)Button btnForgotPassword;
    @Bind(R.id.btn_create_account)Button btnCreateAccount;
    @BindString(R.string.empty_username)String emptyUsername;
    @BindString(R.string.empty_password)String emptyPassword;
    @BindString(R.string.empty_username_password)String emptyUsernamePassword;
    @BindString(R.string.err_email)String invalidEmail;
    private Firebase.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etPassword.setOnEditorActionListener(this);
        bus = EventBus.getDefault();

        Timber.i("Activity Created ");

        mAuthStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                hideProgressDialog();
                setAuthenticatedUser(authData);
            }
        };
        /* Check if the user is authenticated with Firebase already. If this is the case we can set the authenticated
         * user and hide hide any login buttons */
        RootRef.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @OnClick({R.id.btn_signin, R.id.btn_forgotPassword, R.id.btn_create_account})
    public void handleButtonClick(Button button){
        UIUtils.hideSoftInput(this, etPassword);
        switch (button.getId()){
            case R.id.btn_signin:
                Timber.i("SignIn Button click");
                login();
                break;

            case R.id.btn_forgotPassword:
                Timber.i("Forgot Password");
                showForgotPasswordActivity();
                break;

            case R.id.btn_create_account:
                Timber.i("Registration Screen");
                UIUtils.startActivity(this, RegistrationActivity.class);
                this.finish();
        }
    }

    private void setAuthenticatedUser(AuthData authData) {
        if (authData != null) {
            RootRef.authData = authData;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        AppUtils.registerToDefaultBus(this, false);
    }

    @Override
    protected void onStop() {
        AppUtils.unregisterFromDefaultBus(this);

        super.onStop();
    }

    public void loginWithPassword(LoginData loginData) {
        showProgressDialog();
        RootRef.getInstance().authWithPassword(loginData.getUsername(), loginData.getPassword(), new LoginHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                hideProgressDialog();
                RootRef.authData = authData;
                Query query = RootRef.getUsersRoot().orderByChild("uid").startAt(authData.getUid()).endAt(authData.getUid());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            RootRef.user = ds.getValue(User.class);
                        }
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showMainActivity();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {


                    }
                });
                hideProgressDialog();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                super.onAuthenticationError(firebaseError);
                hideProgressDialog();
                Toast.makeText(LoginActivity.this, "Logging failed",
                        Toast.LENGTH_LONG).show();

            }
        });
    }

    public void login(){
        int fieldCheck = emptyCheck();
        UIUtils.hideSoftInput(this, etPassword);
        Timber.i("Field Check value %d", fieldCheck);
        if(fieldCheck == Constants.FieldCheck.VALID) {
            //showProgressDialog();
            LoginData loginData = new LoginData();
            loginData.setUsername(etUserEmail.getText().toString().trim());
            loginData.setPassword(etPassword.getText().toString().trim());
            loginWithPassword(loginData);
//            apiService.login(loginData, new Callback<ApiResponse>() {
//                @Override
//                public void success(ApiResponse apiResponse, Response response) {
//                    Timber.i("Login Successful");
//                    // showMainActivity();
//                    hideProgressDialog();
//                    bus.post("Login Successful");
//                    //Post the creds as a sticky event so that the user can be automatically logged in later
//                    bus.postSticky(new UserCredsEvent(true, etUserEmail.getText().toString()));
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    Timber.i("Login Failed");
//                    hideProgressDialog();
//                    bus.post("Login Failed");
//                }
//            });
        }else{
            switch (fieldCheck){
                case Constants.FieldCheck.BOTH_FIELDS_EMPTY:
                    etUserEmail.setError(emptyUsernamePassword);
                    etUserEmail.requestFocus();
                    break;

                case Constants.FieldCheck.USERNAME_FIELD_EMPTY:
                    etUserEmail.setError(emptyUsername);
                    etUserEmail.requestFocus();
                    break;

                case Constants.FieldCheck.PASSWORD_FIELD_EMPTY:
                    etPassword.setError(emptyPassword);
                    etPassword.requestFocus();
                    break;

                case Constants.FieldCheck.INVALID_EMAIL:
                    etUserEmail.setError(invalidEmail);
                    etUserEmail.requestFocus();
                    break;
            }
        }
    }

    private void showForgotPasswordActivity(){
        UIUtils.startActivity(this, ForgotPasswordActivity.class);
        this.finish();
    }

    private void showMainActivity(){
        UIUtils.startActivity(this, MainMenuTilesActivity.class);
        this.finish();
    }


    /**
     * Empty check for edit text views.
     *
     * @return true, if either username or password filed empty
     */
    private int emptyCheck() {
        int emptyCredential = Constants.FieldCheck.VALID;
        if (etUserEmail.length() == 0 || etPassword.length() == 0) {
            if (etUserEmail.length() == 0 && etPassword.length() == 0) {
                emptyCredential = Constants.FieldCheck.BOTH_FIELDS_EMPTY;
            } else if (etPassword.length() == 0) {
                emptyCredential = Constants.FieldCheck.PASSWORD_FIELD_EMPTY;
            } else {
                emptyCredential = Constants.FieldCheck.USERNAME_FIELD_EMPTY;
            }
        }

//        if (etUserEmail.length() > 0) {
//            if (!AppUtils.isEmailAddressValid(etUserEmail.getText().toString())) {
//                emptyCredential = Constants.FieldCheck.INVALID_EMAIL;
//            }
//        }

        return emptyCredential;
    }

    public void onEvent(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {

            Timber.i("Done button clicked");
            login();

            return true;
        }
        return false;
    }
}
