package phone.vishnu.dailygratitude;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class PasswordFragment extends Fragment {

    private TextInputEditText oldPasswordTIE, newPasswordTIE, confirmPasswordTIE;
    private Button saveButton, cancelButton, removeButton;
    private Switch reminderSwitch;
    private TextView reminderTimeTV;

    public PasswordFragment() {
    }

    public static PasswordFragment newInstance() {
        return new PasswordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_password, container, false);

        oldPasswordTIE = inflate.findViewById(R.id.oldPasswordTIE);
        newPasswordTIE = inflate.findViewById(R.id.newPasswordTIE);
        confirmPasswordTIE = inflate.findViewById(R.id.confirmPasswordTIE);

        saveButton = inflate.findViewById(R.id.buttonSavePassword);
        cancelButton = inflate.findViewById(R.id.buttonCancel);
        removeButton = inflate.findViewById(R.id.buttonRemove);

        reminderSwitch = inflate.findViewById(R.id.reminderSwitch);
        reminderTimeTV = inflate.findViewById(R.id.reminderHintTV);

        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setFABVisibility(View.VISIBLE);
                getActivity().onBackPressed();
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String PASSWORD_PREF = "passWordPreference";

                SharedPreferences sharedPref = getContext().getSharedPreferences(getActivity().getPackageName(), MODE_PRIVATE);
                sharedPref.edit().putString(PASSWORD_PREF, "NotSet!").apply();
                Toast.makeText(getActivity(), "Password Removed", Toast.LENGTH_SHORT).show();

                ((MainActivity) getActivity()).setFABVisibility(View.VISIBLE);
                getActivity().onBackPressed();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldPassword = oldPasswordTIE.getText().toString().trim();
                String newPassword = newPasswordTIE.getText().toString().trim();
                String confirmPassword = confirmPasswordTIE.getText().toString().trim();

                String PASSWORD_PREF = "passWordPreference";

                SharedPreferences sharedPref = getContext().getSharedPreferences(getActivity().getPackageName(), MODE_PRIVATE);

                String originalOldPassword = sharedPref.getString(PASSWORD_PREF, "NotSet!");

                if ((oldPassword.isEmpty() && !originalOldPassword.equals("NotSet!")) || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    if (oldPassword.isEmpty()) {
                        oldPasswordTIE.setError("Field Empty");
                        oldPasswordTIE.requestFocus();
                    } else if (newPassword.isEmpty()) {
                        newPasswordTIE.setError("Field Empty");
                        newPasswordTIE.requestFocus();
                    } else if (confirmPassword.isEmpty()) {
                        confirmPasswordTIE.setError("Field Empty");
                        confirmPasswordTIE.requestFocus();
                    }
                } else {


                    if (oldPassword.equals(originalOldPassword) || originalOldPassword.equals("NotSet!")) {

                        if (newPassword.equals(confirmPassword)) {
                            sharedPref.edit().putString(PASSWORD_PREF, confirmPassword).apply();
                            Toast.makeText(getActivity(), "Password Changed", Toast.LENGTH_SHORT).show();

                            ((MainActivity) getActivity()).setFABVisibility(View.VISIBLE);
                            getActivity().onBackPressed();
                        } else {
                            confirmPasswordTIE.setError("Passwords do not match");
                            confirmPasswordTIE.requestFocus();
                        }
                    } else {
                        oldPasswordTIE.setError("Wrong Password");
                        oldPasswordTIE.requestFocus();
                    }
                }
            }
        });

        reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                final String ALARM_PREFERENCE_TIME = "customAlarmPreference";
                final SharedPreferences.Editor preferences = getActivity().getSharedPreferences(getActivity().getPackageName(), MODE_PRIVATE).edit();

                if (isChecked) {

                    final Calendar c = Calendar.getInstance();

                    TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            c.set(Calendar.MINUTE, minute);

                            preferences.putString(ALARM_PREFERENCE_TIME, "At " + hourOfDay + " : " + minute + " Daily").apply();

                            reminderTimeTV.setText(MessageFormat.format("At {0} : {1} Daily", hourOfDay, minute));
                            reminderTimeTV.setVisibility(View.VISIBLE);

                            myAlarm(c);

                        }
                    }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));
                    timePicker.show();

                } else {
                    reminderTimeTV.setVisibility(View.GONE);

                    preferences.putString(ALARM_PREFERENCE_TIME, "Alarm Not Set").apply();

                    Intent intent = new Intent(getActivity(), NotificationReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

                    if (alarmManager != null) {
                        alarmManager.cancel(pendingIntent);
                    }
                }
            }
        });
    }

    private void myAlarm(Calendar calendar) {

        if (calendar.getTime().compareTo(new Date()) < 0) calendar.add(Calendar.DAY_OF_MONTH, 1);

        Intent intent = new Intent(getActivity(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getApplicationContext().getSystemService(ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
}