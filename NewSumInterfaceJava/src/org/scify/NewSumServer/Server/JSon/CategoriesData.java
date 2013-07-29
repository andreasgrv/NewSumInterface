package org.scify.NewSumServer.Server.JSon;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author grv
 */


/**
 * Class contains categories as strings
 */
public class CategoriesData extends ArrayList <String> implements JSonizable{

    public CategoriesData() {
    }
    
    public CategoriesData(String json) throws Exception{
        super(JSon.json.fromJson(json, CategoriesData.class));
    }

    public CategoriesData(Collection<? extends String> c) {
        super(c);
    }

    @Override
    public String jsonize() {
        return JSon.json.toJson(this);
    }


    public static CategoriesData unjsonize(String jsonstring) throws Exception {
        return JSon.json.fromJson(jsonstring, CategoriesData.class);
    }
    
}
