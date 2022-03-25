package view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myresto.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AuthentificationFirstUserActivity extends AppCompatActivity {

    EditText mname, mEmail;
    Button registrBtnSignIn;
    FirebaseFirestore mDataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification_first_user);
        mDataBase = FirebaseFirestore.getInstance();
        mname = findViewById(R.id.editTextTextPersonName);
        mEmail = findViewById(R.id.editTextTextEmailAddress);
        registrBtnSignIn = findViewById(R.id.button);
        registrBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mName = mname.getText().toString();
                String  mMail = mEmail.getText().toString();
                Map<String, Object> user = new HashMap<>();
                user.put("name ", mName);
                user.put("email ", mMail);






                mDataBase.collection("user")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(AuthentificationFirstUserActivity.this, "Successful", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AuthentificationFirstUserActivity.this, "try again ", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}