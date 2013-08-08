/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newsuminterface;

import java.util.ArrayList;
import org.scify.NewSumInterface.Server.Adaptor.NewSumInstance;
import org.scify.NewSumInterface.Server.JSon.CategoriesData;
import org.scify.NewSumInterface.Server.JSon.LinksData;
import org.scify.NewSumInterface.Server.JSon.SummaryData;
import org.scify.NewSumInterface.Server.JSon.TopicsData;

/**
 *
 * @author scify
 */
public class NewSumInterface {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            LinksData links=NewSumInstance.getLinkLabels();
            ArrayList <String> values=links.getLinks(15);
            System.out.println("\nLinkLabels \n");
            for(String each : values){
                System.out.println(each);
            }
            CategoriesData categories=NewSumInstance.getCategories(values);
            System.out.println("\nCategories \n");
            for(String each : categories){
                System.out.println(each);
            }
            TopicsData topics=NewSumInstance.getTopics(values,categories.get(0));
            System.out.println("\nTopics\n");
            ArrayList <String> ids=topics.getTopicIDs();
            for(String each : ids){
                System.out.println(each);
            }
            TopicsData topicskey=NewSumInstance.getTopicByKeyword("Scify",null);
            ArrayList <String> idskey=topicskey.getTopicIDs();
            System.out.println("\nTopics by key \n");
            for(String each : idskey){
                System.out.println(each);
            }
            SummaryData summary=NewSumInstance.getSummary("812cc4cb-af4c-48a0-b318-06d72962885f", values);
            ArrayList <String> snippets=summary.getSummaries();
            System.out.println("\nSummaries \n");
            for(String each: snippets){
                System.out.println(each);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
