import java.io.FileOutputStream;

public class FileMaker {
    private FileOutputStream writeInFile;
    private String nomFichier;
    private byte[] conversion;


    /**
     * Fonction d'instanciation d'un fichier avec le nom du fichier en paramètre reçu par le premier packet
     * @param nom
     */
    public FileMaker(String nom) {
        try {
            nomFichier = nom.substring(23);

            writeInFile = new FileOutputStream(nomFichier, true);
        } catch (Exception e) {
        }
    }

    /**
     * Fonction d'écriture de la data reçu des autres packets envoyés
     * @param Data
     */
    public void appendDataInFile(String Data) {
        try {
            Data= Data.substring(23);
            conversion = Data.getBytes();
            writeInFile.write(conversion);
        } catch (Exception e) {
        }
    }

    /**
     * Fonction de fermeture du ficher
     */
    public void fermer() {
        try {
            writeInFile.close();
        } catch (Exception e) {
        }
    }

}
