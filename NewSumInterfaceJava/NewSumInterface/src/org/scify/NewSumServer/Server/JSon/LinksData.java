package org.scify.NewSumServer.Server.JSon;

import java.util.ArrayList;

/**
 *
 * @author grv
 */

/**
 * Class contains ArrayList of LinkLabelData.
 * 
 * Used to easily manipulate the data
 * and easily transform between object and JSON format
 */
public class LinksData extends ArrayList <LinkLabelData> implements JSonizable{
    
    public LinksData() {
    }
    
    public LinksData(String json){
        super(JSon.json.fromJson(json, LinksData.class));
    }

    public ArrayList<String> getLinks(){
        ArrayList<String> links=new ArrayList();
        for (LinkLabelData each : this){
            links.add(each.getLink());
        }
        return links;
    }
    
    public ArrayList<String> getLinks(int thisMany){
        ArrayList<String> links=new ArrayList();
        for (LinkLabelData each : this){
            links.add(each.getLink());
            thisMany--;
            if(thisMany==0){
                break;
            }
        }
        return links;
    }

    @Override
    public String jsonize() {
        return JSon.json.toJson(this);
    }


    public static LinksData unjsonize(String jsonstring) throws Exception {
        return JSon.json.fromJson(jsonstring, LinksData.class);
    }
    
    @Override
    public String toString() {
        String temp="";
        for(LinkLabelData each : this){
            temp+=each.toString()+"\n";
        }
        return "LinksData{" + temp +'}';
    }
    
}
