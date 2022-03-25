package view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.myresto.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import model.User;


public class Authentication extends AppCompatActivity {

    private static final String TAG = "GOOGLEAUTH";
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    Dialog dialog;
    ActivityResultLauncher<Intent> resultLauncher;

    Button signInBtn;


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            // startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        //Button signInBtn = findViewById(R.id.signwithgmail);
        createRequest();
        createActivityResultLauncher();


        dialog = new Dialog(Authentication.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_wait);
        dialog.setCanceledOnTouchOutside(false);
        onClickListenerSigneIn();
        mAuth = FirebaseAuth.getInstance();

    }

    private void onClickListenerSigneIn() {
        signInBtn = findViewById(R.id.signwithgmail);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultLauncher.launch(new Intent(mGoogleSignInClient.getSignInIntent()));


            }
        });
    }

    private void createRequest() {
        // Configure Google SignIn.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    private void createActivityResultLauncher() {


        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();

                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
                            try {
                                // Google Sign In was successful, authenticate with Firebase
                                GoogleSignInAccount account = task.getResult(ApiException.class);
                                Log.e(TAG, "firebaseAuthWithGoogle:" + account.getId());
                                firebaseAuthWithGoogle(account.getIdToken());
                            } catch (ApiException e) {
                                // Google Sign In failed, update UI appropriately
                                Log.e(TAG, "Google sign in failed", e);
                            }
                        } else {
                            Log.e(TAG, String.valueOf(result.getData().getDataString()));
                        }
                    }
                });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(Authentication.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            //dialog.dismiss();
                            //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "signInWithCredential:failure", task.getException());
                            //  Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            // updateUI(null);
                            //dialog.dismiss();
                            Toast.makeText(Authentication.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }
   /* private void checkIfUserExistInFirestore() {
        if (mAuth.getCurrentUser() != null)
            viewModel.getWorkmate(mAuth.getCurrentUser().getUid()).observe(this, user -> {
                if (user == null)
                    createUserInFirestore();
                else
                    startMainActivity();
            });
    }
    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }




    private void createUserInFirestore() {
        if (this.mAuth.getCurrentUser() != null) {
            String user = mAuth.getCurrentUser().getUid();
            String username = mAuth.getCurrentUser().getDisplayName();
            String urlPicture = (mAuth.getCurrentUser().getPhotoUrl() != null) ? mAuth.getCurrentUser().getPhotoUrl().toString() : null;
            String email = mAuth.getCurrentUser().getEmail();
            User currentUser= new User(mAuth.getUid(), username, urlPicture, email);

            viewModel.createWorkmate(currentUser);
            viewModel.getCreatedWorkmateLiveData().observe(this, user -> {
                if (user == null)
                    onFailureListener();
                else
                    startMainActivity();
            });
        }
    }*/


}