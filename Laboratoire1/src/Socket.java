import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
    public String recevoir() throws IOException
    {
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        monSocket.receive(packet);
        String message = new String(packet.getData(), 0, packet.getLength());
        return message;
    }
}
