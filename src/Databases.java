import com.mongodb.*;
public class Databases {
     private String db1Name;
    private String db2Name;


    /**
     *  to connect to database
     */
    public void showWhatInsideDatabases() {
        db1Name = "news";
        db2Name = "linksCounter1";
        String newscolections[] ={"news",/*,"sabahNews","BBCNews","CNNNews","aljazeeraNews","sozcuNews",*/"hurriyetNews"/*,*//*"odatvNews"*/} ;
        String linksCollectionsName[] = {"milliyetCounter",/* "sabahCounter","BBCCounter","CNNCounter","aljazeeraCounter","sozcuCounter",*/"hurriyetCounter"/*,"odatvCounter"*/};

        DB db = MongoConnection.getDB(db1Name);
        DB db1 = MongoConnection.getDB(db2Name);
        for (int i = 0; i < newscolections.length; i++) {
            String counter[] = {newscolections[i]  ,linksCollectionsName[i]};
            printdatapase(db,db1,counter);
        }

    }

    /**
     *
     * @param db1 it's for first database that contains news
     * @param db it's for showing how many  new links at specific time
     * @param counter
     */
    private static void printdatapase(DB db1, DB db, String[] counter) {

          for (int i = 0; i < counter.length; i++) {


            DBCollection table = db.getCollection(counter[i]);
            DBCollection table1 = db1.getCollection(counter[i]);


            BasicDBObject searchQuery = new BasicDBObject();
            BasicDBObject searchQuery1 = new BasicDBObject();


            DBCursor cursor = table.find(searchQuery);
            DBCursor cursor2 = table1.find(searchQuery1);


            while (cursor.hasNext()) {
                String doc = cursor.next().toString();
                System.out.println(doc);
            }
             while (cursor2.hasNext()) {
                String doc = cursor2.next().toString();
                System.out.println(doc);
            }

        }

    }
}
