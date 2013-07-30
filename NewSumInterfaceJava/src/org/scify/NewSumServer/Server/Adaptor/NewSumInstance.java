/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scify.NewSumServer.Server.Adaptor;

import java.util.ArrayList;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.scify.NewSumServer.Server.JSon.CategoriesData;
import org.scify.NewSumServer.Server.JSon.JSon;
import org.scify.NewSumServer.Server.JSon.LinksData;
import org.scify.NewSumServer.Server.JSon.SummaryData;
import org.scify.NewSumServer.Server.JSon.TopicsData;

/**
 *
 * @author grv
 */
public class NewSumInstance {

    private static final String SOAP_ACTION =
            "http://143.233.226.97:28080/NewSumFreeService/NewSumFreeService";
    private static final String NAMESPACE = "http://NewSumFreeService.Server.NewSumServer.scify.org/";
    public static final String URL = "";
    private static final String GET_LINK_LABELS = "getLinkLabels";
    private static final String READ_CATEGORIES_METHOD = "getCategories";
    private static final String READ_TOPICS_METHOD = "getTopics";
    private static final String GET_TOPICS_BY_KEYWORD = "getTopicsByKeyword";
    private static final String GET_SUMMARY_METHOD = "getSummary";
    private static HttpTransportSE androidHttpTransport;
    private static SoapSerializationEnvelope envelope;

    public NewSumInstance() {
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
        SoapObject request = new SoapObject(NAMESPACE, GET_LINK_LABELS);
        androidHttpTransport = new HttpTransportSE(URL);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);
        androidHttpTransport.call(SOAP_ACTION, envelope);
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
        SoapObject request = new SoapObject(NAMESPACE, READ_CATEGORIES_METHOD);
        androidHttpTransport = new HttpTransportSE(URL);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        request.addProperty("sUserSources", JSon.jsonize(alUserSources));
        envelope.setOutputSoapObject(request);
        androidHttpTransport.call(SOAP_ACTION, envelope);
        SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
        String sCategories = resultsRequestSOAP.getProperty("return").toString();
        return new CategoriesData(sCategories);
    }

    /**
     * Returns all topics that correspond the selected category and
     * user sources as a TopicsData object.
     *
     * The Topics are sorted first of all by date, and secondly by each
     * Topic's article count for the specific date.
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
        SoapObject request = new SoapObject(NAMESPACE, READ_TOPICS_METHOD);
        androidHttpTransport = new HttpTransportSE(URL);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        request.addProperty("sUserSources", JSon.jsonize(alUserSources));
        request.addProperty("sCategory", JSon.jsonize(sCategory));
        envelope.setOutputSoapObject(request);
        androidHttpTransport.call(SOAP_ACTION, envelope);
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
        SoapObject request = new SoapObject(NAMESPACE, GET_TOPICS_BY_KEYWORD);
        androidHttpTransport = new HttpTransportSE(URL);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        request.addProperty("sKeyword", JSon.jsonize(sKeyword));
        request.addProperty("sUserSources", JSon.jsonize(alUserSources));
        envelope.setOutputSoapObject(request);
        androidHttpTransport.call(SOAP_ACTION, envelope);
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
        SoapObject request = new SoapObject(NAMESPACE, GET_SUMMARY_METHOD);
        androidHttpTransport = new HttpTransportSE(URL);
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        request.addProperty("sCategory", JSon.jsonize(sTopicID));
        request.addProperty("sUserSources", JSon.jsonize(alUserSources));
        envelope.setOutputSoapObject(request);
        androidHttpTransport.call(SOAP_ACTION, envelope);
        SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
        String sSummary = resultsRequestSOAP.getProperty("return").toString();
        return new SummaryData(sSummary);
    }
}
