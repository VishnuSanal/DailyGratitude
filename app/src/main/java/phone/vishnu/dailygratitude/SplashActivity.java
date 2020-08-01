package phone.vishnu.dailygratitude;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SplashActivity extends AppCompatActivity {

    private final String PASSWORD_PREF = "passWordPreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int SPLASH_TIMEOUT = 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String string = getSharedPreferences(getPackageName(), MODE_PRIVATE).getString(PASSWORD_PREF, "NotSet!@#$%^&*()_+");

                if (!("NotSet!@#$%^&*()_+").equals(string))
                    showDialog(string);
                else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
                }
            }
        }, SPLASH_TIMEOUT * 1000);

    }

    private void showDialog(final String string) {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(24, 12, 24, 12);

        TextInputLayout t1 = new TextInputLayout(SplashActivity.this);
        t1.setHintAnimationEnabled(true);
        t1.setLayoutParams(layoutParams);
        t1.isHintEnabled();

        final TextInputEditText e1 = new TextInputEditText(SplashActivity.this);
        e1.setHint("Password");
        e1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        t1.addView(e1);

        final androidx.appcompat.app.AlertDialog.Builder builder =
                new androidx.appcompat.app.AlertDialog.Builder(SplashActivity.this);
        builder.setTitle("Enter the Password");
        builder.setView(t1);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String passWord = e1.getText().toString().trim();

                if (passWord.isEmpty()) {
                    e1.setError("Please Enter A Value");
                    e1.requestFocus();
                } else {
                    if (passWord.equals(string)) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        SplashActivity.this.finish();
                    } else {
                        Toast.makeText(SplashActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        /*Snackbar.make(findViewById(R.id.textView4), "Wrong Password", BaseTransientBottomBar.LENGTH_INDEFINITE).setAction("Try Again", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showDialog(string);
                            }
                        });*/

                    }
                }
            }
        });
        builder.show();
    }
}