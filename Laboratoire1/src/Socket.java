import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//*********************************************************************
// peut-etre essayer de faire un echange d'ip et de port avec l'autre
//*********************************************************************

public class Socket {
    DatagramSocket monSocket;
    InetAddress ip;
    int port;
    public void envoyer(String message) throws IOException
    {
        byte[] buf;
        buf=message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, port);
        monSocket.send(packet);
    }
    public void initialisation(String adresse) throws IOException
    {
        // get a datagram socket
        monSocket = new DatagramSocket();
        ip=InetAddress.getByName(adresse);
        port=50000;

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
        ip=packet.getAddress();
        port= packet.getPort();
        String message = new String(packet.getData(), 0, packet.getLength());
        return message;
    }
}
