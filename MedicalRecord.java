package models;

import java.sql.Timestamp;

    public class MedicalRecord {
        private int id;
        private int patientId;
        private String diagnosis;
        private String treatment;
        private int doctorId;

        public MedicalRecord() {}

        public MedicalRecord(int patientId, String diagnosis, String treatment, int doctorId) {
            this.patientId = patientId;
            this.diagnosis = diagnosis;
            this.treatment = treatment;
            this.doctorId = doctorId;
        }


        public int getId() {
            return id; }
        public void setId(int id) {
            this.id = id; }
        public int getPatientId() {
            return patientId; }
        public String getDiagnosis() {
            return diagnosis; }
        public String getTreatment() {
            return treatment; }
        public int getDoctorId() {
            return doctorId; }
    }

