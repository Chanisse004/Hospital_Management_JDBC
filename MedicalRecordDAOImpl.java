package daoimpl;

import daos.MedicalRecordDAO;
import util.DatabaseConnection;
import models.MedicalRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDAOImpl implements MedicalRecordDAO {

    @Override
    public void addMedicalRecord(MedicalRecord record) {
        String sql = "INSERT INTO medical_records(patient_id, diagnosis, treatment, doctor_id) VALUES(?,?,?,?)";

        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, record.getPatientId());
            ps.setString(2, record.getDiagnosis());
            ps.setString(3, record.getTreatment());
            ps.setInt(4, record.getDoctorId());

            ps.executeUpdate();
            System.out.println("Medical record added successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MedicalRecord> getMedicalRecordsByPatient(int patientId) {
        List<MedicalRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM medical_records WHERE patient_id=?";

        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MedicalRecord mr = new MedicalRecord(
                        rs.getInt("patient_id"),
                        rs.getString("diagnosis"),
                        rs.getString("treatment"),
                        rs.getInt("doctor_id")
                );
                mr.setId(rs.getInt("id"));
                records.add(mr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return records;
    }
}