import java.io.*;

public class Client {
    public static void main(String[] args) throws IOException
    {
        Couche_transportClient couchetransportClient_1 = Couche_transportClient.getInstance();
        File monFichier = new File("C:\\Users\\benge\\OneDrive\\Documents\\Repo\\S3APP3\\Laboratoire1\\src\\one-liners.txt");
        couchetransportClient_1.initialisation("LAPTOP-JGM2CQ2H",monFichier,0);
        try {
            couchetransportClient_1.transmettre();
        }catch(Exception e){};
    }
}
