import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//*********************************************************************
// peut-etre essayer de faire un echange d'ip et de port avec l'autre
//*********************************************************************

public class Socket {
    DatagramSocket monSocket;
    String adresse;
    public void envoyer(String message) throws IOException
    {
        byte[] buf;
        buf=message.getBytes();
        InetAddress address = InetAddress.getByName(adresse);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 50000);
        monSocket.send(packet);
    }
    public void initialisation(String adresse) throws IOException
    {
        // get a datagram socket
        monSocket = new DatagramSocket();

    }
    public void initialisationReception(String adresse) throws IOException
    {
        // get a datagram socket
        monSocket = new DatagramSocket(50000);

    }
    public String recevoir() throws IOException
    {
        byte[] buf = new byte[225];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        monSocket.receive(packet);
        String message = new String(packet.getData(), 0, packet.getLength());
        return message;
    }
}
