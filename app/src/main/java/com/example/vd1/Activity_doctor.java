package com.example.vd1;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Activity_doctor extends AppCompatActivity {

    private ListView listDoctors;
    private Button btnAddDoctor, btnBack;
    private static ArrayList<Doctor> doctorList; // CHỈNH: doctorList thành static
    private DoctorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor);

        listDoctors = findViewById(R.id.listDoctors);
        btnAddDoctor = findViewById(R.id.btnAddDoctor);
        btnBack = findViewById(R.id.btnBack);

        if (doctorList == null) {
            doctorList = new ArrayList<>();
            loadSampleDoctors(); // CHỈ load mẫu 1 lần
        }

        adapter = new DoctorAdapter(doctorList);
        listDoctors.setAdapter(adapter);

        btnAddDoctor.setOnClickListener(v -> showAddDoctorDialog());
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadSampleDoctors() {
        doctorList.add(new Doctor("BS001", "Nguyễn Văn A", "Tim mạch", "0909123456"));
        doctorList.add(new Doctor("BS002", "Trần Thị B", "Nội tiết", "0911223344"));
        doctorList.add(new Doctor("BS003", "Lê Văn C", "Tim mạch", "0933445566"));
        doctorList.add(new Doctor("BS004", "Phạm Thị D", "Ngoại khoa", "0944556677"));
        doctorList.add(new Doctor("BS005", "Hoàng Văn E", "Nội tiết", "0955667788"));
        doctorList.add(new Doctor("BS006", "Vũ Thị F", "Da liễu", "0966778899"));
        doctorList.add(new Doctor("BS007", "Đặng Văn G", "Nhi khoa", "0988990011"));
        doctorList.add(new Doctor("BS008", "Cao Thị H", "Răng hàm mặt", "0999001122"));
        doctorList.add(new Doctor("BS009", "Phan Văn I", "Tim mạch", "0912345678"));
        doctorList.add(new Doctor("BS010", "Bùi Thị K", "Nội tiết", "0923456789"));
        doctorList.add(new Doctor("BS011", "Mai Văn L", "Thần kinh", "0934567890"));
        doctorList.add(new Doctor("BS012", "Ngô Thị M", "Ngoại khoa", "0945678901"));
        doctorList.add(new Doctor("BS013", "Đỗ Văn N", "Mắt", "0956789012"));
        doctorList.add(new Doctor("BS014", "Trịnh Thị O", "Tai mũi họng", "0967890123"));
        doctorList.add(new Doctor("BS015", "Hồ Văn P", "Hồi sức cấp cứu", "0978901234"));
        doctorList.add(new Doctor("BS016", "Lâm Thị Q", "Da liễu", "0989012345"));
        doctorList.add(new Doctor("BS017", "Chu Văn R", "Nhi khoa", "0990123456"));
        doctorList.add(new Doctor("BS018", "Võ Thị S", "Răng hàm mặt", "0911234567"));
        doctorList.add(new Doctor("BS019", "Tôn Văn U", "Thần kinh", "0922345678"));
        doctorList.add(new Doctor("BS020", "Nguyễn Văn T", "Hồi sức cấp cứu", "0977112233"));
    }

    private void showAddDoctorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_doctor, null);

        EditText edtId = view.findViewById(R.id.edtDoctorId);
        EditText edtName = view.findViewById(R.id.edtDoctorName);
        EditText edtSpecialty = view.findViewById(R.id.edtSpecialty);
        EditText edtPhone = view.findViewById(R.id.edtDoctorPhone);
        Button btnSave = view.findViewById(R.id.btnSaveDoctor);
        Button btnCancel = view.findViewById(R.id.btnCancelDoctor);

        AlertDialog dialog = builder.setView(view).create();

        btnSave.setOnClickListener(v -> {
            String id = edtId.getText().toString().trim();
            String name = edtName.getText().toString().trim();
            String specialty = edtSpecialty.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();

            if (id.isEmpty() || name.isEmpty() || specialty.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            for (Doctor d : doctorList) {
                if (d.id.equalsIgnoreCase(id)) {
                    Toast.makeText(this, "Bác sĩ đã tồn tại!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            doctorList.add(new Doctor(id, name, specialty, phone));
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Đã thêm bác sĩ", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void showEditDoctorDialog(int position) {
        Doctor doctor = doctorList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_doctor, null);

        EditText edtId = view.findViewById(R.id.edtDoctorId);
        EditText edtName = view.findViewById(R.id.edtDoctorName);
        EditText edtSpecialty = view.findViewById(R.id.edtSpecialty);
        EditText edtPhone = view.findViewById(R.id.edtDoctorPhone);
        Button btnSave = view.findViewById(R.id.btnSaveDoctor);
        Button btnCancel = view.findViewById(R.id.btnCancelDoctor);

        edtId.setText(doctor.id);
        edtId.setEnabled(false);
        edtName.setText(doctor.name);
        edtSpecialty.setText(doctor.specialty);
        edtPhone.setText(doctor.phone);

        AlertDialog dialog = builder.setView(view).create();

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String specialty = edtSpecialty.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();

            if (name.isEmpty() || specialty.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            doctor.name = name;
            doctor.specialty = specialty;
            doctor.phone = phone;
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public static class Doctor {
        String id, name, specialty, phone;

        public Doctor(String id, String name, String specialty, String phone) {
            this.id = id;
            this.name = name;
            this.specialty = specialty;
            this.phone = phone;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getSpecialty() { return specialty; }
        public String getPhone() { return phone; }
    }

    public static ArrayList<Doctor> getDoctorList() {
        return new ArrayList<>(doctorList); // TRẢ VỀ bản copy để tránh sửa trực tiếp
    }

    class DoctorAdapter extends BaseAdapter {

        private final ArrayList<Doctor> doctors;

        public DoctorAdapter(ArrayList<Doctor> doctors) {
            this.doctors = doctors;
        }

        @Override
        public int getCount() {
            return doctors.size();
        }

        @Override
        public Object getItem(int position) {
            return doctors.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(Activity_doctor.this)
                        .inflate(R.layout.item_doctor, parent, false);
            }

            TextView tvInfo = convertView.findViewById(R.id.tvDoctorInfo);
            TextView tvDetails = convertView.findViewById(R.id.tvDoctorDetails);
            Button btnEdit = convertView.findViewById(R.id.btnEditDoctor);
            Button btnDelete = convertView.findViewById(R.id.btnDeleteDoctor);

            Doctor doctor = doctors.get(position);

            tvInfo.setText(doctor.name + " - " + doctor.specialty);
            tvDetails.setText("Mã: " + doctor.id + " | SĐT: " + doctor.phone);

            btnEdit.setOnClickListener(v -> showEditDoctorDialog(position));
            btnDelete.setOnClickListener(v -> {
                doctors.remove(position);
                notifyDataSetChanged();
                Toast.makeText(Activity_doctor.this, "Đã xoá bác sĩ", Toast.LENGTH_SHORT).show();
            });

            return convertView;
        }
    }
}
