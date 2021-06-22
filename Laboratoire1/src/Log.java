import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

    public class Log {
        private FileHandler logHandler = null;
        private Logger logFile = Logger.getLogger(Log.class.getName());
        private String toBeInserted;
        private SimpleDateFormat dateFormat;
        private String newDateFormat;

        /**
         *  Instanciation d'un Log file dans un répertoire choisi
         */

        public Log() {
            try {
                logHandler = new FileHandler("C:\\Users\\gaup1302\\S3APP3\\Laboratoire1\\src\\log.txt");
            } catch (Exception e) {
                e.printStackTrace();
            }
            dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            newDateFormat = dateFormat.format(new Date());

            logHandler.setFormatter(new SimpleFormatter());
            logFile.addHandler(logHandler);
        }

        /**
         * Envoi d'un log côté serveur
         * @param toBeInserted Le packet et la date
         */
        public void serverLogging(String toBeInserted){
            logFile.info("Le serveur envoi " + toBeInserted + " " + newDateFormat);
        }

        /**
         * Envoi d'un log côté client
         * @param toBeInserted Le packet et la date
         */
        public void clientLogging(String toBeInserted){
            logFile.info("Le client envoi " + toBeInserted + " " + newDateFormat);
        }

        /**
         * Fonction de fermeture du log
         */
        public void fermer()
        {

            logHandler.close();

        }
    }
