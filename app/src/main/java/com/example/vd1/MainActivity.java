package com.example.vd1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnLogout;
    private LinearLayout cardPatients, cardDoctors, cardAppointments, cardBills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Tên file XML là activity_main.xml

        // Ánh xạ view
        btnLogout = findViewById(R.id.btnLogout);
        cardPatients = findViewById(R.id.cardPatients);
        cardDoctors = findViewById(R.id.cardDoctors);
        cardAppointments = findViewById(R.id.cardAppointments);
        cardBills = findViewById(R.id.cardBills); // Thêm dòng này để ánh xạ cardBills

        // Xử lý click nút Đăng xuất
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            // Chuyển về màn hình đăng nhập (LoginActivity là ví dụ)
            Intent intent = new Intent(MainActivity.this, Activity_login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Xử lý click từng chức năng
        cardPatients.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Activity_patient.class);
            startActivity(intent);
        });

        cardDoctors.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Activity_doctor.class);
            startActivity(intent);
        });

        cardAppointments.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterAppointmentActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Thêm flag này để tránh lỗi stack
            startActivity(intent);
        });

        // Xử lý click vào cardBills để xem lịch khám
        cardBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewAppointmentsCombinedActivity.class);
                startActivity(intent);
            }
        });
    }
}

