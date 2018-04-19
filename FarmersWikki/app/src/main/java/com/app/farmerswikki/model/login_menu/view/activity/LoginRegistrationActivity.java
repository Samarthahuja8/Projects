package com.app.farmerswikki.model.login_menu.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.farmerswikki.R;
import com.app.farmerswikki.model.dashboard.view.activity.DashBoardActivity;
import com.app.farmerswikki.util.data.AppCommonObject;
import com.app.farmerswikki.util.data.PrefrenceFile;
import com.app.farmerswikki.util.data.UtilityOfActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ORBITWS19 on 02-Jun-2017.
 */

public class LoginRegistrationActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener
        , GoogleApiClient.ConnectionCallbacks {

    private TextView move_to_registration;
    private LinearLayout layout_registration, layout_login;
    private Button login_btn, regis_btn;
    private FirebaseAuth mAuth;
    private String TAG = "TAG";
    private EditText editText_Username_login, editText_password_login, email_id_signup, editText_password_signup;
    private EditText edit_text_fname, edit_text_sec_name;
    private TextView login_here, forgot_password;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 1;
    private SignInButton signInButton;
    private ConnectionResult mConnectionResult;
    private UtilityOfActivity utilityOfActivity;
    private FirebaseUser user, firebaseUser;
    private LinearLayout linearLayout;
    private Typeface typeface;
    private LinearLayout include_google_signIn, include_or_layout;
    private Spinner title_sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  if(UtilityOfActivity.getDeviceHeightAndWidthInInch(LoginRegistrationActivity.this)>6 ||UtilityOfActivity.getDeviceHeightAndWidthInInch(LoginRegistrationActivity.this)<5){
            Toast.makeText(getApplicationContext(),"Device Compatibility Issue",Toast.LENGTH_LONG).show();
            UtilityOfActivity.manageHandler(LoginRegistrationActivity.this,0);
        } */
        setContentView(R.layout.login);
        setView();
        setTitileSpinner();
        utilityOfActivity = new UtilityOfActivity(LoginRegistrationActivity.this);
        configureGoogleSignIn();
        Intent intent = getIntent();

        if (intent.getStringExtra("calledBy") != null) {
            if (intent.getStringExtra("calledBy").equalsIgnoreCase("LOGOUT")) {
                //signOut by Application
                if (PrefrenceFile.getInstance().getString("login_through").equalsIgnoreCase("FireBase")) {
                    mAuth.signOut();
                    PrefrenceFile.getInstance().setString("isLogin", "N");
                }
            }
        }
    }


    public void setView() {
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
        move_to_registration = (TextView) findViewById(R.id.move_to_registration);
        layout_registration = (LinearLayout) findViewById(R.id.layout_registration);
        layout_login = (LinearLayout) findViewById(R.id.layout_login);
        login_btn = (Button) findViewById(R.id.login_btn);
        regis_btn = (Button) findViewById(R.id.regis_btn);

        editText_Username_login = (EditText) findViewById(R.id.editText_Username_login);
        edit_text_fname = (EditText) findViewById(R.id.edit_text_fname);
        edit_text_sec_name = (EditText) findViewById(R.id.edit_text_sec_name);

        editText_password_login = (EditText) findViewById(R.id.editText_password_login);
        email_id_signup = (EditText) findViewById(R.id.email_id_signup);
        editText_password_signup = (EditText) findViewById(R.id.editText_password_signup);

        include_google_signIn = (LinearLayout) findViewById(R.id.include_google_signIn);
        include_or_layout = (LinearLayout) findViewById(R.id.include_or_layout);
        title_sp = (Spinner) findViewById(R.id.title_sp);

        forgot_password = (TextView) findViewById(R.id.forgot_password);
        login_here = (TextView) findViewById(R.id.login_here);
        login_here.setText(Html.fromHtml(getResources().getString(R.string.exist_login_here)));
        login_here.setOnClickListener(this);


        move_to_registration.setText(Html.fromHtml(getResources().getString(R.string.new_user_join)));
        login_btn.setOnClickListener(this);
        regis_btn.setOnClickListener(this);
        forgot_password.setOnClickListener(this);
        move_to_registration.setOnClickListener(this);

        signInButton = (SignInButton) findViewById(R.id.google_signIn);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        typeface = Typeface.createFromAsset(getAssets(), "alice_reg.ttf");


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.login_btn: {
                if (checkValidatorLogin()) {
                    utilityOfActivity.progressDialogShow("Please Wait");
                    signIn(editText_Username_login.getText().toString(), editText_password_login.getText().toString());
                } else {
                    showSnackBar("Fill Correct Details.");
                }
                break;
            }
            case R.id.regis_btn: {

                if (checkValidatorSignUp()) {
                    utilityOfActivity.progressDialogShow("Please Wait");
                    signUp(email_id_signup.getText().toString(), editText_password_signup.getText().toString());
                } else {
                    showSnackBar("Fill Correct Details.");
                }
                break;
            }
            case R.id.move_to_registration: {

                layout_login.setVisibility(View.GONE);
                layout_registration.setVisibility(View.VISIBLE);
                include_google_signIn.setVisibility(View.GONE);
                include_or_layout.setVisibility(View.GONE);
                break;
            }

            case R.id.login_here: {
                layout_login.setVisibility(View.VISIBLE);
                layout_registration.setVisibility(View.GONE);
                include_google_signIn.setVisibility(View.VISIBLE);
                include_or_layout.setVisibility(View.VISIBLE);
                break;
            }

            case R.id.forgot_password: {


                break;
            }


        }
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        Intent intent = getIntent();
        if (intent.getStringExtra("calledBy") == null) {

            utilityOfActivity.progressDialogShow("Please Wait...");
            firebaseUser = mAuth.getCurrentUser();
            updateUI(firebaseUser, false);
        }
    }

    public void updateUI(FirebaseUser user, boolean flag) {
        utilityOfActivity.progresDissmiss();

        if (user != null) {
            if (user.isEmailVerified()) {
                AppCommonObject.getInstance().setUserName(user.getDisplayName());
                PrefrenceFile.getInstance().setString("isLogin", "Y");
                Intent intent = new Intent(this, DashBoardActivity.class);
                startActivity(intent);
                finish();
            } else {
                if (flag)
                    PrefrenceFile.getInstance().setString("isLogin", "N");
                showSnackBar("Email not verified.");
            }
        }
    }

    public void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(LoginRegistrationActivity.this, "SignUp Successfull", Toast.LENGTH_LONG).show();
                            user = mAuth.getCurrentUser();
                            updateUserInformation();
                            verifyEmailAddress();
                            include_google_signIn.setVisibility(View.VISIBLE);
                            include_or_layout.setVisibility(View.VISIBLE);
                            updateUI(user, true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginRegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            utilityOfActivity.progresDissmiss();
                        }

                        // ...
                    }
                });

    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            user = mAuth.getCurrentUser();
                            AppCommonObject.getInstance().setUserName(user.getDisplayName());
                            PrefrenceFile.getInstance().deleteRecord("login_through");
                            PrefrenceFile.getInstance().setString("login_through", "FireBase");
                            updateUI(user, true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginRegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null, true);
                        }

                        // ...
                    }
                });


    }

    public void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void configureGoogleSignIn() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();

        mGoogleApiClient.connect();
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
                utilityOfActivity.progresDissmiss();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            user = mAuth.getCurrentUser();
                            PrefrenceFile.getInstance().deleteRecord("login_through");
                            PrefrenceFile.getInstance().setString("login_through", "GoogleLogin");
                            updateUI(user, true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginRegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null, true);
                        }

                        // ...
                    }
                });
    }


    public void onViewClick(View view) {

        switch (view.getId()) {
            case R.id.gplus: {
                utilityOfActivity.progressDialogShow("Please Wait...");
                signInButton.performClick();
                signInWithGoogle();
                break;
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(this);
            mGoogleApiClient.disconnect();
        }
    }


    public void verifyEmailAddress() {
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            showSnackBar("Verification sent to your mail id.");
                            layout_registration.setVisibility(View.GONE);
                            layout_login.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    public void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar
                .make(linearLayout, msg, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(LoginRegistrationActivity.this, R.color.bluish_brown));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(LoginRegistrationActivity.this, R.color.white));
        textView.setTypeface(typeface);
        snackbar.show();
    }

    public void updateUserInformation() {


        String userFullName = title_sp.getSelectedItem().toString() + " " + edit_text_fname.getText().toString() +
                " " + edit_text_sec_name.getText().toString();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(userFullName)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }

    public void setTitileSpinner() {

        String items[] = getResources().getStringArray(R.array.titleArray);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.layout_spinner_text_view, items) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                Typeface externalFont = Typeface.createFromAsset(getAssets(), "alice_reg.ttf");
                ((TextView) v).setTypeface(externalFont);

                return v;
            }


            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);

                Typeface externalFont = Typeface.createFromAsset(getAssets(), "alice_reg.ttf");
                ((TextView) v).setTypeface(externalFont);


                return v;
            }
        };


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        title_sp.setAdapter(adapter);
    }


    public boolean checkValidatorSignUp() {
        if (title_sp.getSelectedItem().toString().equalsIgnoreCase("Select Title")) {
            return false;
        }
        if (edit_text_fname.getText().toString().equalsIgnoreCase("") ||
                edit_text_fname.getText().toString() == null) {
            return false;
        }
        if (edit_text_sec_name.getText().toString().equalsIgnoreCase("") ||
                edit_text_sec_name.getText().toString() == null) {
            return false;
        }
        if (email_id_signup.getText().toString().equalsIgnoreCase("") ||
                email_id_signup.getText().toString() == null) {
            return false;
        }
        if (editText_password_signup.getText().toString().equalsIgnoreCase("") ||
                editText_password_signup.getText().toString() == null) {
            return false;
        }

        return true;
    }

    public boolean checkValidatorLogin() {

        if (editText_Username_login.getText().toString().equalsIgnoreCase("") ||
                editText_Username_login.getText().toString() == null) {
            return false;
        }
        if (editText_password_login.getText().toString().equalsIgnoreCase("") ||
                editText_password_login.getText().toString() == null) {
            return false;
        }
        return true;
    }


    public void resetPassword(String mail) {

        mAuth.sendPasswordResetEmail(mail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }


    public void signOutGoogle() {
        mAuth.signOut();

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.d("SignOut", "GooglePlus");
                        PrefrenceFile.getInstance().setString("isLogin", "N");
                    }
                });
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Intent intent = getIntent();
        if (intent.getStringExtra("calledBy") != null) {
            if (intent.getStringExtra("calledBy").equalsIgnoreCase("LOGOUT")) {
                //signOut by Application
                if (PrefrenceFile.getInstance().getString("login_through").equalsIgnoreCase("GoogleLogin")) {
                    signOutGoogle();

                }

            }

        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onBackPressed() {
        if (layout_registration.getVisibility() == View.VISIBLE) {
            layout_registration.setVisibility(View.GONE);
            layout_login.setVisibility(View.VISIBLE);
            include_google_signIn.setVisibility(View.VISIBLE);
            include_or_layout.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }

    }


}
