#include "NewSumInterface.h"

//-----------------------------Parse--------------------------------------

int parse::parseInt(QJsonValue val){
	QVariant variant=val.toVariant();
	return variant.toInt();
}

QDateTime parse::parseDateTime(QJsonObject dateobj){
	int year=parse::parseInt(dateobj.take(yearkey));
	int month=parse::parseInt(dateobj.take(monthkey));
	int day=parse::parseInt(dateobj.take(daykey));
	int hour=parse::parseInt(dateobj.take(hourkey));
	int minute=parse::parseInt(dateobj.take(minutekey));
	int second=parse::parseInt(dateobj.take(secondkey));
	QDate date(year,month,day);
	QTime time(hour,minute,second);
	QDateTime datetime(date,time);
	return datetime;
}

//--------------------------Source----------------------------------

const QString Source::sourcekey="link";
const QString Source::labelkey="sourceName";
const QString Source::logokey="logo";

Source::Source(const Source &copy){
	*this=copy;
}

Source::Source(QJsonObject &json){
	source=json.take(sourcekey).toString();
	label=json.take(labelkey).toString();
}

Source& Source::operator=(const Source& target){
	source=target.source;
	label=target.label;
	logo=target.logo;
	return *this;
}

QString Source::getSource() const{
	return source;
}

QString Source::getLabel() const{
	return label;
}

QString Source::getLogo() const{
	return logo;
}

QString Source::asString() const{
	return source + " " + label + " " + logo;
}

//--------------------------Topic----------------------------------

const QString Topic::idkey="topicID";
const QString Topic::titlekey="topicTitle";
const QString Topic::datekey="date";
const QString Topic::sourcesnumkey="sourcesNum";
const QString Topic::imagekey="imageUrl";
const QString Topic::categoryarg="sCategory";

Topic::Topic(QJsonObject &json){
	qDebug() << json;
	id=json.take(idkey).toString();
	title=json.take(titlekey).toString();
	QJsonValue dateval=json.take(datekey);
	QVariant datevar=dateval.toVariant();
	QJsonObject dateobj=QJsonObject::fromVariantMap(datevar.value<QVariantMap>());
	date=parse::parseDateTime(dateobj);
	sourcesNum=parse::parseInt(json.take(sourcesnumkey));
	imageUrl=json.take(imagekey).toString();
}

QString Topic::getID() const{
	return id;
}

QString Topic::getTitle() const{
	return title;
}

QDate Topic::getDate() const{
	return date.date();
}

QTime Topic::getTime() const{
	return date.time();
}

int Topic::getSourcesNum() const{
	return sourcesNum;
}

QString Topic::getImageUrl() const{
	return imageUrl;
}

QString Topic::asString() const{
	QString value=id + " " + title + " " + date.toString() + " " + sourcesNum + " " + imageUrl;
	return value;
}

QDateTime Topic::getDateTime() const{
	return date;
}


//--------------------------NewSumService----------------------------------
NewSumService::NewSumService(const QString &endpoint,const QString &servicename){
	client= new KDSoapClientInterface(endpoint,servicename);
	client->setSoapVersion(KDSoapClientInterface::SOAP1_1);
}

NewSumService::~NewSumService(){
	delete client;
}

QString NewSumService::getEndPoint(){
	return client->endPoint();
}

//-------------------------Web-Service-Functions---------------------------------------------

QList <Source> NewSumService::getSources(){
	const QString method = GETSOURCES;
        KDSoapMessage message,response;
        response= client->call(method,message);
//        qDebug("%s", qPrintable(response.arguments()[0].value().toString()));
	QString json=response.arguments()[0].value().toString();
	QByteArray bytes=json.toLocal8Bit();
	QJsonDocument parsed=QJsonDocument::fromJson(bytes);
	QVariantList variants=parsed.array().toVariantList();
	QList <Source> result;
	for(int i=0;i<variants.size();i++){
		QJsonObject temp=QJsonObject::fromVariantMap(variants.at(i).value<QVariantMap>());
		Source item(temp);
		result.append(item);
	}
	return result; 
}
QList <QString> NewSumService::getCategories(){
	const QString method = GETCATEGORIES;
        KDSoapMessage message,response;
        response= client->call(method,message);
	QString json=response.arguments()[0].value().toString();
	QByteArray bytes=json.toLocal8Bit();
	QJsonDocument parsed=QJsonDocument::fromJson(bytes);
	QVariantList variants=parsed.array().toVariantList();
	QList <QString> result;
	for(int i=0;i<variants.size();i++){
		QString each(variants.at(i).value<QString>());
		result.append(each);
	}
	return result; 
}

QList <Topic> NewSumService::getTopics(const QString &category){
	const QString method = GETTOPICS;
	KDSoapMessage message,response;
	QVariant categoryvar(category);
	message.addArgument(Topic::categoryarg,categoryvar);
	response= client->call(method,message);
	QString json=response.arguments()[0].value().toString();
	QByteArray bytes=json.toLocal8Bit();
	QJsonDocument parsed=QJsonDocument::fromJson(bytes);
	QVariantList variants=parsed.array().toVariantList();
	QList <Topic> result;
	for(int i=0;i<variants.size();i++){
		QJsonObject temp=QJsonObject::fromVariantMap(variants.at(i).value<QVariantMap>());
		Topic item(temp);
		result.append(item);
	}
	return result;
}
