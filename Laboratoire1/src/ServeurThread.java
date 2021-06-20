import java.io.*;
import java.net.*;
public class ServeurThread extends Thread{
    Socket monSocket;
    String adresse;
    Crc monCRC;
    public ServeurThread() throws IOException {
        this("Serveur");
    }

    public ServeurThread(String name) throws IOException {
        super(name);
        monSocket = new Socket();
        adresse="gegi-lab3041-02";
        monSocket.initialisation(adresse);
        monCRC= new Crc();
    }

    public void run() {
        try {
            String packetRecu=monSocket.recevoir();
            System.out.println("merde");
            if(monCRC.verification(packetRecu))
            {
                String packetenvoye=confirmer(packetRecu);
                monSocket.envoyer(packetenvoye);
            }
            else
                {
                    String packetenvoye=retransmission(packetRecu);
                    monSocket.envoyer(packetenvoye);
                }


            // send the response to the client at "address" and "port"

        } catch (IOException e) {
            e.printStackTrace();
        }
        //socket.close();
    }

    public String confirmer(String packetRecu)
    {
        Packet packet= new Packet();
        packet.setId(Long.parseLong(packetRecu.substring(0,3)));
        packet.setMin(Long.parseLong(packetRecu.substring(4,7)));
        packet.setMax(Long.parseLong(packetRecu.substring(8,11)));
        packet.setType("!");
        String message=packet.toString();
        return message;
    }
    public String retransmission(String packetRecu)
    {
        Packet packet= new Packet();
        packet.setId(Long.parseLong(packetRecu.substring(0,3)));
        packet.setMin(Long.parseLong(packetRecu.substring(4,7)));
        packet.setMax(Long.parseLong(packetRecu.substring(8,11)));
        packet.setType("?");
        String message=packet.toString();
        return message;
    }

}
