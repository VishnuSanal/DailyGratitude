package phone.vishnu.dailygratitude.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import phone.vishnu.dailygratitude.R;
import phone.vishnu.dailygratitude.adapter.RecyclerViewAdapter;
import phone.vishnu.dailygratitude.fragment.PasswordFragment;
import phone.vishnu.dailygratitude.model.Gratitude;
import phone.vishnu.dailygratitude.viewmodel.GratitudeViewModel;

public class MainActivity extends AppCompatActivity {

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
//        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.id_password) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, PasswordFragment.newInstance()).addToBackStack(null).commit();
            setFABVisibility(View.GONE);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setFABVisibility(int visibility) {
        floatingActionButton.setVisibility(visibility);
    }
}