import java.io.IOException;

public class ServeurTest {
    public static void main(String[] args) throws IOException {
        Couche_transportServeur couchetransportServeur_1 = Couche_transportServeur.getInstance();
        couchetransportServeur_1.initialisation(0);
        try{
        couchetransportServeur_1.reception();}
        catch(Exception exception){System.out.println(exception);
        };

    }
}

