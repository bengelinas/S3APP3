import java.io.IOException;
import java.net.InetAddress;

public class ServeurTest {
    static Socket monSocket;
    static String adresse;
    static Crc monCRC;
    static int compteur_erreur;

    public static void main(String[] args) throws IOException {
        monSocket = new Socket();
        adresse="gegi-lab3041-02";
        monSocket.initialisationReception(adresse);
        monCRC= new Crc();

        compteur_erreur=0;
        while(true) {
            try {

                //couche physique
                String packetRecu = monSocket.recevoir();

                //couche liaison
                if (monCRC.verification(packetRecu)) {

                    if((packetRecu.substring(14,15)).equals("$"))
                    {

                        String packetenvoye = confirmer(packetRecu);
                        monSocket.envoyer(packetenvoye);
                    }
                } else{
                    try{
                    String packetenvoye = retransmission(packetRecu);
                    monSocket.envoyer(packetenvoye);}
                    catch(TransmissionErrorException e){System.out.println("connection perdue, 3 erreurs !");
                    //reinitialisation de la couche transport avec les compteurs et le log et tout et tout
                    };
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String confirmer(String packetRecu)
    {
        //couche transport
        Packet packet= new Packet();
        packet.setId(Long.parseLong(packetRecu.substring(0,4)));
        packet.setMin(Long.parseLong(packetRecu.substring(5,9)));
        packet.setMax(Long.parseLong(packetRecu.substring(10,14)));
        packet.setType("!");
        packet.setData("");
        String message=packet.toString();
        message=monCRC.ajouterCrc(message);
        return message;
    }
    public static String retransmission(String packetRecu) throws TransmissionErrorException
    {
        //couche transport
        Packet packet= new Packet();
        packet.setId(Long.parseLong(packetRecu.substring(0,4)));
        packet.setMin(Long.parseLong(packetRecu.substring(5,9)));
        packet.setMax(Long.parseLong(packetRecu.substring(10,14)));
        packet.setType("?");
        packet.setData("");
        String message=packet.toString();
        message=monCRC.ajouterCrc(message);
        compteur_erreur++;
        if(compteur_erreur>3)
        {
            throw new TransmissionErrorException();
        }
        return message;
    }
}
