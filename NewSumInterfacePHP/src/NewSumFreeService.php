<?php

/**
 * NewSumFreeService class
 * 
 *  
 * 
 * @author    {scify}
 */
//webservice constructor
class NewSumFreeService extends SoapClient {

    public function NewSumFreeService($wsdl) {
        parent::__construct($wsdl);
    }
    
    /**
     *  
     *
     * @return array(linkLabels) returns linkLabel elements that correspond to the sources used in NewSum.
     * See documentation for more info on the array(linkLabels) structure.
     */
    public function getLinkLabels() {
        $post=array('parameters' => array());
        try { 
            $response = $this->__soapCall('getLinkLabels',$post)->return;
        } catch(SoapFault $client) { 
            printf("<br/> Request = %s </br>", htmlspecialchars($client->faultcode));
            print $client->getMessage(); 
            print $client->getTraceAsString(); 
        }
        return json_decode($response);
    }
    
    /**
     *
     *
     * @param array(string) $userSources Contains user sources. Can contain  the
     * string "All" or can be initialized to null in order to use all the sources.
     * The whole list can also be obtained by calling getLinkLabels and processing the links. 
     * @throws SOAPException
     * @return array(string) The categories relevant to the userSources specified.
     */
    public function getCategories($userSources){
        $post=array('parameters' => array('sUserSources' => json_encode($userSources)));
        try { 
            $response = $this->__soapCall('getCategories',$post)->return;
        } catch(SoapFault $client) { 
            printf("<br/> Request = %s </br>", htmlspecialchars($client->faultcode));
            print $client->getMessage(); 
            print $client->getTraceAsString(); 
        }
        return json_decode($response);
    }

    /** 
     *
     *
     * @param array(string) $userSources Contains user sources. Can contain  the
     * string "All" or can be initialized to null in order to use all the sources.
     * The whole list can also be obtained by calling getLinkLabels and processing the links. 
     * @param String $category The category relevant to which to get topicTitles.
     * @return array(Topics) Array of Topics according to search.
     * See documentation for more info on the array(Topics) structure.
     */
    public function getTopics($userSources,$category) {
        $post=array('parameters' => array('sUserSources' => json_encode($userSources),
            'sCategory' => json_encode($category)));
        try { 
            $response = $this->__soapCall('getTopics',$post)->return;
        } catch(SoapFault $client) { 
            printf("<br/> Request = %s </br>", htmlspecialchars($client->faultcode));
            print $client->getMessage(); 
            print $client->getTraceAsString(); 
        }
        return json_decode($response);
    }

    /**
     *  
     * @param string $keyword Contains the keyword relevant to which you wish to get topics.
     * @param array(string) $userSources Contains user sources. Can contain  the
     * string "All" or can be initialized to null in order to use all the sources.
     * The whole list can also be obtained by calling getLinkLabels and processing the links. 
     * @return array(topics) topics found using the keyword to search amongst userSources topics.
     * See documentation for more info on the array(topics) structure.
     */
    public function getTopicsByKeyword($keyword,$userSources) {
        $post=array('parameters' => array('sKeyword' => json_encode($keyword),
                'sUserSources' => json_encode($userSources)));
            try { 
                $response = $this->__soapCall('getTopicsByKeyword',$post)->return;
            } catch(SoapFault $client) { 
                printf("<br/> Request = %s </br>", htmlspecialchars($client->faultcode));
                print $client->getMessage(); 
                print $client->getTraceAsString(); 
            }
            return json_decode($response);
    }
    
    /**
     *  
     *
     * @param string $topicID Single topicID used to extract summary from.
     * getTopicIDs can be used to get one.
     * @param array(string) $userSources Contains user sources. Can contain  the
     * string "All" or can be initialized to null in order to use all the sources.
     * The whole list can also be obtained by calling getLinkLabels and processing the links. 
     * @return getSummaryResponse
     */
    public function getSummary($topicID,$userSources) {
        $post=array('parameters' => array('sTopicID' => json_encode($topicID),
                'sUserSources' => json_encode($userSources)));
        try { 
            $response = $this->__soapCall('getSummary',$post)->return;
        } catch(SoapFault $client) { 
            printf("<br/> Request = %s </br>", htmlspecialchars($client->faultcode));
            print $client->getMessage(); 
            print $client->getTraceAsString(); 
        }
        return json_decode($response);
    }

}

?>
