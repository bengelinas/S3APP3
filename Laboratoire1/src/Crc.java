import java.util.zip.* ;

public class Crc {
    public static String encode(String packet) {
        CRC32 monCRC = new CRC32( ) ;
        monCRC.update( packet.getBytes( ) ) ;
        return Long.toHexString( monCRC.getValue( ));
    }
    public String ajouterCrc(String packet)
    {
        String temp=encode(packet);
        String tempDebut=packet.substring(0,15);
        String tempFin=packet.substring(15);
        packet=tempDebut+temp+tempFin;
        return packet;
    }
}
