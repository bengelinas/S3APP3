import java.io.IOException;
import java.net.InetAddress;

public class ServeurTest {
    public static void main(String[] args) throws IOException {
        Reception reception_1= Reception.getInstance();
        reception_1.initialisation(1);
        try{
        reception_1.reception();}
        catch(Exception exception){System.out.println(exception);
        };

    }
}

