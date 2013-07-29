package org.scify.NewSumServer.Server.JSon;

import java.util.ArrayList;

/**
 *
 * @author grv
 */

/**
 * Class contains data of a summary.
 * 
 * Used to easily manipulate the data
 * and easily transform between object and JSON format
 */
public class SummaryData implements JSonizable{
    private ArrayList <SourceData> sources;
    private ArrayList <SnippetData> snippets;

    public SummaryData(String json){
        SummaryData temp=JSon.json.fromJson(json, SummaryData.class);
        this.sources=temp.sources;
        this.snippets=temp.snippets;
    }
        
    public SummaryData(ArrayList<SourceData> sources, ArrayList<SnippetData> snippets) {
        this.sources = sources;
        this.snippets = snippets;
    }

    public ArrayList<SourceData> getSources() {
        return sources;
    }

    public ArrayList<SnippetData> getSnippets() {
        return snippets;
    }
    
    public ArrayList<String> getSummary(){
        ArrayList<String> summaries=new ArrayList();
        for(SnippetData each : snippets){
            summaries.add(each.getSummary());
        }
        return summaries;
    }

    @Override
    public String jsonize() {
        return JSon.json.toJson(this);
    }


    public static SummaryData unjsonize(String jsonstring) throws Exception {
        return JSon.json.fromJson(jsonstring, SummaryData.class);
    }

}
