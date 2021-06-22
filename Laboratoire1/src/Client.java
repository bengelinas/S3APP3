import java.io.*;

public class Client {
    public static void main(String[] args) throws IOException
    {
        Transmission transmission_1= Transmission.getInstance();
        File monFichier = new File("C:\\Users\\gaup1302\\S3APP3\\Laboratoire1\\src\\one-liners.txt");
        transmission_1.initialisation("gegi-lab3041-02",monFichier,4);
        try {
            transmission_1.transmettre();
        }catch(Exception e){};
    }
}
