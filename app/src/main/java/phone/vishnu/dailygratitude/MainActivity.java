package phone.vishnu.dailygratitude;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String PASSWORD_PREF = "passWordPreference";
    private final int ADD_REQUEST_CODE = 22;
    private GratitudeViewModel gratitudeViewModel;
    private RecyclerViewAdapter adapter;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton = findViewById(R.id.addFAB);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        gratitudeViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(GratitudeViewModel.class);
        gratitudeViewModel.getAllGratitude().observe(this, new Observer<List<Gratitude>>() {
            @Override
            public void onChanged(List<Gratitude> gratitudes) {
                for (Gratitude gratitude : gratitudes) {
                    if (gratitude.getDateAdded().equals(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date())))
                        floatingActionButton.setTag("Nope");
                }
                adapter.submitList(gratitudes);
            }
        });
    }

    public void addFABClicked(View view) {
        if ("Nope".equals(view.getTag()))

            Toast.makeText(MainActivity.this, "Today's Gratitude is set. Come Back Tomorrow...", Toast.LENGTH_LONG).show();
        else
            startActivityForResult(new Intent(MainActivity.this, AddActivity.class), ADD_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && null != data && requestCode == ADD_REQUEST_CODE) {

            String title = Objects.requireNonNull(data.getExtras()).getString(AddActivity.TITLE_EXTRA, "");
            String description = data.getExtras().getString(AddActivity.DESCRIPTION_EXTRA, "");

            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Gratitude gratitude = new Gratitude(title, description, date);
            gratitudeViewModel.insert(gratitude);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Changes Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_password: {
                showDialog();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(24, 12, 24, 12);

        TextInputLayout t1 = new TextInputLayout(MainActivity.this);
        t1.setHintAnimationEnabled(true);
        t1.setLayoutParams(layoutParams);
        t1.isHintEnabled();

        final TextInputEditText e1 = new TextInputEditText(MainActivity.this);
        e1.setHint("Password");
        e1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        t1.addView(e1);

        final androidx.appcompat.app.AlertDialog.Builder builder =
                new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
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
                    getSharedPreferences(getPackageName(), MODE_PRIVATE).edit().putString(PASSWORD_PREF, passWord).apply();
                }
            }
        });
        builder.show();
    }

}