# AdapterService :: takecare-adapter-lab #

**Innehållsförteckning**



## Inledning ##

Idag finns ett tjänstekontrakt _GetClinicalChemistryLabOrderOutcome_ med tillhörande virtuelltjänst på tjänsteplattformen. Det finns idag ingen producent på SLL som implementerar detta kontraktet. Då producenten idag inte implementerar RIVTA kontraktet _GetClinicalChemistryLabOrderOutcome_ så behövs en adaptering göras mot bakomliggande tjänst som exponeras.


### Livscykel ###

Denna adapter skall endast finnas tills tjänsteproducent kan erbjuda en tjänst som baseras på befintligt RIVTA kontrakt _clinicalprocess:healthcond:actoutcome:GetLaboratoryOrderOutcome_

### Intressenter ###
_Lista över intressenter (användare, system)_

  * Journalsystem Take Care
> Take Care är SLL:s huvudjournalsystem som levereras av Compugroup (CGM).
  * Svenska Reumaregister (RA)
> RA-registret är grunden för ett beslutsstödsystem inom reumatologi som levereras av Carmona.

## Komponentdiagram (systemsamband) ##
## Sekvensdiagram ##

![https://sll-rtjp-4d.googlecode.com/git/docs/images/TakeCareAdapter-Sequence-diagram.png](https://sll-rtjp-4d.googlecode.com/git/docs/images/TakeCareAdapter-Sequence-diagram.png)

  1. Konsument anroper virtuell tjänst GetClinicalChemistryLabOrderOutcome.
  1. Virtuell tjänst delegerar vidare anrop till adaptertjänsten som implementerar kontraktet GetClinicalChemistryLabOrderOutcome.
  1. Adaptern formaterar om det inkommande meddelandet och anropar tjänsten LabRepilesGet hos Takecare/Xchange.
  1. TakeCare sammanställer data och returnerar ett svar tillbaka till adaptern.
  1. Adaptern fomaterar om svarsmeddelandet och returnerar vidare till den virtuella tjänsten.
  1. Virtuell tjänst svarar tillbaka till konsument.

## Funtionalitet ##

### Uppbyggnad ###
Adaptern är byggd som en Mule Applikation och kan köras som en helt fristående komponent i Mule ESB. Adaptern använder sig av SOI-Toolkit för att dra nytta av den extra funktionalitet som detta ramverk ger.

Adapter projektet består av två stycken Maven artifakter, **takecare-adapter-lab-schemas** och **takecare-adapter-lab-service**. Schemas-projektet håller wsdl- och schemadokument för de tjänster som adapteras mot Take Care. Service-projektet är där all logik finns som används för att adaptera. Beroende finns också till clinicalprocess-healthcond-actoutcome-schemas där kontraktet GetClinicalChemistryLabOrderOutcome implementeras.


### Workflow - LabRepliesGet-service ###
![https://sll-rtjp-4d.googlecode.com/git/docs/images/LabRepliesGet-service.png](https://sll-rtjp-4d.googlecode.com/git/docs/images/LabRepliesGet-service.png)

**Flöde**
  1. Tjänsten lyssnar på ett SOAP anrop över HTTP från en konsument.
  1. Inkommande meddelande transformeras om enligt kontraktet för LabRepliesGet.
  1. En SOAP Action header läggs på meddelandet och ett anrop görs sedan till producenten av tjänsten LabRepliesGet över HTTP.
  1. Ett SOAP svar tas emot och transformeras i sin tur för att returneras till konsument.

### Konfiguration ###
Nedan följer en lista över den funktionalitet som kan eller behöver konfigureras. All konfiguration finns i filen _src/main/resources/takecare-adapter-lab-service-config.properties_. Denna filen skall inte ändras vid drift utan vid installation av adaptern skall en konfigurationsfil med namnet _takecare-adapter-lab-service-config-override.properties_ skapas som placeras på lämplig plats (t.ex. $MULE\_HOME/conf) så adaptern kan nå den.

**LABREPLIESGET\_INBOUND\_URL**
Inkommande ändpunkt för adaptern
```
# Exempel: 
LABREPLIESGET_INBOUND_URL=http://localhost:8083/takecare-adapter-lab-service/services/labRepliesGet/v1  
```


**LABREPLIESGET\_OUTBOUND\_URL**
Ändpunkt för tjänsten LabRepliesGet.
```
# Exempel: 
LABREPLIESGET_OUTBOUND_URL=https://xchange.karolinska.se/x2nod.net/lab/lab-v2.asmx
```

**LABREPLIESGET\_EXTERNAL\_USER** +
Värdet på Extern användare som skall användas vid anrop av tjänsten LabRepliesGet
```
# Exempel: 
LABREPLIESGET_EXTERNAL_USER=RTJP_ESB
```

**LABREPLIESGET\_INVOKING\_SYSTEM** +
Värdet på Invoking system som skall användas vid anrop av tjänsten LabRepliesGet
```
# Exempel: 
LABREPLIESGET_INVOKING_SYSTEM=RTJP_ESB
```

**SERVICE\_TIMEOUT\_MS** +
Antalet millisekunder som anropet till tjänsten LabRepliesGet får ta innan en timeout inträffar.
```
# Exempel: 
SERVICE_TIMEOUT_MS=8000
```


## Bygga och installera ##
### Att bygga adaptern ###
**Förutsättning**
För att kunna bygga adaptern behövs följande finnas installerat.
  * Java JDK version 6.x eller senare.
  * Apache Maven V3
  * Git version 1.7 eller senare

Källkoden finns i ett Git 'repository' på Google Code. https://code.google.com/p/sll-rtjp-4d/

  1. Hämta ut källkoden från GoogleCode.
```
$ git clone https://code.google.com/p/sll-rtjp-4d/  
```
  1. Ställ dig i roten på adapterprojektet.
```
$ cd sll-rtjp-4d/adapterservices/riv.clinicalprocess.healthcond.actoutcome/takecare-adapter-lab
```
  1. Bygg källkoden med maven
  1. Kör kommandot _mvn clean package_
```
$ mvn clean package
```
  1. Du skall nu få upp följande text och då har bygget gått bra.
```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```
  1. I katalogen 'takecare-adapter-lab-service/target/' finns nu en Mule file med namnet 'takecare-adapter-lab-service-`<version>`.zip' som är själva adaptern.
```
$ ls takecare-adapter-lab-service/target/*.zip
takecare-adapter-lab-service/target/takecare-adapter-lab-service-1.0.0.zip
```

### Installation och konfiguration ###
Följande steg beskriver hur du installerar adaptern manuellt på en Mule instans som körs i Linux. Självklart fungerar det även att installera på andra operativsystem och med Mule Management Console.

**Förutsättning**
  * Att en Mule är installerad.
  * Att adaptern är bygggd och du har tillgång till binären. För att bygga adaptern, se ovan.
  * Att ett klientcertifikat (SITHS) finns som har behörighet att anropa tjänsten LabRepliesGet i TakeCare/Xchange.
  * Vad som skall anges för parametern 'Extern användare'.
  * Lämpligt värde på attributet 'InvokingSystem'.

**Översikt**
För att installera Adaptern behövs följande aktiviteter utföras.
  * Installera certifikat.
  * Skapa en konfigurationsfil.
  * Driftsätt adaptern i Mule

**Installera**
  1. Installera certifikat.
> Flytta över certifikatet till Mule servern och lägg det på lämpligt ställe t.ex. $MULE\_HOME/certs. Viktigt är att certifikatet är läsbart av Mule när det placeras på servern.
```
$ scp mycertificate.p12 myserver:
$ sudo mv ~/mycerticate.p12 /opt/mule-standalone-3.3.1/certs
```
> Om inte katalogen finns så får du skapa den först.
  1. Skapa en konfigurationsfil
> Logga in på Mule servern och skapa en propertyfil med namnet 'takecare-adapter-lab-service-config-override.properties' i katalogen './conf'.
```
$ cd /opt/mule-standalone-3.3.1
$ sudo nano conf/takecare-adapter-lab-service-config-override.properties
```
> Följande värden kan sättas i konfigurationsfilen.
```
# Default timeout for synchronous services
SERVICE_TIMEOUT_MS=8000

# Properties for service "LabRepliesGet"
# Endpoint to listen at
LABREPLIESGET_INBOUND_URL=http://localhost:8083/takecare-adapter-lab-service/services/labRepliesGet/v1
# URL endpoint for the service LagRepliesGet
LABREPLIESGET_OUTBOUND_URL=https://xchange.karolinska.se/x2nod.net/lab/lab-v2.asmx
# Extern användare to be used in TakeCare/Xchange
LABREPLIESGET_EXTERNAL_USER=
# Invoking system to be used in TakeCare/Xchange
LABREPLIESGET_INVOKING_SYSTEM=

# TODO: Update to reflect your settings
SOITOOLKIT_HTTPS_TLS_KEYSTORE=../certs/mycertificate.p12
SOITOOLKIT_HTTPS_TLS_KEY_TYPE=pkcs12
SOITOOLKIT_HTTPS_TLS_KEYSTORE_PASSWORD=******
SOITOOLKIT_HTTPS_TLS_KEY_PASSWORD=******


## Optional values: You shouldn't need to change these values 
SOITOOLKIT_HTTPS_CLIENT_SO_TIMEOUT=${SERVICE_TIMEOUT_MS}
SOITOOLKIT_HTTPS_TLS_TRUSTSTORE=../certs/truststore.jks
SOITOOLKIT_HTTPS_TLS_TRUSTSTORE_REQUIRE_CLIENT_AUTH=true
SOITOOLKIT_HTTPS_TLS_TRUSTSTORE_PASSWORD=******

# Standard properties for an external ActiveMQ broker, see soitoolkit-mule-jms-connector-activemq-external.xml.
SOITOOLKIT_MULE_AMQ_BROKER_URL=failover:(tcp://localhost:61616)
SOITOOLKIT_MULE_AMQ_MAX_REDELIVERIES=3
SOITOOLKIT_MULE_AMQ_REDELIVERY_DELAY=10000
SOITOOLKIT_MULE_AMQ_NO_OF_CONSUMERS=2

# Default queue names for info end error log-events
SOITOOLKIT_LOG_INFO_QUEUE=SOITOOLKIT.LOG.INFO
SOITOOLKIT_LOG_ERROR_QUEUE=SOITOOLKIT.LOG.ERROR
```
  1. Driftsätt adaptern i Mule
Börja med att föra över filen till Mule servern.
```
$ scp takecare-adapter-lab-service/target/takecare-adapter-lab-service-1.0.0.zip myserver:
```
Kopiera sedan in filen till katalogen './apps'.
```
$ sudo scp ~/takecare-adapter-lab-service-1.0.0.zip ./apps
```

## Utveckling ##
### Krav på utvecklingsmiljö ###
Följande måste finnas installerat:
  * Mule Studio
  * Java JDK
  * Maven
  * Git

Följande programvaror och versionsnummer har använts vid utvecklingen av adaptern.
  * Mule Studio version 1.3.2
  * Java version 1.6.0\_65
  * Apache Maven 3.0.4
  * git version 1.7.10.2

  1. Hämta ut källkoden
```
$ git clone https://code.google.com/p/sll-rtjp-4d/
```
  1. Validera att koden bygger.
```
$ mvn clean package
```
  1. Skapa projekt filer för Eclipse
```
$ mvn eclipse:clean eclipse:eclipse
```
  1. Importera nu in projektet i Mule Studio

## Driftsmiljö och testmiljö ##
(volymer, prestanda, skalning, certifikat, ...)

### Krav på driftsmiljö / plattform ###
Adaptern kräver kräver Mule 3.3 eller senare och Java version 6.x eller 7.**.**

### Felsökning och monitorering ###