package Model;

public class ReminderTimeModel {
    String time_scheduled,qty_medicine;
    private boolean isSelected;
    public String getTime_scheduled() {
        return time_scheduled;
    }

    public void setTime_scheduled(String time_scheduled) {
        this.time_scheduled = time_scheduled;
    }

    public String getQty_medicine() {
        return qty_medicine;
    }

    public void setQty_medicine(String qty_medicine) {
        this.qty_medicine = qty_medicine;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public boolean getSelected() {
        return isSelected;
    }
}
