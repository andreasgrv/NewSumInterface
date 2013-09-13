#include <iostream>
#include <QtCore>
#include <iostream>
#include "KDSoapClientInterface.h"
#include "NewSumInterface.h"

int main(int argc,char * argv[]){
/*
	QCoreApplication a(argc,argv);
	const QString endPoint = QLatin1String("http://83.212.110.120:8080/NewSumFreeService/NewSumFreeService");
	const QString messageNamespace = QLatin1String("http://NewSumFreeService.Server.NewSumServer.scify.org/");
	KDSoapClientInterface client(endPoint, messageNamespace);
	client.setSoapVersion(KDSoapClientInterface::SOAP1_1);
	const QString method = QLatin1String("getLinkLabels");
	KDSoapMessage message;
	KDSoapMessage response;
	response= client.call(method,message);
	KDSoapValueList values=response.arguments();
	QList <KDSoapValue> list=values.attributes();
	qDebug("%s", qPrintable(response.arguments()[0].value().toString()));
	for(int i=0;i<list.size();i++){
		std::cout<<list.at(i).name().toStdString()<<std::endl;

	}
	
	return 0;
}
*/
	QCoreApplication app(argc,argv);
	const QString endPoint = QLatin1String("http://83.212.110.120:8080/NewSumFreeService/NewSumFreeService");
	const QString messageNamespace = QLatin1String("http://NewSumFreeService.Server.NewSumServer.scify.org/");
	NewSumService client(endPoint,messageNamespace);

	QList <QString> category=client.getCategories();

        for(int i=0;i<category.size();i++){
		QList <Topic> topics=client.getTopics(category.at(i));
		for(int i=0;i<topics.size();i++){
			QString topicID=topics.at(i).getID();
			Summary result4=client.getSummary(topicID);
		}

        }

	QList <Source> result2=client.getSources();
        for(int i=0;i<result2.size();i++){
		qDebug()<<result2.at(i).asString(); 
        }
	 
	return 0;


}
