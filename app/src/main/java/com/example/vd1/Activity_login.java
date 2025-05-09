package com.example.vd1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Activity_login extends AppCompatActivity {

    EditText edtUsername, edtPassword, edtConfirmPassword;
    EditText edtFullName, edtBirthPlace, edtPhone;
    Button btnSubmit;
    TextView tvToggle, tvTitle;

    boolean isLoginMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtFullName = findViewById(R.id.edtFullName);
        edtBirthPlace = findViewById(R.id.edtBirthPlace);
        edtPhone = findViewById(R.id.edtPhone);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvToggle = findViewById(R.id.tvToggle);
        tvTitle = findViewById(R.id.tvTitle);

        tvToggle.setOnClickListener(v -> toggleMode());
        btnSubmit.setOnClickListener(v -> handleAuth());
    }

    private void handleAuth() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        if (isLoginMode) {
            Log.d("Login", "Attempting login with: " + username + ", " + password);
            boolean found = false;
            for (Activity_patient.Patient p : Activity_patient.patientList) {
                Log.d("Login", "Comparing with: " + p.name + ", " + p.password);
                if (p.name.equals(username) && p.password.equals(password)) {
                    found = true;
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                    return;
                }
            }
            if (!found) {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }

        } else {
            String fullName = edtFullName.getText().toString().trim();
            String birthPlace = edtBirthPlace.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                    || fullName.isEmpty() || birthPlace.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            String id = "BN" + (Activity_patient.patientList.size() + 1);
            Activity_patient.Patient newPatient = new Activity_patient.Patient(id, fullName, phone, birthPlace, password);
            Activity_patient.patientList.add(newPatient);
            Log.d("Registration", "New patient registered: " + newPatient.name + ", " + newPatient.password);
            Log.d("Registration", "Patient list size: " + Activity_patient.patientList.size());
            Toast.makeText(this, "Đăng ký thành công! Mời đăng nhập.", Toast.LENGTH_SHORT).show();
            toggleMode();
        }
    }

    private void toggleMode() {
        isLoginMode = !isLoginMode;

        tvTitle.setText(isLoginMode ? "Đăng nhập" : "Đăng ký");
        btnSubmit.setText(isLoginMode ? "Đăng nhập" : "Đăng ký");
        tvToggle.setText(isLoginMode ? "Chưa có tài khoản? Đăng ký" : "Đã có tài khoản? Đăng nhập");

        edtConfirmPassword.setVisibility(isLoginMode ? View.GONE : View.VISIBLE);
        edtFullName.setVisibility(isLoginMode ? View.GONE : View.VISIBLE);
        edtBirthPlace.setVisibility(isLoginMode ? View.GONE : View.VISIBLE);
        edtPhone.setVisibility(isLoginMode ? View.GONE : View.VISIBLE);

        edtUsername.setText("");
        edtPassword.setText("");
        edtConfirmPassword.setText("");
        edtFullName.setText("");
        edtBirthPlace.setText("");
        edtPhone.setText("");
    }
}