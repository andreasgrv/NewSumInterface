package org.scify.NewSumServer.Server.JSon;

import java.util.ArrayList;

/**
 *
 * @author grv
 */

/**
 * Class contains the input source data that was used to get the resulting summary.
 * 
 */
public class SourceData {
    private String url;
    private String name;
    private ArrayList <String> imageUrls;

    /**
     * Make new SourceData from input
     * 
     * @param url the String containing the url source.
     * @param name the name of the source - a label.
     * @param imageUrls url's pointing to images to be used with summary
     * @return a instance of SourceData corresponding to input.
     */
    public SourceData(String url, String name, ArrayList<String> imageUrls) {
        this.url = url;
        this.name = name;
        this.imageUrls = imageUrls;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    /**
     * Check if the two objects are equal
     * 
     * @param url the String containing the url source.
     * @param name the name of the source - a label.
     * @param imageUrls url's pointing to images to be used with summary
     * @return a instance of SourceData corresponding to input.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SourceData other = (SourceData) obj;
        if ((this.url == null) ? (other.url != null) : !this.url.equals(other.url)) {
            return false;
        }
        return true;
    }
    
}
