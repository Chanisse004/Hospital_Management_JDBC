import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.Statement;

public class CreateTables {

    public static void main(String[] args){
        DatabaseConnection db = new DatabaseConnection();
        Connection conn = db.getConnection();

        try{
            Statement stmt = conn.createStatement();

            String doctors = "CREATE TABLE IF NOT EXISTS doctors (" +
                    "id SERIAL primary key," +
                    "first_name VARCHAR(50)," +
                    "last_name VARCHAR(50)," +
                    "specialty VARCHAR(50)," +
                    "phone_number VARCHAR(20)," +
                    "email VARCHAR(100) UNIQUE," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            stmt.executeUpdate(doctors);
            System.out.println("Doctors table created successfully!");

            String patients = "CREATE TABLE IF NOT EXISTS patients ("+
                    "id SERIAL primary key," +
                    "first_name VARCHAR(50)," +
                    "last_name VARCHAR(50)," +
                    "date_of_birth DATE," +
                    "gender VARCHAR(10)," +
                    "phone_number VARCHAR(20)," +
                    "email VARCHAR(100) UNIQUE," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            stmt.executeUpdate(patients);
            System.out.println("Patients table created successfully!");

            String appointments = "CREATE TABLE IF NOT EXISTS appointments (" +
                    "id SERIAL primary key," +
                    "doctor_id INT," +
                    "patient_id INT," +
                    "appointment_date TIMESTAMP," +
                    "status VARCHAR(20)," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE" +
                    ")";
            stmt.executeUpdate(appointments);
            System.out.println("Appointments table created successfully!");

            String medicalRecords = "CREATE TABLE IF NOT EXISTS medical_records (" +
                    "id SERIAL primary key," +
                    "patient_id INT," +
                    "diagnosis TEXT," +
                    "treatment TEXT," +
                    "doctor_id INT," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (doctor_id) REFERENCES doctors(id)" +
                    ")";
            stmt.executeUpdate(medicalRecords);
            System.out.println("Medical Records table created successfully!");

            String doctorPatient = "CREATE TABLE IF NOT EXISTS doctor_patient (" +
                    "doctor_id INT," +
                    "patient_id INT," +
                    "PRIMARY KEY (doctor_id, patient_id)," +
                    "FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE," +
                    "FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE" +
                    ")";
            stmt.executeUpdate(doctorPatient);
            System.out.println("Doctor_Patient table created successfully!");

            String index = "CREATE INDEX IF NOT EXISTS idx_appointment_date ON appointments(appointment_date)";
            stmt.executeUpdate(index);
            System.out.println("index created successfully!");








        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
