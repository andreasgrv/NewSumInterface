#ifndef NEWSUMINTERFACE_H
#define NEWSUMINTERFACE_H
#include <QDate>
#include <QDateTime>
#include <QTime>
#include <QDebug>
#include <QJsonDocument> 
#include <QByteArray>
#include <QVariantList>
#include <QJsonObject>
#include <QJsonArray>
#include <QArrayData>
#include <QListData>
#include "KDSoapClientInterface.h"

const QString GETLANGUAGES=QLatin1String("getLanguages");
const QString GETSOURCES=QLatin1String("getLinkLabels");
const QString GETCATEGORIES=QLatin1String("getCategories");
const QString GETTOPICS=QLatin1String("getTopics");
const QString GETTOPICBYKEYWORD=QLatin1String("getTopicByKeyword");
const QString GETSUMMARY=QLatin1String("getSummary");

namespace parse{

	const QString yearkey="year";
	const QString monthkey="month";
	const QString daykey="dayOfMonth";
	const QString hourkey="hourOfDay";
	const QString minutekey="minute";
	const QString secondkey="second";

	int parseInt(QJsonValue val);
	QDateTime parseDateTime(QJsonObject val);
};

class Source{
	private:
		static const QString sourcekey;
		static const QString labelkey;
		static const QString logokey;
		QString source;
		QString label;
		QString logo;

	public:
		Source(const Source &copy); 
		Source(QJsonObject &json);
		Source& operator=(const Source &target);
		QString getSource() const;
		QString getLabel() const;
		QString getLogo() const;
		QString asString() const;
};

class Topic{
	private:
		static const QString idkey;
		static const QString titlekey;
		static const QString datekey;
		static const QString sourcesnumkey;
		static const QString imagekey;

		QString id;
		QString title;
		QDateTime date;
		int sourcesNum;
		QString imageUrl;
	public:
		static const QString categoryarg;

		Topic(QJsonObject &json);
		QString getID() const;
		QString getTitle() const;
		QDate getDate() const;
		QTime getTime() const;
		QDateTime getDateTime() const;
		int getSourcesNum() const;
		QString getImageUrl() const;
		QString asString() const;

};

class Summary{

};



class NewSumService{

	private:
		KDSoapClientInterface *client;
	
	public:

		NewSumService(const QString &endpoint,const QString &servicename);
		~NewSumService();
		QString getEndPoint();
		QList <QString> getLanguages();
		QList <Source> getSources();
		QList <QString> getCategories();
		QList <Topic> getTopics(const QString &category);
		
};


#endif
