import java.io.*;
import java.nio.file.*;
import java.text.DecimalFormat;
public class Transmission {
    long premierPacket;
    long dernierPacket;
    long currentPacket;
    long tailleFichier;
    String nomFichier;
    File fichier;
    Socket monSocket;
    Crc monCrc;
    public Transmission()
    {

    }

    public String premier()
    {
        Packet packet= new Packet();
        packet.setId(currentPacket);
        packet.setMin(premierPacket);
        packet.setMax(dernierPacket);
        packet.setType("$");
        packet.setData(nomFichier);
        String message=packet.toString();
        return message;
    }

    public String suivant(String data)
    {
        Packet packet= new Packet();
        packet.setId(currentPacket);
        packet.setMin(premierPacket);
        packet.setMax(dernierPacket);
        packet.setType("$");
        packet.setData(data);
        String message=packet.toString();
        return message;
    }

    public void transmettre()
    {
        //Creation du premier packet

            try {
                while(true) {
                    String tempPacket = premier();
                    tempPacket = monCrc.ajouterCrc(tempPacket);
                    monSocket.envoyer(tempPacket);
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
                tempPacket = monCrc.ajouterCrc(tempPacket);
                monSocket.envoyer(tempPacket);
                if(!(attente(tempPacket)))
                {
                    i=i-200;
                    j=j-200;
                    currentPacket--;
                }
            }
        }catch(Exception e){}
    }

    public void initialisation(String adresse, File monFichier)
    {
        monSocket = new Socket();
        try{
            monSocket.initialisation(adresse);
        }
        catch(Exception e){

        }
            //Creation du Crc
            monCrc = new Crc();


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

    public boolean attente(String packet)
    {
        try {
            while(true) {

                String packetRecu = monSocket.recevoir();

                if (!(monCrc.verification(packetRecu))) {
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

        }catch(Exception e){System.out.println(e); }
        return false;
    }
}
