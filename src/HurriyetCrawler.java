import com.mongodb.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class HurriyetCrawler extends Crawler {

    private static    String  siteName = "Hurriyet";

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public HurriyetCrawler(String s) {
        super(s);
    }


    public void getLinks() throws Exception {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.hurriyet.com.tr/").get();
        } catch (IOException e) {
            System.out.println("Enter valid url");

        }
        ArrayList<String> linksList = new ArrayList<String>();


        Elements links1 = doc.select("body > main > div > div > div:nth-child(1) > div a");
        Elements links2 = doc.select("body > main > div > div > div:nth-child(4) a");
        Elements links3 = doc.select("body > main > div > div > div:nth-child(5) > div.col-xs-8.col-sm-8.col-md-8.col-lg-8 a");
        extractLinks(links1, linksList);
        extractLinks(links2, linksList);
        extractLinks(links3, linksList);


        newlinks=checkIfLinkIsAlreadyExist(linksList);
        System.out.println("size of new links array is "+ newlinks.size());
        newsList = getNewUsingBoilerPipe(newlinks);


    }
    public ArrayList<String> checkIfLinkIsAlreadyExist(ArrayList<String> linksList ) {

        return super.checkIfLinkIsAlreadyExist(linksList, this.newsCollection, MongoConnection.getDatabase("news"));
    }


    public void storeInDatabase() {
        System.out.println(newsCollection.getNamespace());
        System.out.println(linksCounter.getNamespace());
        storeInMongodb(newlinks, newsList, newsCollection,linksCounter ,siteName );

    }

    @Override
    public void graphs(String webSiteName, DBCollection newsInfo) {
        super.graphs(webSiteName, newsInfo);
    }

    public ArrayList<String> getNewUsingBoilerPipe(ArrayList<String> linksList) throws IOException {

        return super.getNewUsingBoilerPipe(linksList);
    }

    public void plotGraph( ) {
        DBCollection counter =  MongoConnection.getDBCollection(linksNumberDatabaseName, linksCollectionName);
        graphs(siteName, counter);
    }

    private void extractLinks(Elements links, ArrayList<String> linksList) {
        int x = 1;
        for (Element element : links) {
            String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                if (link1.contains("http"))
                    linksList.add("" + link1);
                else
                    linksList.add("http://www.hurriyet.com.tr/" + link1);
            }
        }
    }
}
