package org.scify.NewSumServer.Server.JSon;

import com.google.gson.Gson;

/**
 *
 * @author grv
 */

    /**
     * Class used as wrapper for Gson with data types that aren't in this package
     * JSonizable interface is implemented for the other classes
     */
public class JSon {
    protected static Gson json=new Gson();
 
    /**
    * Class used as wrapper for Gson with specific return data types
     */
    public static String jsonize(Object object){
        return json.toJson(object);
    } 
   
    public static <T> T unjsonize(String jsonstring, Class<T> classOfT) throws Exception{
        return json.fromJson(jsonstring, classOfT);
    }
}
