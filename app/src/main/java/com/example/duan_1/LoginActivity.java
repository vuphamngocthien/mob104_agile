package com.example.duan_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan_1.ApdapterModel.SanPhamAdapter;
import com.example.duan_1.Model.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private EditText username, password;
    private AppCompatButton login;
    private SignInButton google_login;
    TextView tvDangKy;
    Switch active;

    private static final String TAG = "LoginActivity";
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
    String name, email;
    String idToken;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        tvDangKy = findViewById(R.id.textviewDangKy);
        active = findViewById(R.id.active);

        firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        //this is where we start the Auth state Listener to listen for whether the user is signed in or not
        authStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // Get signedIn user
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //if user is signed in, we call a helper method to save the user details to Firebase
                if (user != null) {
                    // User is signed in
                    // you could place other firebase code
                    //logic to save the user details to Firebase
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))//you can also use R.string.default_web_client_id
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        google_login = findViewById(R.id.google_login);
        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });

        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DangKyActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Login").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String input1 = username.getText().toString();
                        String input2 = password.getText().toString();

                        if((input1.equals("") || input2.equals(""))){
                            Toast.makeText(LoginActivity.this, "Không để trống!", Toast.LENGTH_SHORT).show();
                        }else if (dataSnapshot.child(input1).exists()) {
                            if (dataSnapshot.child(input1).child("password").getValue(String.class).equals(input2)) {
                                if (active.isChecked()) {
                                    if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("Người bán")) {
                                        preferences.setDataLogin(LoginActivity.this, true);
                                        preferences.setDataAs(LoginActivity.this, "Người bán");
                                        Intent intent = new Intent(LoginActivity.this,UserActivity.class);
                                        intent.putExtra("username",input1);
                                        startActivity(intent);
                                    } else if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("Người mua")){
                                        SanPhamAdapter.MyViewHoldere.hide();
                                        preferences.setDataLogin(LoginActivity.this, true);
                                        preferences.setDataAs(LoginActivity.this, "Người mua");
                                        Intent intent = new Intent(LoginActivity.this,UserActivity.class);
                                        intent.putExtra("username",input1);
                                        intent.putExtra("as", input2);
                                        startActivity(intent);
                                    }
                                } else {
                                    if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("Người bán")) {
                                        preferences.setDataLogin(LoginActivity.this, false);
                                        Intent intent = new Intent(LoginActivity.this,UserActivity.class);
                                        intent.putExtra("username",input1);
                                        startActivity(intent);

                                    } else if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("Người mua")){
                                        preferences.setDataLogin(LoginActivity.this, false);
                                        Intent intent = new Intent(LoginActivity.this,UserActivity.class);
                                        intent.putExtra("username",input1);
                                        startActivity(intent);
                                    }
                                }
                            } else {
                                username.setText("");
                                password.setText("");
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public void anImage(){
        SanPhamAdapter.MyViewHoldere.im_delete.setVisibility(View.INVISIBLE);
        SanPhamAdapter.MyViewHoldere.im_update.setVisibility(View.INVISIBLE);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (preferences.getDataLogin(this)) {
            if (preferences.getDataAs(this).equals("Người bán")) {
                startActivity(new Intent(this, UserActivity.class));
                finish();
            } else {
                SanPhamAdapter.MyViewHoldere.hide();
                startActivity(new Intent(this, UserActivity.class));
                finish();
            }
        }

        if (authStateListener != null){
            FirebaseAuth.getInstance().signOut();
        }
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            idToken = account.getIdToken();
            name = account.getDisplayName();
            email = account.getEmail();
            // you can store user data to SharedPreference
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            firebaseAuthWithGoogle(credential);
        }else{
            // Google Sign In failed, update UI appropriately
            Log.e(TAG, "Login Unsuccessful. "+result);
            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }
    private void firebaseAuthWithGoogle(AuthCredential credential){

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            gotoProfile();
                        }else{
                            Log.w(TAG, "signInWithCredential" + task.getException().getMessage());
                            task.getException().printStackTrace();
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }




    private void gotoProfile(){
        Intent intent = new Intent(LoginActivity.this, UserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

}