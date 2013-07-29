package org.scify.NewSumServer.Server.JSon;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author grv
 */

/**
 * Class contains the data needed to specify a topic.
 * 
 */
public class TopicData{
        private String topicID;
        private String topicTitle;
        private Calendar date;
        private int sourcesNum;
        private ArrayList <String> imageURLs;

    public TopicData() {
            
        }
        
    public TopicData(String topicID, String topicTitle, Calendar dateinms, int sourcesNum, ArrayList<String> imageUrls) {
        this.topicID = topicID;
        this.topicTitle = topicTitle;
        this.date = dateinms;
        this.sourcesNum = sourcesNum;
        this.imageURLs = imageUrls;
    }

    public String getTopicID() {
        return topicID;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public Calendar getDate() {
        return date;
    }

    public int getSourcesNum() {
        return sourcesNum;
    }

    public ArrayList<String> getImageURLs() {
        return imageURLs;
    }
        
}
