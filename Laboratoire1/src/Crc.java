import java.util.zip.* ;

public class Crc {
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
        return packet;
    }

    public boolean verification(String packet)
    {
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
                return false;
            }
    }
}
