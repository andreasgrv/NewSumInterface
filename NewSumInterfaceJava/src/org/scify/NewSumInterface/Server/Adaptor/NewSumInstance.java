/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scify.NewSumInterface.Server.Adaptor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import javax.xml.soap.SOAPException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.scify.NewSumInterface.Server.JSon.CategoriesData;
import org.scify.NewSumInterface.Server.JSon.JSon;
import org.scify.NewSumInterface.Server.JSon.LinksData;
import org.scify.NewSumInterface.Server.JSon.SummaryData;
import org.scify.NewSumInterface.Server.JSon.TopicsData;

/**
 *
 * @author grv
 */
public class NewSumInstance {

    private static final String USERDIR = System.getProperty("user.dir", ".");
    private static final String SEPARATOR = System.getProperty("file.separator", "/");
    private static final String FILENAME = "properties.dat";
    private static String FILEPATH = 
            USERDIR + SEPARATOR + SEPARATOR +FILENAME;
    private static final String GET_LINK_LABELS = "getLinkLabels";
    private static final String READ_CATEGORIES_METHOD = "getCategories";
    private static final String READ_TOPICS_METHOD = "getTopics";
    private static final String GET_TOPICS_BY_KEYWORD = "getTopicsByKeyword";
    private static final String GET_SUMMARY_METHOD = "getSummary";
    private static final String[] FILE_SYNTAX= {"wsdl:","namespace:","soap:"}; //order matters!
    private static HttpTransportSE androidHttpTransport;
    private static SoapSerializationEnvelope envelope;
    private static String url;
    private static String namespace;
    private static String soapAction;
    private static boolean initialized=false;
    
