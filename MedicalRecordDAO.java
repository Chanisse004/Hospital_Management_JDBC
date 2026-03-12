package daos;

import models.MedicalRecord;
import java.util.List;

public interface MedicalRecordDAO {
    void addMedicalRecord(MedicalRecord record);
    List<MedicalRecord> getMedicalRecordsByPatient(int patientId);
}
