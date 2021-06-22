import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

//*********************************************************************
// peut-etre essayer de faire un echange d'ip et de port avec l'autre
//*********************************************************************

public class Couche_physique {
    DatagramSocket monSocket;
    InetAddress ip;
    int port;

    /**
     * Envoi d'un packet à travers un socket
     * @param message Message dans le packet pour l'envoi
     * @throws IOException
     */

    public void envoyer(String message) throws IOException
    {
        byte[] buf;
        buf=message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, port);
        monSocket.send(packet);
    }

    /**
     * Initialisation d'un socket pour la communication transmission client
     * @param adresse Adresse INet Client
     * @param brise Brise la communication ou non 0 ou 1;
     * @throws IOException
     */

    public void initialisation(String adresse, int brise) throws IOException
    {
        // get a datagram socket
        monSocket = new DatagramSocket();
        if (brise==-1)
        {
            monSocket.setSoTimeout(1);
        }
        else
            {
                monSocket.setSoTimeout(10000);
            }

        ip=InetAddress.getByName(adresse);
        port=50000;

    }

    /**
     * Initialisation de la communication reception serveur
     * @param adresse Adresse INet Serveur
     * @throws IOException
     */

    public void initialisationReception(String adresse) throws IOException
    {
        // get a datagram socket
        monSocket = new DatagramSocket(50000);

    }

    /**
     * Reception d'un packet et renvoie de la confirmation
     * @return Retourne un message contenu dans le packet
     * @throws IOException
     */

    public String recevoir() throws IOException
    {
        byte[] buf = new byte[225];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        String message;
        try {
            monSocket.receive(packet);
            ip = packet.getAddress();
            port = packet.getPort();
            message = new String(packet.getData(), 0, packet.getLength());
        }catch(SocketTimeoutException e){
            message = "0000-0000-0000$00000000";
            System.out.println("TIMEOUT !");
        }


        return message;
    }
}
