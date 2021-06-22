import java.util.zip.* ;

public class Crc {
    int packet_transmis;
    Socket monSocket;
    int packet_recu;
    int packet_perdu;
    int packet_erreur;
    int monBrise;

    /**
     * Fonction d'instanciation d'un CRC
     * @param adresse Adresse
     * @param serveur Serveur oui ou non
     * @param brise Briser les packets selon le nombre indiqué et peut mettre le timeout à 1 seconde si = -1;
     */

    Crc(String adresse, boolean serveur, int brise){
        packet_transmis=0;
        packet_erreur=0;
        packet_perdu=0;
        packet_recu=0;
        monBrise=brise;
        monSocket = new Socket();
        if(serveur==false){
        try{
            monSocket.initialisation(adresse, brise);
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

    /**
     * Permet d'encode le header et la data
     * @param packet Prend l'information d'un packet en String
     * @return Retourne le CRC
     */

    public static String encode(String packet) {
        CRC32 monCRC = new CRC32( ) ;
        monCRC.update( packet.getBytes( ) ) ;
        String temp=Long.toHexString( monCRC.getValue( ));
        String padded = "00000000".substring(temp.length()) + temp;
        return padded;
    }

    /**
     * Fonction qui permet l'ajout du CRC au packet
     * @param packet Prend un packet
     * @return Retourne le packet avec le CRC dedans
     */

    public String ajouterCrc(String packet)
    {

        String temp=encode(packet);
        if(monBrise>0)
        {
        temp="00000000";
        monBrise--;
        }
        String tempDebut=packet.substring(0,15);
        String tempFin=packet.substring(15);
        packet=tempDebut+temp+tempFin;
        packet_transmis++;
        try {
            monSocket.envoyer(packet);
        }catch(Exception e){}
        return packet;
    }

    /**
     * Fonction de vérification du CRC d'un packet reçu
     * @return Retourne le packet
     */
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
                return tempDebut+"*";
            }
    }

    /**
     * Fonction qui réinitialise les compteurs à 0;
     */

    public void reinitialisation()
    {
        packet_transmis=0;
        packet_erreur=0;
        packet_perdu=0;
        packet_recu=0;
    }
}
