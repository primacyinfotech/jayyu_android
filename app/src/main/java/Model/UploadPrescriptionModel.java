package Model;

public class UploadPrescriptionModel {
    String report_name,uploaded_date;
    int upload_image;

    public UploadPrescriptionModel(String report_name, String uploaded_date, int upload_image) {
        this.report_name = report_name;
        this.uploaded_date = uploaded_date;
        this.upload_image = upload_image;
    }

    public String getReport_name() {
        return report_name;
    }

    public void setReport_name(String report_name) {
        this.report_name = report_name;
    }

    public String getUploaded_date() {
        return uploaded_date;
    }

    public void setUploaded_date(String uploaded_date) {
        this.uploaded_date = uploaded_date;
    }

    public int getUpload_image() {
        return upload_image;
    }

    public void setUpload_image(int upload_image) {
        this.upload_image = upload_image;
    }
}
