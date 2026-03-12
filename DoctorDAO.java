package daos;

import models.Doctor;

import java.util.List;

public interface DoctorDAO {
    void addDoctor(Doctor doctor);
    List<Doctor> getAllDoctors();


}
