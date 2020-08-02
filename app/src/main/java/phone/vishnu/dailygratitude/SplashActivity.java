package phone.vishnu.dailygratitude;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SplashActivity extends AppCompatActivity {

    private final String PASSWORD_PREF = "passWordPreference";
    private TextInputEditText editText;
    private TextInputLayout inputLayout;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        editText = findViewById(R.id.passwordTIE);
        inputLayout = findViewById(R.id.passwordTIL);
        textView = findViewById(R.id.appNameTV);
        button = findViewById(R.id.buttonSubmit);

        int SPLASH_TIMEOUT = 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final String string = getSharedPreferences(getPackageName(), MODE_PRIVATE).getString(PASSWORD_PREF, "NotSet!@#$%^&*()_+");

                if (!("NotSet!@#$%^&*()_+").equals(string)) {
                    textView.setVisibility(View.GONE);
                    inputLayout.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    editText.requestFocus();

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String passWord = editText.getText().toString().trim();

                            if (passWord.isEmpty()) {
                                editText.setError("Please Enter A Value");
                                editText.requestFocus();
                            } else {
                                if (passWord.equals(string)) {
                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                    SplashActivity.this.finish();
                                } else {

                                    editText.setError("Wrong Password");
                                    editText.requestFocus();
                                }
                            }

                        }
                    });

                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
                }
            }
        }, SPLASH_TIMEOUT * 1000);

    }

}