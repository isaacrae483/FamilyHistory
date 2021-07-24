package Data;

public class Snames {
    String[] data;

    public Snames(String[] d){
        data = d;
    }

    public String getData(int i) {
        return data[i];
    }
    public int getSize(){
        return data.length;
    }
}
