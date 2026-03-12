import daoimpl.AppointmentDAOImpl;
import daoimpl.DoctorDAOImpl;
import daoimpl.MedicalRecordDAOImpl;
import daoimpl.PatientDAOImpl;
import models.Appointment;
import models.Doctor;
import models.MedicalRecord;
import models.Patient;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class Main {

    public static void main(String[] args) {

        try {

            DatabaseConnection db = new DatabaseConnection();
            Connection conn = db.getConnection();


            DoctorDAOImpl doctorDAO = new DoctorDAOImpl();
            PatientDAOImpl patientDAO = new PatientDAOImpl();
            AppointmentDAOImpl appointmentDAO = new AppointmentDAOImpl();
            MedicalRecordDAOImpl medicalRecordDAO = new MedicalRecordDAOImpl();


            doctorDAO.addDoctor(new Doctor("John", "Smith", "Cardiology", "0781111111", "john3@hospital.com"));
            doctorDAO.addDoctor(new Doctor("Sarah", "Brown", "Pediatrics", "0782222222", "sarah3@hospital.com"));


            patientDAO.addPatient(new Patient("Alice", "Walker", Date.valueOf("2000-05-10"), "Female", "0783333333", "alice3@email.com"));
            patientDAO.addPatient(new Patient("David", "Mugisha", Date.valueOf("1998-08-21"), "Male", "0784444444", "david3@email.com"));
            patientDAO.addPatient(new Patient("Emma", "Davis", Date.valueOf("2002-07-15"), "Female", "0785555555", "emma3@email.com"));

            System.out.println("\nSample data inserted successfully.\n");


            appointmentDAO.addAppointment(new Appointment(1, 1, Timestamp.valueOf("2026-03-20 10:00:00"), "Scheduled"));
            appointmentDAO.addAppointment(new Appointment(2, 2, Timestamp.valueOf("2026-03-21 11:00:00"), "Scheduled"));
            appointmentDAO.addAppointment(new Appointment(1, 3, Timestamp.valueOf("2026-03-22 12:00:00"), "Scheduled"));


            medicalRecordDAO.addMedicalRecord(new MedicalRecord(1, "Malaria", "Medication", 1));
            medicalRecordDAO.addMedicalRecord(new MedicalRecord(2, "Flu", "Rest and Medicine", 2));
            medicalRecordDAO.addMedicalRecord(new MedicalRecord(1, "Cold", "Medication", 1));

            System.out.println("\nAppointments & Medical Records inserted successfully.\n");


            System.out.println("Patients with Doctor ID = 1:");
            String q1 = "SELECT p.first_name, p.last_name FROM patients p " +
                    "JOIN appointments a ON p.id = a.patient_id WHERE a.doctor_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(q1);
            ps1.setInt(1, 1);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                System.out.println(rs1.getString("first_name") + " " + rs1.getString("last_name"));
            }


            System.out.println("\nMedical Records for Patient ID = 1:");
            String q2 = "SELECT diagnosis, treatment FROM medical_records WHERE patient_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(q2);
            ps2.setInt(1, 1);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                System.out.println("Diagnosis: " + rs2.getString("diagnosis") +
                        " | Treatment: " + rs2.getString("treatment"));
            }


            System.out.println("\nTotal Appointments per Doctor:");
            String q3 = "SELECT doctor_id, COUNT(*) AS total FROM appointments GROUP BY doctor_id";
            ResultSet rs3 = conn.createStatement().executeQuery(q3);
            while (rs3.next()) {
                System.out.println("Doctor ID: " + rs3.getInt("doctor_id") +
                        " | Appointments: " + rs3.getInt("total"));
            }


            String q4 = "UPDATE appointments SET status=? WHERE id=?";
            PreparedStatement ps4 = conn.prepareStatement(q4);
            ps4.setString(1, "Completed");
            ps4.setInt(2, 1);
            ps4.executeUpdate();
            System.out.println("\nAppointment status updated successfully.");


            String q5 = "DELETE FROM patients WHERE id=?";
            PreparedStatement ps5 = conn.prepareStatement(q5);
            ps5.setInt(1, 2);
            ps5.executeUpdate();
            System.out.println("Patient deleted successfully.");


            System.out.println("\nDoctors with more than 1 patient:");
            String q6 = "SELECT doctor_id, COUNT(patient_id) AS total FROM appointments " +
                    "GROUP BY doctor_id HAVING COUNT(patient_id) > 1";
            ResultSet rs6 = conn.createStatement().executeQuery(q6);
            while (rs6.next()) {
                System.out.println("Doctor ID: " + rs6.getInt("doctor_id") +
                        " | Patients: " + rs6.getInt("total"));
            }


            System.out.println("\nPatients diagnosed more than once:");
            String q7 = "SELECT patient_id, COUNT(*) AS diagnoses FROM medical_records " +
                    "GROUP BY patient_id HAVING COUNT(*) > 1";
            ResultSet rs7 = conn.createStatement().executeQuery(q7);
            while (rs7.next()) {
                System.out.println("Patient ID: " + rs7.getInt("patient_id") +
                        " | Diagnoses: " + rs7.getInt("diagnoses"));
            }


            System.out.println("\nAppointments per Month:");
            String q8 = "SELECT EXTRACT(MONTH FROM appointment_date) AS month, COUNT(*) AS total " +
                    "FROM appointments GROUP BY month";
            ResultSet rs8 = conn.createStatement().executeQuery(q8);
            while (rs8.next()) {
                System.out.println("Month: " + rs8.getInt("month") +
                        " | Appointments: " + rs8.getInt("total"));
            }

            System.out.println("\nAll operations completed successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}