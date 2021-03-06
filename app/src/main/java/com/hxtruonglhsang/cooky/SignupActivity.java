package com.hxtruonglhsang.cooky;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.firebase.auth.FirebaseUser;
import com.hxtruonglhsang.cooky.model.User;
import com.hxtruonglhsang.cooky.service.Firebase;
import com.hxtruonglhsang.cooky.service.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @BindView(R.id.input_name)
    EditText _nameText;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_signup)
    Button _signupButton;
    @BindView(R.id.link_login)
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        handleDisplayUI();

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    private void handleDisplayUI() {
        ImageView logo = findViewById(R.id.logoApp);
        Glide.with(getApplicationContext()).load(R.drawable.icon_primary_color).into(logo);

        String normalText = "Already a member? ";
        String boldText = "Login";
        SpannableString str = new SpannableString(normalText + boldText);
        str.setSpan(new StyleSpan(Typeface.BOLD), normalText.length(), normalText.length() + boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextView loginInfo = findViewById(R.id.link_login);
        loginInfo.setText(str);
    }

    public void signup() {
        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this, R.style.Theme_AppCompat);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang tạo tài khoản ...");
        progressDialog.show();

        final String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // Implement your own signup logic here.

        Firebase.signUpWithEmail(email, password, new Firebase.ISignUpCallback() {
            @Override
            public void onCallback(FirebaseUser currentUser) {
                if (currentUser != null) {
                    User user = new User();
                    user.setId(currentUser.getUid());
                    user.setEmail(currentUser.getEmail());
                    user.setUserName(name);
                    UserService.updateUser(user);
                    onSignupSuccess();
                } else {
                    onSignupFailed();
                }
                progressDialog.dismiss();
            }
        });

//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onSignupSuccess or onSignupFailed
//                        // depending on success
//                        onSignupSuccess();
//                        // onSignupFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(false);
        setResult(RESULT_OK, null);
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Đăng ký thất bại!", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
