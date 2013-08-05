#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
#  test.py
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
import NewSumInterface

def main():
    #import logging
    #logging.basicConfig(level=logging.INFO)
    #logging.getLogger('suds.client').setLevel(logging.DEBUG)
    
    client=NewSumInterface.NewSumInterface()
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
        print topic['topicID'], " : ", topic['topicTitle']

    topicsFromKey=client.getTopicsByKeyword("μόρσι",sources)
    print "Topics from Keyword!\n\n"
    for topicK in topicsFromKey:
        print topic['topicID'], " : ", topic['topicTitle']
        
    summaries=client.getSummary(topic['topicID'],["ALL"])
    print "Summaries! \n\n"
    for snippet in summaries['snippets']:
        print snippet['summary']
    return 0

if __name__ == '__main__':
    main()
    

