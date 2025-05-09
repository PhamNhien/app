package com.example.vd1;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ViewAppointmentsCombinedActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAppointments;
    private AppointmentAdapter appointmentAdapter;
    private List<Appointment> appointmentList;
    private TextView emptyTextView;
    private Button btnBack;

    public static class Appointment {
        private String patientName;
        private String doctorName;
        private String specialty;
        private String appointmentTime;

        public Appointment(String patientName, String doctorName, String specialty, String appointmentTime) {
            this.patientName = patientName;
            this.doctorName = doctorName;
            this.specialty = specialty;
            this.appointmentTime = appointmentTime;
        }

        public String getPatientName() {
            return patientName;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public String getSpecialty() {
            return specialty;
        }

        public String getAppointmentTime() {
            return appointmentTime;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty;
        }

        public void setAppointmentTime(String appointmentTime) {
            this.appointmentTime = appointmentTime;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointments);

        recyclerViewAppointments = findViewById(R.id.recyclerViewAppointments);
        recyclerViewAppointments.setLayoutManager(new LinearLayoutManager(this));
        emptyTextView = findViewById(R.id.emptyTextView);
        btnBack = findViewById(R.id.btnBack);

        appointmentList = getAppointmentsFromRegistration();
        appointmentAdapter = new AppointmentAdapter(this, appointmentList);
        recyclerViewAppointments.setAdapter(appointmentAdapter);

        updateEmptyView();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private List<Appointment> getAppointmentsFromRegistration() {
        List<Appointment> appointments = new ArrayList<>();
        if (RegisterAppointmentActivity.registeredAppointments != null) {
            appointments.addAll(RegisterAppointmentActivity.registeredAppointments);
        }
        return appointments;
    }

    private void updateEmptyView() {
        if (appointmentList.isEmpty()) {
            recyclerViewAppointments.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerViewAppointments.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
        }
    }

    private class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {
        private Context context;
        private List<Appointment> appointmentList;

        public AppointmentAdapter(Context context, List<Appointment> appointmentList) {
            this.context = context;
            this.appointmentList = appointmentList;
        }

        @NonNull
        @Override
        public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_appointment, parent, false);
            return new AppointmentViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
            Appointment appointment = appointmentList.get(position);
            holder.textViewPatientName.setText("Bệnh nhân: " + appointment.getPatientName());
            holder.textViewDoctorName.setText("Bác sĩ: " + appointment.getDoctorName());
            holder.textViewSpecialty.setText("Chuyên khoa: " + appointment.getSpecialty());
            holder.textViewAppointmentTime.setText("Thời gian: " + appointment.getAppointmentTime());

//            holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showEditDialog(position);
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return appointmentList.size();
        }

        public class AppointmentViewHolder extends RecyclerView.ViewHolder {
            public TextView textViewPatientName;
            public TextView textViewDoctorName;
            public TextView textViewSpecialty;
            public TextView textViewAppointmentTime;
//            public Button buttonEdit;

            public AppointmentViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewPatientName = itemView.findViewById(R.id.textViewPatientName);
                textViewDoctorName = itemView.findViewById(R.id.textViewDoctorName);
                textViewSpecialty = itemView.findViewById(R.id.textViewSpecialty);
                textViewAppointmentTime = itemView.findViewById(R.id.textViewAppointmentTime);
//                buttonEdit = itemView.findViewById(R.id.buttonEdit);
            }
        }

        private void showEditDialog(int position) {
            Appointment appointment = appointmentList.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Chỉnh sửa lịch hẹn");

            View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_appointment, null);
            builder.setView(dialogView);

            EditText edtPatientName = dialogView.findViewById(R.id.edtPatientName);
            EditText edtDoctorName = dialogView.findViewById(R.id.edtDoctorName);
            EditText edtSpecialty = dialogView.findViewById(R.id.edtSpecialty);
            EditText edtAppointmentTime = dialogView.findViewById(R.id.edtAppointmentTime);

            // Gán dữ liệu cũ
            edtPatientName.setText(appointment.getPatientName());
            edtDoctorName.setText(appointment.getDoctorName());
            edtSpecialty.setText(appointment.getSpecialty());
            edtAppointmentTime.setText(appointment.getAppointmentTime());

            builder.setPositiveButton("Lưu", (dialog, which) -> {
                appointment.setPatientName(edtPatientName.getText().toString().trim());
                appointment.setDoctorName(edtDoctorName.getText().toString().trim());
                appointment.setSpecialty(edtSpecialty.getText().toString().trim());
                appointment.setAppointmentTime(edtAppointmentTime.getText().toString().trim());

                notifyDataSetChanged();
                Toast.makeText(context, "Đã lưu thay đổi!", Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

            builder.show();
        }
    }
}
