package phone.vishnu.dailygratitude;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import static android.content.Context.MODE_PRIVATE;

public class PasswordFragment extends Fragment {

    private TextInputEditText oldPasswordTIE, newPasswordTIE, confirmPasswordTIE;
    private Button saveButton, cancelButton, removeButton;

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
    }
}