package Data;

public class Fnames {
    String[] data;

    public Fnames(String[] d){
        data = d;
    }

    public String getData(int i) {
        return data[i];
    }
    public int getSize(){
        return data.length;
    }
}
