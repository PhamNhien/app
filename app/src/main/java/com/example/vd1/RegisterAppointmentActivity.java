package com.example.vd1;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;  // Import List
// Import class Appointment từ ViewAppointmentsCombinedActivity
import com.example.vd1.ViewAppointmentsCombinedActivity.Appointment;

public class RegisterAppointmentActivity extends AppCompatActivity {

    private EditText edtPatientName, edtAppointmentDate;
    private Spinner spinnerSpecialties, spinnerDoctors;
    private Button btnRegisterAppointment, btnBackAppointment;
    private ArrayAdapter<String> doctorAdapter;
    private ArrayList<Activity_doctor.Doctor> allDoctors;
    private ArrayList<String> specialtyList;

    public static List<Appointment> registeredAppointments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_appointment);

        try {
            edtPatientName = findViewById(R.id.edtPatientName);
            edtAppointmentDate = findViewById(R.id.edtAppointmentDate);
            spinnerSpecialties = findViewById(R.id.spinnerSpecialties);
            spinnerDoctors = findViewById(R.id.spinnerDoctors);
            btnRegisterAppointment = findViewById(R.id.btnRegisterAppointment);
            btnBackAppointment = findViewById(R.id.btnBackAppointment);

            allDoctors = Activity_doctor.getDoctorList(); // lấy danh sách bác sĩ

            if (allDoctors == null || allDoctors.isEmpty()) {
                Toast.makeText(this, "Không có bác sĩ nào để hiển thị!", Toast.LENGTH_LONG).show();
                return;
            }

            setupSpecialtySpinner();

            btnRegisterAppointment.setOnClickListener(v -> registerAppointment());
            btnBackAppointment.setOnClickListener(v -> finish());

        } catch (Exception e) {
            showError(e);
        }
    }

    private void setupSpecialtySpinner() {
        try {
            HashSet<String> specialties = new HashSet<>();
            for (Activity_doctor.Doctor doctor : allDoctors) {
                specialties.add(doctor.getSpecialty());
            }

            specialtyList = new ArrayList<>(specialties);
            if (specialtyList.isEmpty()) {
                Toast.makeText(this, "Không có chuyên khoa nào!", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayAdapter<String> specialtyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, specialtyList);
            specialtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSpecialties.setAdapter(specialtyAdapter);

            spinnerSpecialties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedSpecialty = specialtyList.get(position);
                    loadDoctorsBySpecialty(selectedSpecialty);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });

        } catch (Exception e) {
            showError(e);
        }
    }

    private void loadDoctorsBySpecialty(String specialty) {
        try {
            ArrayList<String> doctorNames = new ArrayList<>();
            boolean hasDoctors = false; // Thêm biến để theo dõi xem có bác sĩ nào không
            for (Activity_doctor.Doctor doctor : allDoctors) {
                if (doctor.getSpecialty().equalsIgnoreCase(specialty)) {
                    doctorNames.add(doctor.getName() + " (ID: " + doctor.getId() + ")");
                    hasDoctors = true; // Đặt thành true nếu tìm thấy bác sĩ
                }
            }

            if (!hasDoctors) { // Kiểm tra biến hasDoctors
                doctorNames.add("Không có bác sĩ nào!");
                spinnerDoctors.setEnabled(false);
            } else {
                spinnerDoctors.setEnabled(true);
            }

            doctorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doctorNames);
            doctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDoctors.setAdapter(doctorAdapter);

        } catch (Exception e) {
            showError(e);
        }
    }

    private void registerAppointment() {
        try {
            String patientName = edtPatientName.getText().toString().trim();
            String appointmentDate = edtAppointmentDate.getText().toString().trim();
            String selectedDoctorName = (String) spinnerDoctors.getSelectedItem();

            if (patientName.isEmpty() || appointmentDate.isEmpty() || selectedDoctorName == null || selectedDoctorName.equals("Không có bác sĩ nào!")) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin và chọn bác sĩ", Toast.LENGTH_SHORT).show();
                return;
            }
            String selectedSpecialty = spinnerSpecialties.getSelectedItem().toString();

            // Lấy thông tin bác sĩ từ tên đã chọn.
            String doctorName = selectedDoctorName.substring(0, selectedDoctorName.indexOf(" ("));

            // Tạo đối tượng Appointment và thêm vào danh sách
            Appointment appointment = new Appointment(patientName, doctorName, selectedSpecialty, appointmentDate);
            registeredAppointments.add(appointment);

            Toast.makeText(this, "Đăng ký lịch hẹn thành công!", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            showError(e);
        }
    }

    private void showError(Exception e) {
        e.printStackTrace();
        Toast.makeText(this, "Đã xảy ra lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}

