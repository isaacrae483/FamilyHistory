package Model;

public class Location {
    String country;
    String city;
    String latitude;
    String longitude;

    public Location(String co, String ci, String la, String lo){
        country = co;
        city = ci;
        latitude = la;
        longitude = lo;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
