package com.example.vd1;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Activity_patient extends AppCompatActivity {

    private ListView listPatients;
    private Button btnAddPatient, btnBack;
    public static ArrayList<Patient> patientList = new ArrayList<>();
    private PatientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient);

        listPatients = findViewById(R.id.listPatients);
        btnAddPatient = findViewById(R.id.btnAddPatient);
        btnBack = findViewById(R.id.btnBack);

        adapter = new PatientAdapter();
        listPatients.setAdapter(adapter);

        btnBack.setOnClickListener(view -> finish());

        btnAddPatient.setOnClickListener(view -> showAddPatientDialog());

        listPatients.setOnItemClickListener((parent, view, position, id) -> showPatientOptionsDialog(position));
    }

    private void showAddPatientDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_add_patient, null);
        EditText edtId = view.findViewById(R.id.edtPatientId);
        EditText edtName = view.findViewById(R.id.edtPatientName);
        EditText edtPhone = view.findViewById(R.id.edtPatientPhone);
        EditText edtAddress = view.findViewById(R.id.edtPatientAddress);
        Button btnSave = view.findViewById(R.id.btnSavePatient);
        Button btnCancel = view.findViewById(R.id.btnCancelPatient);

        AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        btnSave.setOnClickListener(v -> {
            String id = edtId.getText().toString().trim();
            String name = edtName.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();

            if (id.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            for (Patient p : patientList) {
                if (p.id.equalsIgnoreCase(id)) {
                    Toast.makeText(this, "Bệnh nhân đã tồn tại!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            patientList.add(new Patient(id, name, phone, address, ""));
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Đã thêm bệnh nhân mới", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void showEditPatientDialog(int position) {
        Patient patient = patientList.get(position);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_patient, null);

        EditText edtId = view.findViewById(R.id.edtPatientId);
        EditText edtName = view.findViewById(R.id.edtPatientName);
        EditText edtPhone = view.findViewById(R.id.edtPatientPhone);
        EditText edtAddress = view.findViewById(R.id.edtPatientAddress);
        Button btnSave = view.findViewById(R.id.btnSavePatient);
        Button btnCancel = view.findViewById(R.id.btnCancelPatient);

        edtId.setText(patient.id);
        edtId.setEnabled(false);
        edtName.setText(patient.name);
        edtPhone.setText(patient.phone);
        edtAddress.setText(patient.address);

        AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            patient.name = name;
            patient.phone = phone;
            patient.address = address;
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void showPatientOptionsDialog(int position) {
        Patient patient = patientList.get(position);

        new AlertDialog.Builder(this)
                .setTitle("Thông tin bệnh nhân")
                .setMessage("ID: " + patient.id + "\nTên: " + patient.name + "\nSĐT: " + patient.phone + "\nĐịa chỉ: " + patient.address)
                .setPositiveButton("Chỉnh sửa", (dialog, which) -> showEditPatientDialog(position))
                .setNegativeButton("Xoá", (dialog, which) -> {
                    patientList.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Đã xoá bệnh nhân", Toast.LENGTH_SHORT).show();
                })
                .setNeutralButton("Đóng", null)
                .show();
    }

    // ✅ Model bệnh nhân (public static để dùng chung)
    public static class Patient {
        public String id, name, phone, address, password;

        public Patient(String id, String name, String phone, String address, String password) {
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.address = address;
            this.password = password;
        }
    }

    // ✅ Adapter hiển thị danh sách bệnh nhân
    class PatientAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return patientList.size();
        }

        @Override
        public Object getItem(int position) {
            return patientList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(Activity_patient.this).inflate(R.layout.item_patient, parent, false);
            }

            TextView tvId = convertView.findViewById(R.id.tvPatientId);
            TextView tvName = convertView.findViewById(R.id.tvPatientName);
            TextView tvPhone = convertView.findViewById(R.id.tvPatientPhone);
            TextView tvAddress = convertView.findViewById(R.id.tvPatientAddress);

            Patient patient = patientList.get(i);

            tvId.setText("ID: " + patient.id);
            tvName.setText("Tên: " + patient.name);
            tvPhone.setText("SĐT: " + patient.phone);
            tvAddress.setText("Địa chỉ: " + patient.address);

            return convertView;
        }
    }
}
