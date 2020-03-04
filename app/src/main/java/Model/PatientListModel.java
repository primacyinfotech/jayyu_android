package Model;

public class PatientListModel {
    private  String patient_name,relation_patient;
    private int patient_id;

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

    public String getRelation_patient() {
        return relation_patient;
    }

    public void setRelation_patient(String relation_patient) {
        this.relation_patient = relation_patient;
    }
}
