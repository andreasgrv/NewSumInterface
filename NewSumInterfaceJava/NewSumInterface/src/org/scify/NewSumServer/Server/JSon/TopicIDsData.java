package org.scify.NewSumServer.Server.JSon;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author grv
 */

/**
 * Class contains the topicIDs as strings.
 * 
 * Used to easily manipulate the data
 * and easily transform between object and JSON format
 */
public class TopicIDsData extends ArrayList <String> implements JSonizable{

    public TopicIDsData() {
    }

    public TopicIDsData(String json){
        super(JSon.json.fromJson(json, TopicIDsData.class));
    }
    
    public TopicIDsData(Collection<? extends String> c) {
        super(c);
    }

    @Override
    public String jsonize() {
        return JSon.json.toJson(this);
    }
    

    public static TopicIDsData unjsonize(String jsonstring) throws Exception {
        return JSon.json.fromJson(jsonstring, TopicIDsData.class);
    }
    
}
