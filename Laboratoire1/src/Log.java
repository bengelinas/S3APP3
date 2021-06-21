import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

    public class Log {
        private FileHandler logHandler = null;
        private Logger logFile = Logger.getLogger(Log.class.getName());
        private String toBeInserted;
        private SimpleDateFormat dateFormat;
        private String newDateFormat;

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

        public void serverLogging(String toBeInserted){
            logFile.info("Le serveur envoi " + toBeInserted + " " + newDateFormat);
        }

        public void clientLogging(String toBeInserted){
            logFile.info("Le client envoi " + toBeInserted + " " + newDateFormat);
        }
        public void fermer()
        {

            logHandler.close();

        }
    }
