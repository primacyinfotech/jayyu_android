package Model;

public class AccountPresPatientModel {
    String patient_name;
    int prescription_count,patient_id;

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public int getPrescription_count() {
        return prescription_count;
    }

    public void setPrescription_count(int prescription_count) {
        this.prescription_count = prescription_count;
    }
}
