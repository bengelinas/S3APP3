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
    public static Reception getInstance()
    {
        if(instance==null)
        {
            instance= new Reception();
        }
        return instance;
    }
    public void initialisation(int brise) throws IOException
    {
        adresse="gegi-lab3041-02";

        monCRC= new Crc(adresse,true,brise);
        monLog=new Log();
        compteur_erreur=0;
        current_packet=1;
    }
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