    static{
        try{
            initializeLinksFromFile();
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public static class InvalidFileFormatException extends Exception {

        private static final String message = "Format expected \n"
                + "wsdl:the_actual_url\n"
                + "namespace:the_actual_namespace\n"
                + "soap:the_actual_soap_location_url\n"
                + "Invalid file format in file \n";

        public InvalidFileFormatException(String path) {
            super(message + path);
        }
    }

    /**
     * Returns the list of default sources the server uses as a LinksData
     * object.
     *
     * LinksData is of form ArrayList <LinkData>
     *
     * @throws SOAPException,IOException XMLParserException.
     * @return The links (i.e. RSS feeds) and their assigned labels.
     * @see NewSumWS.pdf for more info.
     */
    public static LinksData getLinkLabels() throws Exception {
        SoapObject request = new SoapObject(namespace, GET_LINK_LABELS);
        androidHttpTransport = new HttpTransportSE(url);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);
        androidHttpTransport.call(soapAction, envelope);
        Object attempt = envelope.getResponse();
        if (attempt.getClass() == SoapFault.class) {
            SoapFault fault = (SoapFault) envelope.getResponse();
            throw fault;
        }
        SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
        String sLinks = resultsRequestSOAP.getProperty("return").toString();
        return new LinksData(sLinks);
    }

    /**
     * Traverses the User's Sources preference and returns the Categories that
     * correspond to these Sources as a CategoriesData object.
     *
     * CategoriesData is an ArrayList <String> containing the categories.
     *
     * @param alUserSources The user selected Sources passed as an ArrayList of
     * strings. If "All" is the first string in the ArrayList or null is passed
     * as alUserSources , all default sources are considered valid.
     * @throws SOAPException,IOException XMLParserException.
     * @return the categories that correspond to the specified user sources.
     * @see NewSumWS.pdf for more info.
     */
    public static CategoriesData getCategories(ArrayList<String> alUserSources) throws Exception {
        SoapObject request = new SoapObject(namespace, READ_CATEGORIES_METHOD);
        androidHttpTransport = new HttpTransportSE(url);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        request.addProperty("sUserSources", JSon.jsonize(alUserSources));
        envelope.setOutputSoapObject(request);
        androidHttpTransport.call(soapAction, envelope);
        Object attempt = envelope.getResponse();
        if (attempt.getClass() == SoapFault.class) {
            SoapFault fault = (SoapFault) envelope.getResponse();
            throw fault;
        }
        SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
        String sCategories = resultsRequestSOAP.getProperty("return").toString();
        return new CategoriesData(sCategories);
    }

    /**
     * Returns all topics that correspond the selected category and user sources
     * as a TopicsData object.
     *
     * The Topics are sorted first of all by date, and secondly by each Topic's
     * article count for the specific date.
     *
     * @param alUserSources The user selected Sources passed as an ArrayList of
     * strings. If "All" is the first string in the ArrayList or null is passed
     * as alUserSources , all default sources are considered valid.
     * @param sCategory The category to search for topics in.
     * @throws SOAPException,IOException XMLParserException.
     * @return the topics found as a TopicsData object.
     * @see NewSumWS.pdf for more info.
     */
    public static TopicsData getTopics(ArrayList<String> alUserSources, String sCategory) throws Exception {
        SoapObject request = new SoapObject(namespace, READ_TOPICS_METHOD);
        androidHttpTransport = new HttpTransportSE(url);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        request.addProperty("sUserSources", JSon.jsonize(alUserSources));
        request.addProperty("sCategory", JSon.jsonize(sCategory));
        envelope.setOutputSoapObject(request);
        androidHttpTransport.call(soapAction, envelope);
        Object attempt = envelope.getResponse();
        if (attempt.getClass() == SoapFault.class) {
            SoapFault fault = (SoapFault) envelope.getResponse();
            throw fault;
        }
        SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
        String sTopics = resultsRequestSOAP.getProperty("return").toString();
        return new TopicsData(sTopics);
    }

    /**
     * Searches the Topic index for the specified keyword, and returns all
     * topics that correspond to that keyword as a TopicsData object.
     *
     * The topics are sorted by the number of appearances of the keyword.
     *
     * @param sKeyword the user search entry as a String.
     * @param alUserSources The user selected Sources passed as an ArrayList of
     * strings. If "All" is the first string in the ArrayList or null is passed
     * as alUserSources , all default sources are considered valid.
     * @throws SOAPException,IOException XMLParserException.
     * @return the topics found for the specified keyword.
     * @see NewSumWS.pdf for more info.
     */
    public static TopicsData getTopicByKeyword(String sKeyword,
            ArrayList<String> alUserSources) throws Exception {
        SoapObject request = new SoapObject(namespace, GET_TOPICS_BY_KEYWORD);
        androidHttpTransport = new HttpTransportSE(url);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        request.addProperty("sKeyword", JSon.jsonize(sKeyword));
        request.addProperty("sUserSources", JSon.jsonize(alUserSources));
        envelope.setOutputSoapObject(request);
        androidHttpTransport.call(soapAction, envelope);
        Object attempt = envelope.getResponse();
        if (attempt.getClass() == SoapFault.class) {
            SoapFault fault = (SoapFault) envelope.getResponse();
            throw fault;
        }
        SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
        String sTopics = resultsRequestSOAP.getProperty("return").toString();
        return new TopicsData(sTopics);
    }

    /**
     * Returns the summary for the specified topicID relevant to the selected
     * user sources as a SummaryData.
     *
     * The SummaryData object contains an ArrayList of sources containing the
     * user selected sources and an ArrayList of snippets containing the
     * returned summaries.
     *
     * @param sTopicID The topic ID of interest as a String.
     * @param alUserSources The user selected Sources passed as an ArrayList of
     * strings. If "All" is the first string in the ArrayList or null is passed
     * as alUserSources , all default sources are considered valid.
     * @throws SOAPException,IOException XMLParserException.
     * @return The summary that corresponds to the specified ID and user
     * sources.
     * @see NewSumWS.pdf for more info.
     */
    public static SummaryData getSummary(String sTopicID, ArrayList<String> alUserSources) throws Exception {
        SoapObject request = new SoapObject(namespace, GET_SUMMARY_METHOD);
        androidHttpTransport = new HttpTransportSE(url);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        request.addProperty("sTopicID", JSon.jsonize(sTopicID));
        request.addProperty("sUserSources", JSon.jsonize(alUserSources));
        envelope.setOutputSoapObject(request);
        androidHttpTransport.call(soapAction, envelope);
        Object attempt = envelope.getResponse();
        if (attempt.getClass() == SoapFault.class) {
            SoapFault fault = (SoapFault) envelope.getResponse();
            throw fault;
        }
        SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
        String sSummary = resultsRequestSOAP.getProperty("return").toString();
        return new SummaryData(sSummary);
    }

    public static String getUrl() throws Exception{
        return url;
    }
    
    public static String getNamespace() throws Exception{
        return namespace;
    }
        
    public static String getSoapAction() throws Exception{
        return soapAction;
    }
    
    public static String getUrlEmptyOnFail(){
            return url;
    }
    
    public static String getNamespaceEmptyOnFail(){
            return namespace;
    }
        
    public static String getSoapActionEmptyOnFail(){
            return soapAction;
    }
    

    
    private static void initializeLinksFromFile() throws FileNotFoundException,InvalidFileFormatException{
        //reads SOAP_ACTION , URL and NAMESPACE
        //from file , if the file doesn't exist defaults are used. if IOException occurs throws exception
        try{
            if(!initialized){
                String filename=FILEPATH;
                InputStream fis = new FileInputStream(filename);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
                String line;
                int iteration=0;
                while ((line = br.readLine()) != null && iteration<FILE_SYNTAX.length) {
/*                  debug file read
                    System.out.println(line);
*/
                    //for everyline in file if array of FILE_SYNTAX is not exceeded 
                    String check=line.substring(0,(FILE_SYNTAX[iteration]).length());
                    if(check.equals(FILE_SYNTAX[iteration])){//if the format is correct
                        switch(iteration){//order matters 
                            case 0:
                                url=line.substring(FILE_SYNTAX[iteration].length(),line.length()).trim();
                                break;
                            case 1:
                                namespace=line.substring(FILE_SYNTAX[iteration].length(),line.length()).trim();
                                break;
                            case 2:
                                soapAction=line.substring(FILE_SYNTAX[iteration].length(),line.length()).trim();
                                break;
                        }
                    }
                    else{
                        throw new InvalidFileFormatException(FILENAME);
                    }
                    iteration++;
                }
/*                debug file read
                  System.out.println(url+"\n"+namespace+"\n"+soapAction);
*/
                initialized=true;
            }
        }catch(FileNotFoundException e){
            throw e;
        }catch(IOException e){
            throw new InvalidFileFormatException(FILENAME);
        }
    }
}
