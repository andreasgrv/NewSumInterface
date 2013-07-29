package org.scify.NewSumServer.Server.JSon;

import java.util.ArrayList;

/**
 *
 * @author grv
 */

/**
 * Class contains the topics.
 * 
 * Used to easily manipulate the data
 * and easily transform between object and JSON format
 */
public class TopicsData extends ArrayList <TopicData> implements JSonizable{

    public TopicsData() {
    }

    public TopicsData(String json){
        super(JSon.json.fromJson(json, TopicsData.class));
    }
    
    public ArrayList<String> getTopicIDs(){
        ArrayList<String> ids=new ArrayList();
        for(TopicData each : this){
            ids.add(each.getTopicID());
        }
        return ids;
    }

    @Override
    public String jsonize() {
        return JSon.json.toJson(this);
    }

    public static TopicsData unjsonize(String jsonstring) throws Exception {
        return JSon.json.fromJson(jsonstring, TopicsData.class);
    }
    
    @Override
    public String toString() {
        String temp="";
        for(TopicData each : this){
            temp+=each.toString()+"\n";
        }
        return "TopicsData{" + temp +'}';
    }
    
}
