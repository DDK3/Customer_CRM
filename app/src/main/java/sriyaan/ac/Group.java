package sriyaan.ac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dipesh on 25-Jul-16.
 */
public class Group {

    private String Name;
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private ArrayList<Child> Items;

    public HashMap<String, List<String>> getGetData() {
        return getData;
    }

    public void setGetData(HashMap<String, List<String>> getData) {
        this.getData = getData;
    }

    public HashMap<String, List<String>> getData;


    public ArrayList<Child> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Child> items) {
        Items = items;
    }


}
