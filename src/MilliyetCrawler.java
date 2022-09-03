import com.mongodb.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.util.ArrayList;

public class MilliyetCrawler extends Crawler {

     final  String  siteName = "Milliyet";

    public MilliyetCrawler(String s) {
        super(s);
    }

    public void getLinks() throws Exception {
        ArrayList<String> linksList;
        linksList = new ArrayList<String>();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.milliyet.com.tr/").get();
        } catch (IOException e) {
            System.out.println("Enter valid url");
        }
        org.jsoup.select.Elements links1 = doc.select(".flashbar a");
        org.jsoup.select.Elements links2 = doc.select(".flashbar1 .top_p1 a");
        org.jsoup.select.Elements links3 = doc.select(".flashbar1 .top_p2 a");
        org.jsoup.select.Elements links4 = doc.select(".flashbar1 .tnw a");
        org.jsoup.select.Elements links5 = doc.select("#mnst11 a");

        extractLinks(links1, linksList);
        extractLinks(links2, linksList);
        extractLinks(links3, linksList);
        extractLinks(links4, linksList);
        extractLinks(links5, linksList);

        for (int i = 0; i < linksList.size(); i++) {
            System.out.println("Links list " + linksList.get(i));
        }

        newlinks = checkIfLinkIsAlreadyExist(linksList);
        newsList = getNewUsingBoilerPipe(newlinks);
    }

    public ArrayList<String> checkIfLinkIsAlreadyExist(ArrayList<String> linksList) {

        return super.checkIfLinkIsAlreadyExist(linksList, this.newsCollection, MongoConnection.getDatabase("news"));
    }

    private void extractLinks(Elements links, ArrayList<String> linksList) {
        for (Element element : links) {
            String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                linksList.add(link1);
            }
        }
    }

    public void storeInDatabase() {
        System.out.println(newsCollection.getNamespace());
        System.out.println(linksCounter.getNamespace());

        storeInMongodb(newlinks, newsList, newsCollection, linksCounter, siteName);

    }

    public ArrayList<String> getNewUsingBoilerPipe(ArrayList<String> linksList) throws IOException {

        return super.getNewUsingBoilerPipe(linksList);
    }

    public void plotGraph() {
        DBCollection counter = MongoConnection.getDBCollection(linksNumberDatabaseName, linksCollectionName);
        graphs(siteName, counter);

    }

    @Override
    public void graphs(String webSiteName, DBCollection newsInfo) {
        super.graphs(webSiteName, newsInfo);
    }

}