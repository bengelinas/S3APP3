import java.io.*;
import java.nio.file.*;

public class Couche_transportClient {
    long premierPacket;
    long dernierPacket;
    long currentPacket;
    long tailleFichier;
    String nomFichier;
    File fichier;
    Couche_liaison monCoucheliaison;
    Log monLog;
    int compteur_erreur;
    static private Couche_transportClient instance;

    private Couche_transportClient()
    {

    }

    /**
     * Fonction de Transmission permettant d'instancier une seule fois
     *
     * @return Une instance de Transmission
     */

    public static Couche_transportClient getInstance()
    {
        if(instance==null)
        {
            instance= new Couche_transportClient();
        }
        return instance;
    }

    /**
     * Fonction permettant de prendre formatter le premier packet
     *
     * @return message étant le premier packet formatter
     */

    public String premier()
    {
        Packet packet= new PacketBuilder(currentPacket,premierPacket,dernierPacket,"$",nomFichier).build();
        String message=packet.toString();
        return message;
    }

    /**
     * Fonction permettant de formatter les packets suivant jusqu'à la fin de la transmission
     * @param data Le data contenu dans le packet
     * @return message étant les autres packet formatter
     */


    public String suivant(String data)
    {
        Packet packet= new PacketBuilder(currentPacket,premierPacket,dernierPacket,"$",data).build();
        String message=packet.toString();
        return message;
    }

    /**
     * Fonction permettant de transmettre les packets
     * Envoie du premier packet séparement des autres
     * Attente des acknowledgements aussi
     * @throws TransmissionErrorException Erreur de transmission
     */

    public void transmettre() throws TransmissionErrorException
    {
        //Creation du premier packet

            try {
                while(true) {
                    String tempPacket = premier();
                    tempPacket = monCoucheliaison.ajouterCrc(tempPacket);
                    monLog.clientLogging(tempPacket);
                    if(attente(tempPacket))
                    {
                        break;
                    }
                }
            } catch (Exception e){
                }



        try {
        //Creation et envoie des packets de donnees
        byte[] dataConversion = Files.readAllBytes(Paths.get(fichier.getAbsolutePath()));
        String dataPacket = new String(dataConversion);
        int i=0;
        int j=200;
        String monData;
        String tempPacket="";
            while(currentPacket<=dernierPacket)
            {
                if(j > dataPacket.length()){
                    int restant = j - dataPacket.length();
                    monData=dataPacket.substring(i,j-restant);
                }
                else{
                    monData=dataPacket.substring(i,j);
                }

                i=i+200;
                j=j+200;
                currentPacket++;

                //Attendre le packet d'acknowledgement
                tempPacket=suivant(monData);
                tempPacket = monCoucheliaison.ajouterCrc(tempPacket);
                monLog.clientLogging(tempPacket);
                if(!(attente(tempPacket)))
                {
                    i=i-200;
                    j=j-200;
                    currentPacket--;
                }
            }
        }catch(Exception e){
        }
        monLog.fermer();

    }

    /**
     * Initialisation du CRC et des paramètres des packets
     * @param adresse Adresse de l'envoi
     * @param monFichier Nom du fichier d'envoi
     * @param brise Briser les packets selon le nombre indiqué et peut mettre le timeout à 1 seconde si = -1;
     */

    public void initialisation(String adresse, File monFichier, int brise)
    {

            //Creation du Crc
        monCoucheliaison = new Couche_liaison(adresse,false,brise);
        monLog = new Log("logClient");

        //initialisation des variables
        String message="";
        fichier=monFichier;
        nomFichier=fichier.getName();
        premierPacket=1;
        currentPacket=premierPacket;
        tailleFichier = fichier.length();
        long totalPacketNumber = (tailleFichier/200)+1;
        dernierPacket=totalPacketNumber+premierPacket;
    }

    /**
     * Fonction permettant l'attente d'un packet de acknowledgement
     * @param packet Retour du packet
     * @return Confirmation d'un packet reçu ou non
     * @throws TransmissionErrorException
     */

    public boolean attente(String packet) throws TransmissionErrorException
    {
        try {
            while(true) {

                String packetRecu= monCoucheliaison.verification();


                if ((packetRecu.substring(15).equals("*"))) {
                    compteur_erreur++;

                    return false;
                }
                if ((packetRecu.substring(0, 14).equals( packet.substring(0, 14)))) {
                    if (packetRecu.substring(14, 15).equals( "!")) {

                        return true;
                    } else if (packetRecu.substring(14, 15).equals( "?")) {

                        return false;
                    }
                    else if(packetRecu.substring(14, 15).equals( "$"))
                    {

                    }
                }
            }

        }catch(Exception e){}
        return false;
    }
}
