package Model;

import java.util.Collection;

public class Searchmodel {
    int search_id;
    String Search_item,composition_name;
    int arrow_img;


   /* public Searchmodel(String search_item, int arrow_img) {
        Search_item = search_item;
        this.arrow_img = arrow_img;
    }*/

    public String getComposition_name() {
        return composition_name;
    }

    public void setComposition_name(String composition_name) {
        this.composition_name = composition_name;
    }

    public String getSearch_item() {
        return Search_item;
    }

    public void setSearch_item(String search_item) {
        Search_item = search_item;
    }

    public int getArrow_img() {
        return arrow_img;
    }

    public void setArrow_img(int arrow_img) {
        this.arrow_img = arrow_img;
    }

    public int getSearch_id() {
        return search_id;
    }

    public void setSearch_id(int search_id) {
        this.search_id = search_id;
    }
}
