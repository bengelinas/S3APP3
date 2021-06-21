import java.util.zip.* ;

public class Crc {
    int packet_transmis;
    Socket monSocket;
    int packet_recu;
    int packet_perdu;
    int packet_erreur;
    Crc(String adresse, boolean serveur){
        packet_transmis=0;
        packet_erreur=0;
        packet_perdu=0;
        packet_recu=0;
        monSocket = new Socket();
        if(serveur==false){
        try{
            monSocket.initialisation(adresse);
            } catch(Exception e) {
            }
        }else
            {
                try{
                    monSocket.initialisationReception(adresse);
                } catch(Exception e) {
                }
            }
    }
    public static String encode(String packet) {
        CRC32 monCRC = new CRC32( ) ;
        monCRC.update( packet.getBytes( ) ) ;
        String temp=Long.toHexString( monCRC.getValue( ));
        String padded = "00000000".substring(temp.length()) + temp;
        return padded;
    }

    public String ajouterCrc(String packet)
    {
        String temp=encode(packet);
        String tempDebut=packet.substring(0,15);
        String tempFin=packet.substring(15);
        packet=tempDebut+temp+tempFin;
        packet_transmis++;
        try {
            monSocket.envoyer(packet);
        }catch(Exception e){}
        return packet;
    }

    public String verification()
    {
        String packet="";
        try {
        packet=monSocket.recevoir();
        }catch(Exception e){}
        packet_recu++;
        String tempDebut=packet.substring(0,15);

        String tempCRC=packet.substring(15,23);

        String tempData=packet.substring(23);

        String temp=tempDebut+tempData;
        String CRC= encode(temp);
        if(CRC.equals(tempCRC))
        {
            return packet;
        }
        else
            {
                packet_erreur++;
                return "";
            }
    }
    public void reinitialisation()
    {
        packet_transmis=0;
        packet_erreur=0;
        packet_perdu=0;
        packet_recu=0;
    }
}
