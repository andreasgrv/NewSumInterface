package org.scify.NewSumServer.Server.JSon;

/**
 *
 * @author grv
 */

/**
 * Class contains the basic data needed for the summary.
 * 
 */
public class SnippetData {
    private String summary;
    private String sourceUrl;
    private String sourceName;
    private String feedUrl;

    public SnippetData(String summary, String sourceUrl, String sourceName, String feedUrl) {
        this.summary = summary;
        this.sourceUrl = sourceUrl;
        this.sourceName = sourceName;
        this.feedUrl = feedUrl;
    }

    public String getSummary() {
        return summary;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getFeedUrl() {
        return feedUrl;
    }
    
    
}
