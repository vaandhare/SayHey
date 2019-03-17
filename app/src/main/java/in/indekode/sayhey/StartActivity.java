package in.indekode.sayhey;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    Button startLogin, startReg;
    FirebaseAuth auth;
    TextView privacy_policy, termsAC;

    @Override
    protected void onStart() {
        super.onStart();

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if( user != null){
            finish();
            startActivity(new Intent(StartActivity.this, MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startLogin = findViewById(R.id.bt_start_login);
        startReg = findViewById(R.id.bt_start_register);

        startLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }
        });

        startReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
            }
        });

        privacy_policy = findViewById(R.id.Private_policy);
        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(StartActivity.this);
                alert.setTitle("Privacy Policy");

                WebView wv = new WebView(StartActivity.this);
                wv.loadUrl("http://indekode.ml/SayHey/privacy_policy.html");
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);

                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

        termsAC = findViewById(R.id.TermsAndCondn);
        termsAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(StartActivity.this);
                alert.setTitle("Terms & Conditions");

                WebView wv = new WebView(StartActivity.this);
                wv.loadUrl("http://indekode.ml/SayHey/terms_and_conditions.html");
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);

                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

    }
}
