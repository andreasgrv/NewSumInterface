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
    private static final String READ_TOPICIDS_METHOD = "getTopicIDs";
    private static final String READ_TOPICS_METHOD = "getTopicTitles";
    private static final String READ_CATEGORIES_METHOD = "getCategories";
    private static final String GET_SUMMARY_METHOD = "getSummary";
    private static final String GET_TOPIC_TITLES_BY_IDS = "getTopicTitlesByIDs";
    private static final String GET_TOPICS_BY_KEYWORD = "getTopicsByKeyword";
    private static HttpTransportSE androidHttpTransport;
    private static SoapSerializationEnvelope envelope; 
   
    public NewSumInstance(){
        androidHttpTransport=new HttpTransportSE(URL);
        envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
    }

    public static LinksData getLinkLabels() throws Exception{
        SoapObject request = new SoapObject(NAMESPACE, GET_LINK_LABELS);
        envelope.setOutputSoapObject(request);
        androidHttpTransport.call(SOAP_ACTION, envelope);
	SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
        String sLinks=resultsRequestSOAP.getProperty("return").toString();
        return new LinksData(sLinks);
    }
    
    public static CategoriesData getCategories(ArrayList <String> alUserSources) throws Exception{
        SoapObject request = new SoapObject(NAMESPACE, READ_CATEGORIES_METHOD);
        request.addProperty("sUserSources", JSon.jsonize(alUserSources));
        envelope.setOutputSoapObject(request);
        androidHttpTransport.call(SOAP_ACTION, envelope);
	SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
        String sCategories=resultsRequestSOAP.getProperty("return").toString();
        return new CategoriesData(sCategories);
    }

    public static SummaryData getSummary(String sTopicID,ArrayList <String> alUserSources) throws Exception{
        SoapObject request = new SoapObject(NAMESPACE, GET_SUMMARY_METHOD);
        request.addProperty("sCategory", JSon.jsonize(sTopicID));
        request.addProperty("sUserSources", JSon.jsonize(alUserSources));
        envelope.setOutputSoapObject(request);
        androidHttpTransport.call(SOAP_ACTION, envelope);
        SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
        String sSummary=resultsRequestSOAP.getProperty("return").toString();
        return new SummaryData(sSummary);
    }
 
    public static TopicsData getTopicByKeyword(String sKeyword,
                                               ArrayList <String> alUserSources) throws Exception{
        SoapObject request = new SoapObject(NAMESPACE, GET_TOPICS_BY_KEYWORD);
        request.addProperty("sKeyword", JSon.jsonize(sKeyword));
        request.addProperty("sUserSources", JSon.jsonize(alUserSources));
        envelope.setOutputSoapObject(request);
        androidHttpTransport.call(SOAP_ACTION, envelope);
        SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
        String sTopics=resultsRequestSOAP.getProperty("return").toString();
        return new TopicsData(sTopics);
    }
}
