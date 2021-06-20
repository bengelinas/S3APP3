import java.io.IOException;

public class ServeurTest {
    static Socket monSocket;
    static String adresse;
    static Crc monCRC;


    public static void main(String[] args) throws IOException {
        monSocket = new Socket();
        adresse="gegi-lab3041-02";
        monSocket.initialisationReception(adresse);
        monCRC= new Crc();
        while(true) {
            try {
                System.out.println("merde");
                String packetRecu = monSocket.recevoir();

                if (monCRC.verification(packetRecu)) {

                    String packetenvoye = confirmer(packetRecu);
                    monSocket.envoyer(packetenvoye);
                } else {

                    String packetenvoye = retransmission(packetRecu);
                    monSocket.envoyer(packetenvoye);
                }


                // send the response to the client at "address" and "port"

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String confirmer(String packetRecu)
    {
        Packet packet= new Packet();
        packet.setId(Long.parseLong(packetRecu.substring(0,4)));
        packet.setMin(Long.parseLong(packetRecu.substring(5,9)));
        packet.setMax(Long.parseLong(packetRecu.substring(10,14)));
        packet.setType("!");
        packet.setData("");
        String message=packet.toString();
        return message;
    }
    public static String retransmission(String packetRecu)
    {
        Packet packet= new Packet();
        packet.setId(Long.parseLong(packetRecu.substring(0,4)));
        packet.setMin(Long.parseLong(packetRecu.substring(5,9)));
        packet.setMax(Long.parseLong(packetRecu.substring(10,14)));
        packet.setType("?");
        packet.setData("");
        String message=packet.toString();
        return message;
    }
}
