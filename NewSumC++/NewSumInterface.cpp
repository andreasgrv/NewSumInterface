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

//---------------------------Summary--------------------------------------

//-------Source--subclass-------------------------------------------------
const QString Summary::Source::urlkey="url";
const QString Summary::Source::namekey="name";
const QString Summary::Source::imageurlkey="imageUrl";

Summary::Source::Source(QJsonObject &json){
	url=json.take(urlkey).toString();
	name=json.take(namekey).toString();
	imageUrl=json.take(imageurlkey).toString();
}

//-------Snippet--subclass-------------------------------------------------
const QString Summary::Snippet::summarykey="summary";
const QString Summary::Snippet::sourceurlkey="sourceUrl";
const QString Summary::Snippet::sourcenamekey="sourceName";
const QString Summary::Snippet::feedurlkey="feedUrl";

Summary::Snippet::Snippet(QJsonObject &json){
	summary=json.take(summarykey).toString();
	sourceUrl=json.take(sourceUrl).toString();
	sourceName=json.take(sourcenamekey).toString();
	feedUrl=json.take(feedurlkey).toString();
}

//------rest-of-Summary---------------------------------------------------
const QString Summary::topicidarg="sTopicID";
const QString Summary::sourceskey="sources";
const QString Summary::snippetskey="snippets";


Summary::Summary(QJsonObject &json){
	QJsonValue sourcesval=json.take(sourceskey);
	QVariantList sourcesvar=sourcesval.toVariant().toList();
	for(int i=0;i<sourcesvar.size();i++){
		QJsonObject temp=QJsonObject::fromVariantMap(sourcesvar.at(i).value<QVariantMap>());
		Source item(temp);
		sources.push_back(item);	
	}

	QJsonValue snippetsval=json.take(snippetskey);
	QVariantList snippetsvar=snippetsval.toVariant().toList();

	for(int i=0;i<snippetsvar.size();i++){
		QJsonObject temp=QJsonObject::fromVariantMap(snippetsvar.at(i).value<QVariantMap>());
		Snippet item(temp);
		snippets.push_back(item);
	}
}

QList <QMap<QString,QString>> Summary::getSnippets(){
	QList <QMap<QString,QString>> result;
	for(Snippet each : snippets){
		QMap <QString,QString> map;
		map.insert(Snippet::summarykey,each.summary);
		map.insert(Snippet::sourceurlkey,each.sourceUrl);
		map.insert(Snippet::sourcenamekey,each.sourceName);
		map.insert(Snippet::feedurlkey,each.feedUrl);
		result.append(map);
	}
	return result;	
}

QList <QMap<QString,QString>> Summary::getSources(){
	QList <QMap<QString,QString>> result;
	for(Source each : sources){
		QMap <QString,QString> map;
		map.insert(Source::urlkey,each.url);
		map.insert(Source::namekey,each.name);
		map.insert(Source::imageurlkey,each.imageUrl);
		result.append(map);
	}
	return result;	
}

QString Summary::asString(){
	QString sourcestring="";
	for(Source each : sources){
		sourcestring+=each.name;
		sourcestring+=" ";
		sourcestring+=each.url;
		sourcestring+=" ";
		sourcestring+=each.imageUrl;
		sourcestring+="\n";
	}
	QString snippetstring="";
	for(Snippet each : snippets){
		snippetstring+=each.summary;
		snippetstring+=" ";
		snippetstring+=each.sourceUrl;
		snippetstring+=" ";
		snippetstring+=each.sourceName;
		snippetstring+=" ";
		snippetstring+=each.feedUrl;
		snippetstring+="\n";
	}
	QString result=sourcestring+"\n"+snippetstring;
	return result;
}

//--------------------------NewSumService----------------------------------

int NewSumService::topicCounter=0;
int NewSumService::summaryCounter=0;

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
	QString json=response.arguments()[0].value().toString();
	std::ofstream file (method.toStdString()+".json");
	if (file.is_open()){
		file << json.toStdString();
		file.close();
	}
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
	std::ofstream file (method.toStdString()+".json");
	if (file.is_open()){
		file << json.toStdString();
		file.close();
	}
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
	summaryCounter=0;
	const QString method = GETTOPICS;
	KDSoapMessage message,response;
	QVariant categoryvar(category);
	message.addArgument(Topic::categoryarg,categoryvar);
	response= client->call(method,message);
	QString json=response.arguments()[0].value().toString();
	std::ofstream file (method.toStdString()+std::to_string(topicCounter)+".json");
	if (file.is_open()){
		file << json.toStdString();
		file.close();
	}
	QByteArray bytes=json.toLocal8Bit();
	QJsonDocument parsed=QJsonDocument::fromJson(bytes);
	QVariantList variants=parsed.array().toVariantList();
	QList <Topic> result;
	for(int i=0;i<variants.size();i++){
		QJsonObject temp=QJsonObject::fromVariantMap(variants.at(i).value<QVariantMap>());
		Topic item(temp);
		result.append(item);
	}
	topicCounter++;
	return result;
}

Summary NewSumService::getSummary(const QString &topicID){
	const QString method = GETSUMMARY;
	KDSoapMessage message,response;
	QVariant topicidvar(topicID);
	message.addArgument(Summary::topicidarg,topicidvar);
	response= client->call(method,message);
	QString json=response.arguments()[0].value().toString();
	std::ofstream file (method.toStdString()+std::to_string(topicCounter)+"."+std::to_string(summaryCounter)+".json");
	if (file.is_open()){
		file << json.toStdString();
		file.close();
	}
	QByteArray bytes=json.toLocal8Bit();
	QJsonDocument parsed=QJsonDocument::fromJson(bytes);
	QVariant variant=parsed.toVariant();
	QJsonObject summary=QJsonObject::fromVariantMap(variant.value<QVariantMap>());
	Summary result(summary);
	summaryCounter++;
	return result;
}
