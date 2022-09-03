/*
 *
  * This project about web scraping for eight Turkish websites
  *
  *
  * */
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;


public class Main {
    final static Level LOG_LEVEL = Level.ERROR;
    final int period = 10800000; // each three hours  , 3 hours = 10800000 ms

    static String crawler_names[] = {
            CrawlerFactory.MILLIYET,
            CrawlerFactory.SABAH,
            CrawlerFactory.CNN,
            CrawlerFactory.BBC,
            CrawlerFactory.ALJAZEERA,
            CrawlerFactory.ODATV,
            CrawlerFactory.SOZCU,
            CrawlerFactory.HURRIYET

    };
    public static void main(String[] args) throws Exception {



        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(LOG_LEVEL);
        createCrawlers();
    //  Databases dbs = new Databases(); // if we wannna see what is inside the Databases
    //  dbs.showWhatInsideDatabases();
    }

    private static void createCrawlers() throws Exception {
        CrawlerFactory factory = new CrawlerFactory();
        for (String name : crawler_names) {
            System.out.println("\n\n\n"+name+"\n\n\n");
            Crawler c = factory.getCrawler(name);
            if (c != null) {
                c.getLinks();
                c.storeInDatabase();
                c.plotGraph();
            }
        }

    }
}


