import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class OdatvCrawler extends Crawler {
    private static    String  siteName = "Odatv";

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public OdatvCrawler(String s) {
        super(s);

    }


    public void getLinks() throws Exception {
        ArrayList<String> linksList = new ArrayList<String>();
        newsList = new ArrayList<String>();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://odatv.com/tops.php").get();
        } catch (IOException e) {
            System.out.println("Enter valid url");

        }


        Elements links1 = doc.select("#boxed-wrap > section.container.page-content > div:nth-child(2) a");
        extractLinks(links1, linksList);

        newlinks=checkIfLinkIsAlreadyExist(linksList);
        System.out.println("size of new links array is "+ newlinks.size());
        newsList = getNewUsingBoilerPipe(newlinks);
     }
    private void extractLinks(Elements links, ArrayList<String> linksList){
        for (Element element : links) {

            String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                if(! link1.contains("http"))
                    linksList.add("http://odatv.com/"+link1);
                else
                    linksList.add(""+link1);
            }
        }
    }
    public ArrayList<String> checkIfLinkIsAlreadyExist(ArrayList<String> linksList ) {

        return super.checkIfLinkIsAlreadyExist(linksList, this.newsCollection, MongoConnection.getDatabase("news"));
    }


    public void storeInDatabase() {
        System.out.println(newsCollection.getNamespace());
        System.out.println(linksCounter.getNamespace());
        storeInMongodb(newlinks, newsList, newsCollection,linksCounter ,siteName );

    }

    public ArrayList<String> getNewUsingBoilerPipe(ArrayList<String> linksList) throws IOException {

        return super.getNewUsingBoilerPipe(linksList);
    }

    public void plotGraph( ) {

        DBCollection counter =  MongoConnection.getDBCollection(linksNumberDatabaseName, linksCollectionName);
        graphs(siteName, counter);
    }

    @Override
    public void graphs(String webSiteName, DBCollection newsInfo) {
        super.graphs(webSiteName, newsInfo);
    }

}