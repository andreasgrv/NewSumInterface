package org.scify.NewSumInterface.Server.JSon;


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
    private String imageUrl;

    /**
     * Make new SourceData from input
     * 
     * @param url the String containing the url source.
     * @param name the name of the source - a label.
     * @param imageUrls url's pointing to image to be used with summary
     * @return a instance of SourceData corresponding to input.
     */
    public SourceData(String url, String name, String imageUrl) {
        this.url = url;
        this.name = name;
        this.imageUrl = imageUrl;
    }
    
    /**
     * Get the Url
     * 
     * @return sourceName - url as a String.
     */
    public String getUrl() {
        return url;
    }
    /**
     * Get the name
     * 
     * @return sourceName - name as a String.
     */
    public String getName() {
        return name;
    }
    /**
     * Get the imageUrl
     * 
     * @return sourceName - imageUrl as a String.
     */
    public String getImageUrl() {
        return imageUrl;
    }
    
    @Override
    public int hashCode() { //make hash from url
        int hash = (this.url).hashCode();
        return hash;
    }

    /**
     * Check if the two objects are equal
     * 
     * @param url the String containing the url source.
     * @param name the name of the source - a label.
     * @param imageUrls url's pointing to image to be used with summary
     * @return a instance of SourceData corresponding to input.
     */
    @Override   //code is a bit awkward, it was autogenerated and it works so I won't mess with it
    public boolean equals(Object obj) { //check comments for explanation of what happens
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }    
        final SourceData other = (SourceData) obj; //2 instances of SourceData are equal if they have the same url.
        if ((this.url == null) ? (other.url != null) : !this.url.equals(other.url)) {
            return false;
        }
        return true;
    }
    
}
