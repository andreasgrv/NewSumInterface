#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
#  NewSumInterface.py
#  
#  Copyright 2013  <scify@scifyTerminal2>
#  
#  This program is free software; you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation; either version 2 of the License, or
#  (at your option) any later version.
#  
#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#  
#  You should have received a copy of the GNU General Public License
#  along with this program; if not, write to the Free Software
#  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
#  MA 02110-1301, USA.
#  
#  

DATA_FILE='properties.dat'
DATA_FILE_PREFIXES=['wsdl:','namespace:','soap:']
ERROR_MSG="Wrong file format \n\
wsdl:the_wsdl_link\n\
namspace:the_web_service_locationi\n\
soap:the_soap_location \n\
expected"
LABELS=["wsdl","namespace","soap"]
class NewSumInterface:
    """ Class used for communication with the NewSumServer """

    def __init__(self):
        """ read wsdl location from file """
        import os
        workingDir= os.path.dirname(__file__)
        filename=os.path.join(workingDir,DATA_FILE)
        f=open(filename,'r')
        dataLines= {LABELS[x]:y.replace(DATA_FILE_PREFIXES[x],'') for (x,y) in enumerate(line.strip() for line in f) if y.startswith(DATA_FILE_PREFIXES[x])} 
        if len(dataLines) < len(LABELS):
            raise Exception(ERROR_MSG)
        self.wsdl=dataLines['wsdl']
        self.namespace=dataLines['namespace']
        self.soap=dataLines['soap']

    def getLinkLabels(self):
        """ Get news sources from server as list of {url,label}""" 
        import json
        from suds.client import Client
        client=Client(self.wsdl)
        result= client.service.getLinkLabels()
        return json.loads(result)

    def getCategories(self,categories=["All"]):
        """Gets categories relative to the users selected sources"""
        pass
        import json
        from suds.client import Client
        client=Client(self.wsdl)
        jsonizedCat=json.dumps(categories)
        result = client.service.getCategories(jsonizedCat)
        return json.loads(result)

    def getTopics(self,category,sources):
        """Get topics relative to the user sources and the category selected"""
        import json
        from suds.client import Client
        client=Client(self.wsdl)
        jsonizedCat=json.dumps(category)
        jsonizedSources=json.dumps(sources)
        result= client.service.getTopics(jsonizedSources,jsonizedCat)
        return json.loads(result)

    def getTopicsByKeyword(self,keyword,sources):
        """Get topics relevant to the user sources and the keyword to search for"""
        import json
        from suds.client import Client
        client=Client(self.wsdl)
        jsonizedKey=json.dumps(keyword)
        jsonizedSources=json.dumps(sources)
        result = client.service.getTopicsByKeyword(jsonizedKey,jsonizedSources)
        return json.loads(result)

    def getSummary(self,topicID,sources):
        """Get the summary that corresponds the the selected id amongst
        the selected user sources"""
        import json
        from suds.client import Client
        client=Client(self.wsdl)
        jsonizedID=json.dumps(topicID)
        jsonizedSources=json.dumps(sources)
        result = client.service.getSummary(jsonizedID,jsonizedSources)
        return json.loads(result)

def main(): 
    #import logging
    #logging.basicConfig(level=logging.INFO)
    #logging.getLogger('suds.client').setLevel(logging.DEBUG)
    
    client=NewSumInterface()
    #print NewSumInterface().getCategories()
    print "Link labels!\n\n"
    for i in client.getLinkLabels():
        print i['link']
        print i['sourceName']

    sources=[i['link'] for i in client.getLinkLabels()]
    print "Categories!\n\n"
    for i in client.getCategories(sources[1:15]):
        print i

    topics=client.getTopics("Αθλητισμός",sources)
    print "Topics! \n\n"
    for topic in topics: 
        print topic['topicTitle']

    try:
        topicsFromKey=client.getTopicsByKeyword("alsdkasd",sources)
        print "Topics from Keyword!\n\n"
        for topicK in topicsFromKey:
            print topicK['topicTitle']
    except Exception, e:
        print e
    summaries=client.getSummary(topic['topicID'],None)
    print "Summaries! \n\n"
    for snippet in summaries['snippets']:
        print snippet['summary']
    return 0

if __name__ == '__main__':
    main()
    

