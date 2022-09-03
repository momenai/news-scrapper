import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

// Demo program for connecting to MongoDB.
// Uses singleton pattern for MongoClient.
public class MongoConnection {

    private static MongoClient mClient;

    // Singleton for MongoClient
    // Creates a single connection pool internally
    public static MongoClient getMongoClient() {
        if (mClient == null) {
            mClient = new MongoClient("localhost", 27017);
        }
        return mClient;
    }

    // Utility method to get database instance
    public static MongoDatabase getDatabase() {
        return getMongoClient().getDatabase("news");
    }


    /**
     *
     * @param name to get data database instance
     * @return retrun database
     */
    public static MongoDatabase getDatabase(String name) {
        return getMongoClient().getDatabase(name);
    }

    // Utility method to get user collection
    public static MongoCollection<Document> getCollection() {
        return getDatabase().getCollection("news");
    }

    // Utility method to get user collection
    public static MongoCollection<Document> getCollection( String name) {
        return getDatabase().getCollection(name);
    }

    // Utility method to get user collection
    public static DBCollection getDBCollection(String linksNumberDatabaseName, String linksCollectionName) {
        return getMongoClient()
                .getDB(linksNumberDatabaseName)
                .getCollection(linksCollectionName);
    }

    public static DB getDB(String name){
        return  getMongoClient().getDB(name);
    }

}