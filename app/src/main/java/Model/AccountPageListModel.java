package Model;

public class AccountPageListModel {
    int icon_view;
    String ac_list;

    public AccountPageListModel(int icon_view, String ac_list) {
        this.icon_view = icon_view;
        this.ac_list = ac_list;
    }

    public int getIcon_view() {
        return icon_view;
    }

    public void setIcon_view(int icon_view) {
        this.icon_view = icon_view;
    }

    public String getAc_list() {
        return ac_list;
    }

    public void setAc_list(String ac_list) {
        this.ac_list = ac_list;
    }


}
