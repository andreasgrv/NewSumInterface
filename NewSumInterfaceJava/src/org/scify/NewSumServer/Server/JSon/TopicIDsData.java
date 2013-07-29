package org.scify.NewSumServer.Server.JSon;

import com.google.gson.JsonSyntaxException;
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

    /**
     * Make new TopicIDsData
     * 
     * @return a empty instance of TopicIDsData.
     */
    public TopicIDsData() {
    }

    /**
     * Make new TopicIDsData from JSON String
     * 
     * @param json input String containing the JSON code for the object
     * @throws JsonSyntaxException
     * @return a instance of TopicIDsData corresponding to input format.
     */
    public TopicIDsData(String json) throws JsonSyntaxException{
        super(JSon.json.fromJson(json, TopicIDsData.class));
    }
    
    /**
     * Make new TopicIDsData
     * 
     * @param c the <String> collection to be used as input
     * @return an instance of TopicIDsData with the id's from the collection
     */
    public TopicIDsData(Collection<? extends String> c) {
        super(c);
    }

    /**
     * Returns a String of the JSON format of the current object
     * 
     * @return JSON format of object.
     */
    @Override
    public String jsonize() {
        return JSon.json.toJson(this);
    }
    
    /**
     * Returns an instance of TopicIDsData relevant to the JSON string
     * 
     * @param jsonstring the String in json format to be converted.
     * @throws JsonSyntaxException
     * @return instance of object corresponding to the JSON String.
     */
    public static TopicIDsData unjsonize(String jsonstring) throws Exception {
        return JSon.json.fromJson(jsonstring, TopicIDsData.class);
    }
    
}
