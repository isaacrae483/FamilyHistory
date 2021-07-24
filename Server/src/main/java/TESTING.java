import java.util.Random;

public class TESTING {

    public static void main(String args[]){
        TESTING test = new TESTING();
        System.out.println(test.createId());
        System.out.println(test.createAuthToken());
    }
    private String createId(){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++){
            sb.append(random.nextInt(9));
        }

        return sb.toString();
    }
    private String createAuthToken(){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++){
            sb.append((char)(random.nextInt(25) + 'a'));
        }

        return sb.toString();
    }
}




/*executeUpdate returns number of rows effected check not 0
update delete insert
 */