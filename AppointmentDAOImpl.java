package daoimpl;

import daos.AppointmentDAO;
import util.DatabaseConnection;
import models.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAOImpl implements AppointmentDAO {

    @Override
    public void addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments(doctor_id, patient_id, appointment_date, status) VALUES(?,?,?,?)";

        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, appointment.getDoctorId());
            ps.setInt(2, appointment.getPatientId());
            ps.setTimestamp(3, appointment.getAppointmentDate());
            ps.setString(4, appointment.getStatus());

            ps.executeUpdate();
            System.out.println("Appointment added successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatus(int appointmentId, String status) {
        String sql = "UPDATE appointments SET status=? WHERE id=?";

        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, appointmentId);

            ps.executeUpdate();
            System.out.println("Appointment status updated.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE doctor_id=?";

        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment a = new Appointment(
                        rs.getInt("doctor_id"),
                        rs.getInt("patient_id"),
                        rs.getTimestamp("appointment_date"),
                        rs.getString("status")
                );
                a.setId(rs.getInt("id"));
                appointments.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return appointments;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments";

        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Appointment a = new Appointment(
                        rs.getInt("doctor_id"),
                        rs.getInt("patient_id"),
                        rs.getTimestamp("appointment_date"),
                        rs.getString("status")
                );
                a.setId(rs.getInt("id"));
                appointments.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return appointments;
    }
}