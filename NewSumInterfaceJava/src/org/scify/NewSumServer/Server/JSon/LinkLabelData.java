package org.scify.NewSumServer.Server.JSon;

/**
 *
 * @author grv
 */

/**
 * Class contains the link-url and the sourceName which is basically a label
 * for the link-url
 */
public class LinkLabelData{
        private String link;
        private String sourceName;

        public LinkLabelData(){
            
        }
        
        public LinkLabelData(String link, String sourceName) {
            this.link = link;
            this.sourceName = sourceName;
        }
       

    public String getLink() {
        return link;
    }

    public String getSourceName() {
        return sourceName;
    }

    @Override
    public String toString() {
        return "LinkLabelData{" + "link=" + link + ", sourceName=" + sourceName + '}';
    }
    }