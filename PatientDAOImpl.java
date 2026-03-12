package daoimpl;


import daos.PatientDAO;
import util.DatabaseConnection;
import models.Patient;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

    public class PatientDAOImpl implements PatientDAO {

        @Override
        public void addPatient(Patient patient) {
            String sql = "INSERT INTO patients(first_name,last_name,date_of_birth,gender,phone_number,email) VALUES(?,?,?,?,?,?)";

            try (Connection conn = new DatabaseConnection().getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, patient.getFirstName());
                ps.setString(2, patient.getLastName());
                ps.setDate(3, (Date) patient.getDateOfBirth());
                ps.setString(4, patient.getGender());
                ps.setString(5, patient.getPhoneNumber());
                ps.setString(6, patient.getEmail());

                ps.executeUpdate();
                System.out.println("Patient added successfully.");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public List<Patient> getAllPatients() {
            List<Patient> patients = new ArrayList<>();
            String sql = "SELECT * FROM patients";

            try (Connection conn = new DatabaseConnection().getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Patient p = new Patient(
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getDate("date_of_birth"),
                            rs.getString("gender"),
                            rs.getString("phone_number"),
                            rs.getString("email")
                    );
                    p.setId(rs.getInt("id"));
                    patients.add(p);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return patients;
        }
    }

