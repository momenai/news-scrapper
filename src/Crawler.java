import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;
import org.bson.Document;
import org.jfree.ui.RefineryUtilities;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public abstract class Crawler {
    protected  String linksNumberDatabaseName = "linksCounter1" ;
    protected  String linksCollectionName;
    protected ArrayList<String> newsList;
    protected ArrayList<String> newlinks;
    protected MongoCollection<org.bson.Document> newsCollection;
    protected MongoCollection<org.bson.Document> linksCounter;
    private static String dbName = "news";



    public Crawler(String linksCollectionName) {
        this.linksCollectionName = linksCollectionName;
        newsCollection = MongoConnection.getCollection(dbName);
        linksCounter = MongoConnection
                .getDatabase(linksNumberDatabaseName)
                .getCollection(linksCollectionName);
        newsList = new ArrayList<String>();
        newlinks = new ArrayList<String>();
    }

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public abstract void getLinks() throws Exception;

    public abstract void plotGraph( );

    public abstract void storeInDatabase();

    public ArrayList<String> getNewUsingBoilerPipe(ArrayList<String> linksList) throws IOException {
        ArrayList<String> newsList = new ArrayList<String>();

        try {
            for (int i = 0; i < linksList.size(); i++) {
                String ifNull = "There is not news , it may be ads";
                final HTMLDocument htmlDoc;
                String content;
                String titleWithContent;
                try {
                    if (HTMLFetcher.fetch(new URL(linksList.get(i))) == null)
                        System.out.println("There is no text in the link");
                } catch (IOException e) {
                    content = ifNull;
                    System.out.println("***********************************************************\n\n\n\n\n\n");
                    titleWithContent = "" + content;
                    newsList.add(titleWithContent);
                    continue;
                }
                try {
                    htmlDoc = HTMLFetcher.fetch(new URL(linksList.get(i)));
                    final TextDocument doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
                    content = CommonExtractors.ARTICLE_EXTRACTOR.getText(doc);
                    titleWithContent = "" + content; // add article to Database
                    System.out.println(doc.getTitle());
                    System.out.println(linksList.get(i));
                    System.out.println(titleWithContent);
                    newsList.add(titleWithContent);
                } catch (Exception e) {
                    continue;
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return newsList;
    }

    protected ArrayList<String> checkIfLinkIsAlreadyExist(ArrayList<String> linksList, MongoCollection<Document> collection, MongoDatabase database) {
        Date date = new Date();
        String linksDate = sdf.format(date);

        ArrayList<String> newlinks = new ArrayList<String>();
        for (int i = 0; i < linksList.size(); i++) {
            org.bson.Document toInsert = new org.bson.Document("links", linksList.get(i)).append("date", linksDate); // object to inset
            if (collection.find().first() == null) {
                newlinks.add(linksList.get(i));
                System.out.println("add " + linksList.get(i) + " a new links");

            } else {
                BasicDBObject filter = new BasicDBObject();
                filter.put("links", linksList.get(i));
                if (collection.find(filter).first() == null) {
                    newlinks.add(linksList.get(i));
                    System.out.println("add " + linksList.get(i) + " a new links");

                } else {
                   System.out.println("Sorry " + linksList.get(i) + " already exists");
                }
            }
        }

        return newlinks;
    }

    public void storeInMongodb(ArrayList<String> newlinks, ArrayList<String> newsList, MongoCollection<Document>
                                                collection, MongoCollection<Document> collection2, String webSiteName) {

        Date date = new Date();
        String linksDate = sdf.format(date);


        for (int i = 0; i < newlinks.size(); i++) {
            if (newlinks.size() == newsList.size()) {
                org.bson.Document toInsert = new org.bson.Document("links", newlinks.get(i)).append("date", linksDate).append("news", newsList.get(i)); // object to inset
                if (collection.find().first() == null) {
                    collection.insertOne(toInsert); // Add links to the database
                } else {
                    BasicDBObject filter = new BasicDBObject();
                    filter.put("links", newlinks.get(i));
                    if (collection.find(filter).first() == null) {
                        collection.insertOne(toInsert);
                     } else {
                       // System.out.println("Sorry " + newlinks.get(i) + " already exists");
                    }
                }
            }
        }
      //  System.out.println("before add to db " + newlinks.size());
        org.bson.Document toInsert2 = new org.bson.Document("date", linksDate)
                                    .append("newLinks", newlinks.size()).append("webSiteName", webSiteName); // object to insert
        collection2.insertOne(toInsert2); // Add to linksCounter databases
    }

    public void graphs(String webSiteName, DBCollection counter) {
        plotGraph chart = new plotGraph(
                "" + webSiteName,
                "links by time ", counter);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);

    }
}