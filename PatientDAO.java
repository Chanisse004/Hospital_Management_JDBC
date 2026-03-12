package daos;

import models.Patient;

import java.util.List;

public interface PatientDAO {
    void addPatient(Patient patient);
    List<Patient> getAllPatients();
}
