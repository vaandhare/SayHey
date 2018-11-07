package in.indekode.sayhey;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText et_username, et_email, et_pw;
    Button btn_reg;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_username = findViewById(R.id.ett_username);
        et_email = findViewById(R.id.ett_emailid);
        et_pw = findViewById(R.id.ett_password);
        btn_reg = findViewById(R.id.btn_register);
        mAuth = FirebaseAuth.getInstance();

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString();
                String emailid = et_email.getText().toString();
                String password = et_pw.getText().toString();

                if (username.isEmpty() || emailid.isEmpty() || password.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if ( password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password must be greater than 6 characters", Toast.LENGTH_SHORT).show();
                } else{
                  Register(username, emailid, password);
                }
            }
        });

    }

    public void Register(final String username, String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();

                    String userID = firebaseUser.getUid();

                    mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userID);
                    hashMap.put("username", username);
                    hashMap.put("imageURL", "default");
                    hashMap.put("Status", "Offline");

                    mDatabaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Registration Successful !!", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            }
                        }
                    });
                }else {
                    Toast.makeText(RegisterActivity.this, "Error in registration", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sendEmailVerification(){
        FirebaseUser firebaseUser1 = mAuth.getCurrentUser();
        if(firebaseUser1 != null){
            firebaseUser1.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if ( task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Verification link is send to your Email-id", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        finish();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }else {
                        Toast.makeText(RegisterActivity.this, "Failed to send verification Email",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
