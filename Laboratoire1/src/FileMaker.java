import java.io.FileOutputStream;

public class FileMaker {
    private FileOutputStream writeInFile;
    private String nomFichier;
    private byte[] conversion;


    public FileMaker(String nom) {
        try {
            nomFichier = nom.substring(23);

            writeInFile = new FileOutputStream(nomFichier, true);
        } catch (Exception e) {
        }
    }

    public void appendDataInFile(String Data) {
        try {
            Data= Data.substring(23);
            conversion = Data.getBytes();
            writeInFile.write(conversion);
        } catch (Exception e) {
        }
    }

    public void fermer() {
        try {
            writeInFile.close();
        } catch (Exception e) {
        }
    }

}
