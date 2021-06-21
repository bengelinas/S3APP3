import java.util.zip.* ;

public class Crc {
    int packet_transmis;
    int packet_recu;
    int packet_perdu;
    int packet_erreur;
    Crc(){
        packet_transmis=0;
        packet_erreur=0;
        packet_perdu=0;
        packet_recu=0;
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
        return packet;
    }

    public boolean verification(String packet)
    {
        packet_recu++;
        String tempDebut=packet.substring(0,15);

        String tempCRC=packet.substring(15,23);

        String tempData=packet.substring(23);

        String temp=tempDebut+tempData;
        String CRC= encode(temp);
        if(CRC.equals(tempCRC))
        {
            return true;
        }
        else
            {
                packet_erreur++;
                return false;
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
