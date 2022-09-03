import com.mongodb.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
 import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.ArrayList;

class plotGraph extends ApplicationFrame {

    public plotGraph(String applicationTitle, String chartTitle, DBCollection counter) {
        super(applicationTitle);
        ArrayList<Integer> linksNumber = new ArrayList();
        ArrayList<String> dateLinks = new ArrayList();

        DB db = MongoConnection.getDB("linksCounter1");
        BasicDBObject searchQuery = new BasicDBObject();
        BasicDBObject fields = (BasicDBObject) new BasicDBObjectBuilder().add("newLinks", 1).add("_id", 0).get();
        DBCursor cursor = counter.find(searchQuery );

      while (cursor.hasNext()) {
            DBObject news=  cursor.next();
           Object num= news.get("newLinks");
          Object date= news.get("date");

          System.out.println(news);
          int i;

            if (num==null)
                i =0;
            else
                i = (Integer) num;
          linksNumber.add(i);
          dateLinks.add((String) date);


          System.out.println(  "   "+ i);
      }

        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle," ","Number of links",
                createDataset(linksNumber,dateLinks),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
         while (cursor.hasNext()) {
              String news=  cursor.next().toString();
           // System.out.println(  "   "+ cursor.next());
        }

    }

    private DefaultCategoryDataset createDataset(ArrayList<Integer> couner, ArrayList<String> dateLinks) {

//        int[] data = {500, 13, 60, 120, 80, 30};
//        String[] vals = {"08:00", "12:00", "13:00", "16:00", "20:00", "00:00"};
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < couner.size(); i++)
        {
            String data = dateLinks.get(i);
            if (data ==null)
                data = "";

            dataset.addValue(couner.get(i), "links",data );


        }
        return dataset;
    }


}