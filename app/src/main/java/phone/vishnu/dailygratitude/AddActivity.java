package phone.vishnu.dailygratitude;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AddActivity extends AppCompatActivity {

    public static final String TITLE_EXTRA = "com.vishnu.dailygratitude.TITLE_STRING";
    public static final String DESCRIPTION_EXTRA = "com.vishnu.dailygratitude.DESCRIPTION_STRING";
    private FloatingActionButton saveButton;
    private TextInputEditText titleTIE, descriptionTIE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        titleTIE = findViewById(R.id.addTitleTIE);
        descriptionTIE = findViewById(R.id.addDescriptionTIE);
        saveButton = findViewById(R.id.addNewSaveIV);

        Toolbar toolbar = findViewById(R.id.addEditToolbar);
        toolbar.setTitle("Today I'm Grateful for");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        saveButton.setImageResource(R.drawable.add_note);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = titleTIE.getText().toString().trim();
                String description = descriptionTIE.getText().toString().trim();

                if (isValid(title, description)) {
                    sendData(title, description);
                }
            }
        });
    }

    private void sendData(String title, String description) {
        Intent i = new Intent();
        i.putExtra(TITLE_EXTRA, title);
        i.putExtra(DESCRIPTION_EXTRA, description);

        setResult(RESULT_OK, i);
        finish();
    }

    private boolean isValid(String title, String description) {

        if (title.isEmpty() || description.isEmpty()) {

            if (title.isEmpty()) {
                titleTIE.setError("Field Empty");
                titleTIE.requestFocus();
            } else if (description.isEmpty()) {
                descriptionTIE.setError("Field Empty");
                descriptionTIE.requestFocus();
            }

            return false;
        }
        return true;
    }

}