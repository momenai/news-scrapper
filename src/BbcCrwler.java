import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class BbcCrwler extends Crawler {
    private final   String  siteName = "BBC";

    protected BbcCrwler(String s) {

    super(s);

    }

    public void getLinks() throws Exception {

        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.bbc.com/turkce").get();
        } catch (IOException e) {
            System.out.println("Enter valid url");
        }
        ArrayList<String> linksList = new ArrayList<String>();

        Elements links1 = doc.select("#comp-top-story-1 > div > div a");
        Elements links2 = doc.select("#comp-top-story-2 > div a");
        Elements links3 = doc.select("#comp-top-story-3 > div > div a");

        extractLinks(links1, linksList);
        extractLinks(links2, linksList);
        extractLinks(links3, linksList);
        ArrayList<String> newLinksList =new ArrayList<String>();

        System.out.println("size of new links array is "+ linksList.size());

        for (int i = 0; i < linksList.size(); i++) {
            if (!linksList.get(i).contains("http"))
                newLinksList.add("http://www.bbc.com/" + linksList.get(i)); // to add the main link for the sub_link
            else
                newLinksList.add(linksList.get(i));
        }
        for (String S : newLinksList )
            System.out.println(S);
        newlinks=checkIfLinkIsAlreadyExist(newLinksList);
       newsList = getNewUsingBoilerPipe(newlinks);


    }

    public ArrayList<String> checkIfLinkIsAlreadyExist(ArrayList<String> linksList ) {

        return super.checkIfLinkIsAlreadyExist(linksList, this.newsCollection,MongoConnection.getDatabase("news"));
    }


    private void extractLinks(Elements links, ArrayList<String> linksList) {
        for (Element element : links) {
            String link1 = element.attr("href");
            if (!linksList.contains(link1)) {
                linksList.add("" + link1);
            }
        }
    }

    public void storeInDatabase() {
        System.out.println(newsCollection.getNamespace());
        System.out.println(linksCounter.getNamespace());
        for (int i = 0; i <newlinks.size() ; i++) {
            System.out.println(newlinks.get(i));
        }
       storeInMongodb(newlinks, newsList, newsCollection,linksCounter ,siteName );

    }

    @Override
    public void storeInMongodb(ArrayList<String> linksList, ArrayList<String> newsList, MongoCollection<org.bson.Document> collection,
                               MongoCollection<org.bson.Document> collection2, String webSiteName) {
        super.storeInMongodb(linksList, newsList, collection, collection2, webSiteName);
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
