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
        try{
            String tempPacket= premier();
            tempPacket = monCrc.ajouterCrc(tempPacket);
            monSocket.envoyer(tempPacket);
        }catch(Exception e){}

        //Attendre le packet d'acknowledgement
        try {
        //Creation et envoie des packets de donnees
        byte[] dataConversion = Files.readAllBytes(Paths.get(fichier.getAbsolutePath()));
        String dataPacket = new String(dataConversion);
        int i=0;
        int j=200;
        String monData;
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
                String tempPacket=suivant(monData);
                tempPacket = monCrc.ajouterCrc(tempPacket);
                monSocket.envoyer(tempPacket);
            }
        }catch(Exception e){}
    }

    public void initialisation(String adresse, File monFichier)
    {
        monSocket = new Socket();
        try{
            monSocket.initialisation(adresse);}
        catch(Exception e){

        }
        //Creation du Crc
        monCrc= new Crc();


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
}
