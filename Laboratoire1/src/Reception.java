import java.io.IOException;

public class Reception {
    String adresse;
    Crc monCRC;
    int compteur_erreur;
    FileMaker monFichier;
    int current_packet;
    Log monLog;
    static private Reception instance;

    private Reception()
    {

    }

    /**
     * Fonction de Reception permettant d'instancier une seule fois
     * @return Une instance de Reception
     */

    public static Reception getInstance()
    {
        if(instance==null)
        {
            instance= new Reception();
        }
        return instance;
    }

    /**
     * Fonction d'initialisation du CRC, Log et l'adresse de l'ordinateur
     * @param brise Briser les packets selon le nombre indiqué et peut mettre le timeout à 1 seconde si = -1;
     * @throws IOException
     */

    public void initialisation(int brise) throws IOException
    {
        adresse="gegi-lab3041-02";

        monCRC= new Crc(adresse,true,brise);
        monLog=new Log();
        compteur_erreur=0;
        current_packet=1;
    }

    /**
     * Fonction de reception d'un packet du serveur et vérification du CRC
     * @throws TransmissionErrorException
     */

    public void reception() throws TransmissionErrorException {
        while (true) {


                //couche physique
                String packetRecu;
                packetRecu=monCRC.verification();
                //couche liaison
                if (!(packetRecu.substring(15).equals("*"))) {

                    if ((packetRecu.substring(14, 15)).equals("$")) {
                        if (Long.parseLong(packetRecu.substring(0, 4)) == current_packet) {
                            if ((packetRecu.substring(0, 4)).equals("0001")) {
                                monFichier = new FileMaker(packetRecu);
                            } else if ((packetRecu.substring(0, 4)).equals(packetRecu.substring(10, 14))) {
                                monFichier.appendDataInFile(packetRecu);
                                monFichier.fermer();
                                monLog.fermer();
                            } else {
                                monFichier.appendDataInFile(packetRecu);

                            }
                            current_packet++;
                        }
                        String packetenvoye = confirmer(packetRecu);
                        monLog.serverLogging(packetenvoye);


                    }
                } else {
                    try {
                        String packetenvoye = retransmission(packetRecu);
                        monLog.serverLogging(packetenvoye);
                    } catch (TransmissionErrorException e) {
                        System.out.println("connection perdue, 3 erreurs !");
                        monCRC.reinitialisation();
                        try {
                        this.initialisation(0);
                        } catch (Exception exception) {}
                        throw new TransmissionErrorException();
                    }
                    ;
                }


            }
            }

    /**
     * Fonction de confirmation d'un packet reçu
     * @param packetRecu Prends un packet reçu
     * @return Retourne un message
     */
        public String confirmer(String packetRecu)
        {
            //couche transport
            Packet packet= new PacketBuilder(Long.parseLong(packetRecu.substring(0,4)),
                    Long.parseLong(packetRecu.substring(5,9)),
                    Long.parseLong(packetRecu.substring(10,14)),
                    "!","").build();
            String message=packet.toString();
            message=monCRC.ajouterCrc(message);
            return message;
        }

    /**
     * Fonction de retransmission d'un packet si la reception se fait pas
     * @param packetRecu Prends le packet reçu invalide
     * @return Retourne un message
     * @throws TransmissionErrorException
     */
        public String retransmission(String packetRecu) throws TransmissionErrorException
        {
            //couche transport
            Packet packet= new PacketBuilder(Long.parseLong(packetRecu.substring(0,4)),
                    Long.parseLong(packetRecu.substring(5,9)),
                    Long.parseLong(packetRecu.substring(10,14)),"?","").build();
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
