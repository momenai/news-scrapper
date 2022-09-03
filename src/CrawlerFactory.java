public class CrawlerFactory {
    final static String MILLIYET = "MILLIYET";
    final static String HURRIYET = "HURRIYET";
    final static String SABAH = "SABAH";
    final static String SOZCU = "SOZCU";
    final static String CNN = "CNN";
    final static String BBC = "BBC";
    final static String ODATV = "ODATV";
    final static String ALJAZEERA = "ALJAZEERA";


    final static String SABAH_COLL_NAME = "sabahCounter";
    final static String MILLIYET_COLL_NAME = "milliyetCounter";
    final static String HURRIYET_COLL_NAME = "hurriyetCounter";
    final static String CNN_COLL_NAME = "CNNCounter";
    final static String BBC_COLL_NAME = "BBCCounter";
    final static String ALJAZEERA_COLL_NAME = "aljazeeraCounter";
    final static String ODATV_COLL_NAME = "odatvCounter";
    final static String SOZCU_COLL_NAME = "milliyetCounter";

    public Crawler getCrawler(String name) {
        if (name.equals(MILLIYET)) {
            return new MilliyetCrawler(MILLIYET_COLL_NAME);

        } else if (name.equals(HURRIYET)) {
            return new HurriyetCrawler(HURRIYET_COLL_NAME);

        } else if (name.equals(SABAH)) {
            return new SabahCrawler(SABAH_COLL_NAME);

        } else if (name.equals(BBC)) {
            return new BbcCrwler(BBC_COLL_NAME);

        } else if (name.equals(CNN)) {
            return new CnnCrawler(CNN_COLL_NAME);

        }else if (name.equals(SOZCU)) {
            return new SozcuCrawler(SOZCU_COLL_NAME);

        }else if (name.equals(ODATV)) {
            return new OdatvCrawler(ODATV_COLL_NAME);

        }else if (name.equals(ALJAZEERA)) {
            return new AljazeeraCrawler(ALJAZEERA_COLL_NAME);
        }
        return null;
    }
}
