package daoimpl;

import daos.DoctorDAO;
import models.Doctor;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class DoctorDAOImpl implements DoctorDAO {

    @Override
    public void addDoctor(Doctor doctor) {

        String sql = "INSERT INTO doctors(first_name,last_name,specialty,phone_number,email) VALUES(?,?,?,?,?)";

        try {

            Connection conn = new DatabaseConnection().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, doctor.getFirstName());
            ps.setString(2, doctor.getLastName());
            ps.setString(3, doctor.getSpecialty());
            ps.setString(4, doctor.getPhoneNumber());
            ps.setString(5, doctor.getEmail());

            ps.executeUpdate();

            System.out.println("Doctor added successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Doctor> getAllDoctors() {

        List<Doctor> doctors = new ArrayList<>();

        String sql = "SELECT * FROM doctors";

        try {

            Connection conn = new DatabaseConnection().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Doctor d = new Doctor(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("specialty"),
                        rs.getString("phone_number"),
                        rs.getString("email")
                );

                d.setId(rs.getInt("id"));
                doctors.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return doctors;
    }
}