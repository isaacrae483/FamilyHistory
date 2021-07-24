package Data;

import Model.Location;

public class Locations {
    Location[] data;

    public Locations(Location [] d){
        data = d;
    }

    public Location getData(int i) {
        return data[i];
    }
    public int getSize(){
        return data.length;
    }

}
