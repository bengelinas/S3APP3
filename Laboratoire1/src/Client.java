import java.io.*;

public class Client {
    public static void main(String[] args) throws IOException
    {
        Transmission transmission_1= Transmission.getInstance();
        File monFichier = new File("C:\\Users\\benge\\OneDrive\\Documents\\Repo\\S3APP3\\Laboratoire1\\src\\one-liners.txt");
        transmission_1.initialisation("LAPTOP-JGM2CQ2H",monFichier,0);
        try {
            transmission_1.transmettre();
        }catch(Exception e){};
    }
}
