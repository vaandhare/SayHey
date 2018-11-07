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
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class ForgotPassActivity extends AppCompatActivity {

    MaterialEditText forgot_email;
    Button forgotBtn;
    FirebaseAuth resetauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Forgot Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        forgot_email = findViewById(R.id.forgot_email);
        forgotBtn = findViewById(R.id.forgot_button);
        resetauth = FirebaseAuth.getInstance();

        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resetemail = forgot_email.getText().toString();

                if (resetemail.isEmpty()){
                    Toast.makeText(ForgotPassActivity.this, "Enter your Registered Email-Id", Toast.LENGTH_SHORT).show();
                }
                else{
                    resetauth.sendPasswordResetEmail(resetemail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if ( task.isSuccessful()){
                                Toast.makeText(ForgotPassActivity.this, "Password Sent to Registered Email..", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgotPassActivity.this, LoginActivity.class));
                            }
                            else
                                Toast.makeText(ForgotPassActivity.this, "Email is either not registered or incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}
