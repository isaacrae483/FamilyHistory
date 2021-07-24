package Data;

public class Mnames {
    String[] data;

    public Mnames(String[] d){
        data = d;
    }

    public String getData(int i) {
        return data[i];
    }
    public int getSize(){
        return data.length;
    }
}
