# Introduction #

Service contracts to exchange medical record and research data with the decision support system (RA).

Service Domain Name: `urn:sll:clinicalprocess:healthcond:description`

Also see:

  * [GetRaDSData WSDL](https://code.google.com/p/sll-rtjp-4d/source/browse/contracts/sll/clinicalprocess/healthcond/description/schemas/interactions/GetRaDSDataInteraction/GetRaDSDataInteraction_1.0_RIVTABP21.wsdl)
  * [GetRaDSData Service Contract Schema](https://code.google.com/p/sll-rtjp-4d/source/browse/contracts/sll/clinicalprocess/healthcond/description/schemas/interactions/GetRaDSDataInteraction/GetRaDSDataResponder_1.0.xsd)
  * [RegisterRaDSData WSDL](https://code.google.com/p/sll-rtjp-4d/source/browse/contracts/sll/clinicalprocess/healthcond/description/schemas/interactions/RegisterRaDSDataInteraction/RegisterRaDSDataInteraction_1.0_RIVTABP21.wsdll)
  * [RegisterRaDSData Service Contract Schema](https://code.google.com/p/sll-rtjp-4d/source/browse/contracts/sll/clinicalprocess/healthcond/description/schemas/interactions/RegisterRaDSDataInteraction/RegisterRaDSDataResponder_1.0.xsd)
  * [Service Domain Schema](https://code.google.com/p/sll-rtjp-4d/source/browse/contracts/sll/clinicalprocess/healthcond/description/schemas/core_components/clinicalprocess_healthcond_description_1.0.xsd)

## Domain shared data types ##

Simple Data Types:

  * `PatientIdType` based on  [string](http://www.w3.org/TR/xmlschema-2/#string)
  * `PersonalIdentityNumberType` based on based on  [string](http://www.w3.org/TR/xmlschema-2/#string)
  * `HSAIdType` based on  [string](http://www.w3.org/TR/xmlschema-2/#string)

Complex Type `PatientType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| patientId | [string](http://www.w3.org/TR/xmlschema-2/#date) | The patient identity on the format 'YYYYMMDDNNNN'  | 1..1 |
| givenName |  [string](http://www.w3.org/TR/xmlschema-2/#string)  | Given name | 0..1 |
| surname  | [string](http://www.w3.org/TR/xmlschema-2/#string)  | Surname | 0..1 |

## Service Contract GetRaDSData :: Request ##

Request Type: `GetRaDSDataType`

| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| patientId | [string](http://www.w3.org/TR/xmlschema-2/#string) | The patient identity on the format 'YYYYMMDDNNNN'  | 1..1 |
| date | [date](http://www.w3.org/TR/xmlschema-2/#date) | The actual date or omitted for all | 0..1 |


#### Request SOAP Example ####
```

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:int="urn:riv:itintegration:registry:1" xmlns:tns="urn:sll:clinicalprocess:healthcond:description:GetRaDSDataResponder:1">
   <soapenv:Header>
      <int:LogicalAddress>TEST</int:LogicalAddress>
   </soapenv:Header>
   <soapenv:Body>
      <tns:GetRaDSData>
         <tns:patientId>191212121212</tns:patientId>
         <tns:date>2013-09-04</tns:date>
      </tns:GetRaDSData>
   </soapenv:Body>
</soapenv:Envelope>

```

## Service Contract GetRaDSData :: Response ##


Response Type: `GetRaDSDataResponseType`

| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| `ResultCode` | [string](http://www.w3.org/TR/xmlschema-2/#string) | One of 'OK' or 'ERROR'.  | 1..1 |
| comment | [string](http://www.w3.org/TR/xmlschema-2/#string)  | On an error the comment shall describe a cause in plain text, in such a way that it makes sense to an end-user. | 0..1 |
| registryData | `RegistryDataType`, see below for details | Domain specific  datatype, see detailed description below. If an ERROR has occurred this element is omitted. If the result code is OK but and this element is missing it  means there's no results for the given request input.  | 0..1 |

Registry Result Element Type: `RegistryDataType`

| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| inclusionCriteria | `InclusionCriteriaType`, see below | Inclusion criteria  | 0..1 |
| responsiblePhysician | `PersonType`, see below | Responsible physician during visit  | 0..1 |
| hospital | `LocationType`, see below | Hospital and unit  | 0..1 |
| diagnosis | List of diagnose :: `DiagnoseType`, see below | Diagnosis and onset date  | 0..1 |
| dominantHand | [string](http://www.w3.org/TR/xmlschema-2/#string)  | Left or Right handed  | 0..1 |
| weight | [float](http://www.w3.org/TR/xmlschema-2/#float)  | Weight in Kg  | 0..1 |
| height | [float](http://www.w3.org/TR/xmlschema-2/#float)  | Height in cm  | 0..1 |
| menopaus | [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True if it has begun, otherwise False   | 0..1 |
| menopausAge | [integer](http://www.w3.org/TR/xmlschema-2/#integer)  |  Age, entering menopaus  | 0..1 |
| studyProjects | [string](http://www.w3.org/TR/xmlschema-2/#string)  |  The study or project name  | 0..1 |
| visitDate | [date](http://www.w3.org/TR/xmlschema-2/#date)  |  The date of visit  | 0..1 |
| visitDoctor | `PersonType`, see below | Doctor handling visit  | 0..1 |
| SR | [float](http://www.w3.org/TR/xmlschema-2/#float) | Sedimentation Rate  Value | 0..1 |
| CRP | [float](http://www.w3.org/TR/xmlschema-2/#float) | C-Reactive Protein Value  | 0..1 |
| workability |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Workability rate  | 0..1 |
| pain |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Pain rate  | 0..1 |
| HAQ |  [float](http://www.w3.org/TR/xmlschema-2/#float) | Disability scale | 0..1 |
| globalHealth |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Overall health rate  | 0..1 |
| tenderJoints |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Tender joint rate (28) | 0..1 |
| swollenJoints |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Swollen joint rate (28) | 0..1 |
| DAS | [float](http://www.w3.org/TR/xmlschema-2/#float) | Disease Activity Score  | 0..1 |
| doctorsGlobal |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Doctors overall rate  | 0..1 |
| trombocyter |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Thrombocytes value  | 0..1 |
| RF |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Reumatoid factor  | 0..1 |
| antiCcp |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Anti-cyclic citrullinated peptide antibodies  | 0..1 |
| xrayDate |  [date](http://www.w3.org/TR/xmlschema-2/#date) | Date for X-Ray  | 0..1 |
| erosion |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) |  X-Ray Erosion Score  | 0..1 |
| progress |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) |  X-Ray Progress  | 0..1 |
| drugs |  List of drug :: `DrugType`, see below. | A list of prescribed drugs | 0..1 |
| smoking | [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True if patient is a smoker, otherwise False   | 0..1 |
| tenderJoints68 |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Tender joint rate (68)  | 0..1 |
| swollenJoints68 |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Swollen joint rate (68) | 0..1 |
| basdaiScore |  [float](http://www.w3.org/TR/xmlschema-2/#float) | Bath Ankylosing Spondylitis Disease Activity Index Score | 0..1 |
| basfiScore |  [float](http://www.w3.org/TR/xmlschema-2/#float) | Bath Ankylosing Spondylitis Functional Index Score | 0..1 |

Element Type: `InclusionCriteriaType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| arthritisIn3Joints |  [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True if arthritis in at least 3 joints. | 0..1 |
| arthritisInHand |  [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True if arthritis in a hand. | 0..1 |
| morningStiffness |  [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True when stiffness occurs in the morning. | 0..1 |
| rheumaticNoduli |  [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True if rheumatic noduls exists . | 0..1 |
| rheumatoidFaktorPos |  [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True if rheumatic factor is positive. | 0..1 |
| xrayChanges |  [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True if X-Ray result has changed. | 0..1 |
| symmetricArthritis |  [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True if arthritis is symmetric. | 0..1 |

Element Type: `PersonType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| ID |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Personal identity (civic) | 0..1 |
| givenName |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Given name | 0..1 |
| surname |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Surname | 0..1 |
| HSAID |  [string](http://www.w3.org/TR/xmlschema-2/#string) | HSA Identity number | 0..1 |
| profession |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Professional title | 0..1 |

Element Type: `LocationType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| provider |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Health care provider/giver | 0..1 |
| unit |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Health care unit name | 0..1 |
| HSAID |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Health care unit HSA Identity number | 0..1 |

Element Type: `DiagnoseType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| name |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Name of diagnosis  | 0..1 |
| onsetDate |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Diagnosis onset date  | 0..1 |

Element Type: `DrugType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| name |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Name of drug  | 0..1 |
| dose |  [float](http://www.w3.org/TR/xmlschema-2/#float) | The dose in milligrams (mg) | 0..1 |
| startDate |  [date](http://www.w3.org/TR/xmlschema-2/#date) | Start date | 0..1 |
| endDate |  [date](http://www.w3.org/TR/xmlschema-2/#date) | End date | 0..1 |
| endCause |  [string](http://www.w3.org/TR/xmlschema-2/#string) | End due to cause | 0..1 |
| type |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Type of drug | 0..1 |
| interval |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Interval/frequency for dose | 0..1 |

#### Response SOAP Example ####
```

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <GetRaDSDataResponse xmlns="urn:sll:clinicalprocess:healthcond:description:GetRaDSDataResponder:1" xmlns:ns2="urn:riv:itintegration:registry:1" xmlns:ns3="urn:sll:clinicalprocess:healthcond:description:1">
         <registryData>
            <inclusionCriteria>
               <arthritisIn3Joints>false</arthritisIn3Joints>
               <arthritisInHand>true</arthritisInHand>
               <morningStiffness>true</morningStiffness>
               <rheumaticNoduli>true</rheumaticNoduli>
               <rheumatoidFaktorPos>true</rheumatoidFaktorPos>
               <xrayChanges>false</xrayChanges>
               <symmetricArthritis>true</symmetricArthritis>
            </inclusionCriteria>
            <responsiblePhysician>
               <ID>id-368</ID>
               <givenName>Bengt</givenName>
               <surname>Bengtsson</surname>
               <HSAID>HSAID-4751</HSAID>
               <profession>Doctor</profession>
            </responsiblePhysician>
            <hospital>
               <provider>Provider-2433</provider>
               <unit>Enhet-2794</unit>
               <HSAID>HSAID-780</HSAID>
            </hospital>
            <diagnosis>
               <diagnose>
                  <name>Diagnose-256</name>
                  <ICD10>ICD10-26</ICD10>
                  <onsetDate>2013-10-30+01:00</onsetDate>
               </diagnose>
               <diagnose>
                  <name>Diagnose-310</name>
                  <ICD10>ICD10-21</ICD10>
                  <onsetDate>2013-10-30+01:00</onsetDate>
               </diagnose>
            </diagnosis>
            <dominantHand>right</dominantHand>
            <weight>68.5</weight>
            <height>165.6</height>
            <menopaus>false</menopaus>
            <menopausAge>44</menopausAge>
            <studyProjects>
               <studyProject>Project-0</studyProject>
            </studyProjects>
            <visitDate>2013-10-30+01:00</visitDate>
            <visitDoctor>
               <ID>id-96</ID>
               <givenName>Bengt</givenName>
               <surname>Bengtsson</surname>
               <HSAID>HSAID-1228</HSAID>
               <profession>Doctor</profession>
            </visitDoctor>
            <SR>3.4</SR>
            <CRP>3.4</CRP>
            <workability>88</workability>
            <pain>94</pain>
            <HAQ>1.8</HAQ>
            <globalHealth>12</globalHealth>
            <tenderJoints>22</tenderJoints>
            <swollenJoints>27</swollenJoints>
            <DAS>10.0</DAS>
            <doctorsGlobal>52</doctorsGlobal>
            <trombocyter>8</trombocyter>
            <RF>RF value</RF>
            <antiCcp>4</antiCcp>
            <xrayDate>2013-10-30+01:00</xrayDate>
            <erosion>6</erosion>
            <progress>82</progress>
            <drugs>
               <drug>
                  <name>A name - 357</name>
                  <dose>14.6</dose>
                  <startDate>2013-10-30+01:00</startDate>
                  <endDate>2013-10-30+01:00</endDate>
                  <endCause>endcause42</endCause>
                  <type>A type - 10</type>
               </drug>
            </drugs>
            <smoking>true</smoking>
            <tenderJoints68>11</tenderJoints68>
            <swollenJoints66>10</swollenJoints66>
            <basdaiScore>6.2</basdaiScore>
            <basfiScore>4.0</basfiScore>
         </registryData>
      </GetRaDSDataResponse>
   </soap:Body>
</soap:Envelope>

```

## Service Contract RegisterRaDSData :: Request ##

Request Type: `RegisterRaDSDataType`

| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| patient | `PatientType`, see below for details  | Patient information  | 1..1 |
| registryData | `RegistryDataType`, see below for details  | Data to be registred  | 1..1 |

Element Type: `RegistryDataType`

| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| inclusionCriteria | `InclusionCriteriaType`, see below | Inclusion criteria  | 0..1 |
| responsiblePhysician | `PersonType`, see below | Responsible physician during visit  | 0..1 |
| hospital | `LocationType`, see below | Hospital and unit  | 0..1 |
| diagnosis | List of diagnose :: `DiagnoseType`, see below | Diagnosis and onset date  | 0..1 |
| dominantHand | [string](http://www.w3.org/TR/xmlschema-2/#string)  | Left or Right handed  | 0..1 |
| height | [float](http://www.w3.org/TR/xmlschema-2/#float)  | Height in cm  | 0..1 |
| weight | [float](http://www.w3.org/TR/xmlschema-2/#float)  | Weight in Kg  | 0..1 |
| menopaus | [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True if it has begun, otherwise False   | 0..1 |
| menopausAge | [integer](http://www.w3.org/TR/xmlschema-2/#integer)  |  Age, entering menopaus  | 0..1 |
| studyProjects | [string](http://www.w3.org/TR/xmlschema-2/#string)  |  The study or project name  | 0..1 |
| visitDate | [date](http://www.w3.org/TR/xmlschema-2/#date)  |  The date of visit  | 0..1 |
| visitDoctor | `PersonType`, see below | Doctor handling visit  | 0..1 |
| SR | [float](http://www.w3.org/TR/xmlschema-2/#float) | Sedimentation Rate  Value | 0..1 |
| CRP | [float](http://www.w3.org/TR/xmlschema-2/#float) | C-Reactive Protein Value  | 0..1 |
| workability |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Workability rate  | 0..1 |
| pain |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Pain rate  | 0..1 |
| HAQ |  [float](http://www.w3.org/TR/xmlschema-2/#float) | Disability scale | 0..1 |
| globalHealth |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Overall health rate  | 0..1 |
| tenderJoints |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Tender joint rate (28) | 0..1 |
| swollenJoints |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Swollen joint rate (28) | 0..1 |
| DAS | [float](http://www.w3.org/TR/xmlschema-2/#float) | Disease Activity Score  | 0..1 |
| doctorsGlobal |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Doctors overall rate  | 0..1 |
| trombocytes |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Thrombocytes value  | 0..1 |
| RF |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Reumatoid factor  | 0..1 |
| antiCcp |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Anti-cyclic citrullinated peptide antibodies  | 0..1 |
| xrayDate |  [date](http://www.w3.org/TR/xmlschema-2/#date) | Date for X-Ray  | 0..1 |
| erosion |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) |  X-Ray Erosion Score  | 0..1 |
| progress |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) |  X-Ray Progress  | 0..1 |
| drugs |  List of drug :: `DrugType`, see below. | A list of prescribed drugs | 0..1 |
| smoking | [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True if patient is a smoker, otherwise False   | 0..1 |
| tenderJoints68 |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Tender joint rate (68)  | 0..1 |
| swollenJoints66 |  [integer](http://www.w3.org/TR/xmlschema-2/#integer) | Swollen joint rate (68) | 0..1 |
| basdaiScore |  [float](http://www.w3.org/TR/xmlschema-2/#float) | Bath Ankylosing Spondylitis Disease Activity Index Score | 0..1 |
| basfiScore |  [float](http://www.w3.org/TR/xmlschema-2/#float) | Bath Ankylosing Spondylitis Functional Index Score | 0..1 |

Element Type: `InclusionCriteriaType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| arthritisIn3Joints |  [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True if arthritis in at least 3 joints. | 0..1 |
| arthritisInHand |  [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True if arthritis in a hand. | 0..1 |
| morningStiffness |  [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True when stiffness occurs in the morning. | 0..1 |
| rheumaticNoduli |  [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True if rheumatic noduls exists . | 0..1 |
| rheumatoidFaktorPos |  [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True if rheumatic factor is positive. | 0..1 |
| xrayChanges |  [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True if X-Ray result has changed. | 0..1 |
| symmetricArthritis |  [boolean](http://www.w3.org/TR/xmlschema-2/#boolean)  | True if arthritis is symmetric. | 0..1 |

Element Type: `PersonType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| ID |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Personal identity (civic) | 0..1 |
| givenName |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Given name | 0..1 |
| surname |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Surname | 0..1 |
| HSAID |  [string](http://www.w3.org/TR/xmlschema-2/#string) | HSA Identity number | 0..1 |
| profession |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Professional title | 0..1 |

Element Type: `LocationType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| provider |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Health care provider/giver | 0..1 |
| unit |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Health care unit name | 0..1 |
| HSAID |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Health care unit HSA Identity number | 0..1 |

Element Type: `DiagnoseType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| name |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Name of diagnosis  | 0..1 |
| name |  [string](http://www.w3.org/TR/xmlschema-2/#string) | the 10th revision of the International Statistical Classification of Diseases and Related Health Problems  | 0..1 |
| onsetDate |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Diagnosis onset date  | 0..1 |


Element Type: `DrugType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| name |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Name of drug  | 0..1 |
| dose |  [float](http://www.w3.org/TR/xmlschema-2/#float) | The dose in milligrams (mg) | 0..1 |
| startDate |  [date](http://www.w3.org/TR/xmlschema-2/#date) | Start date | 0..1 |
| endDate |  [date](http://www.w3.org/TR/xmlschema-2/#date) | End date | 0..1 |
| endCause |  [string](http://www.w3.org/TR/xmlschema-2/#string) | End due to cause | 0..1 |
| type |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Type of drug | 0..1 |
| interval |  [string](http://www.w3.org/TR/xmlschema-2/#string) | Interval/frequency for dose | 0..1 |


#### Request SOAP Example ####
```

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:urn="urn:riv:itintegration:registry:1" 
                  xmlns:tns="urn:sll:clinicalprocess:healthcond:description:RegisterRaDSDataResponder:1" 
                  xmlns:core="urn:sll:clinicalprocess:healthcond:description:1">
   <soapenv:Header>
      <urn:LogicalAddress>TEST</urn:LogicalAddress>
   </soapenv:Header>
   <soapenv:Body>
    <tns:RegisterRaDSData>
      <tns:patient>
        <core:patientId>191212121212</core:patientId>
        <core:givenName>Tolvan</core:givenName>
        <core:surname>Tolvansson</core:surname>
      </tns:patient>
      <tns:registryData>
        <tns:inclusionCriteria>
          <tns:arthritisInHand>true</tns:arthritisInHand>
          <tns:morningStiffness>true</tns:morningStiffness>
          <tns:rheumaticNoduli>false</tns:rheumaticNoduli>
          <tns:rheumatoidFaktorPos>true</tns:rheumatoidFaktorPos>
          <tns:xrayChanges>false</tns:xrayChanges>
          <tns:symmetricArthritis>true</tns:symmetricArthritis>
        </tns:inclusionCriteria>
        <tns:responsiblePhysician>
          <tns:ID>191212121212</tns:ID>
          <tns:givenName>Sven</tns:givenName>
          <tns:surname>Larsson</tns:surname>
          <tns:HSAID>HSAID0</tns:HSAID>
          <tns:profession>profession0</tns:profession>
        </tns:responsiblePhysician>
        <tns:hospital>
          <tns:provider>provider0</tns:provider>
          <tns:unit>unit0</tns:unit>
          <tns:HSAID>HSAID1</tns:HSAID>
        </tns:hospital>
        <tns:diagnosis>
          <tns:diagnose>
            <tns:name>name0</tns:name>
            <tns:onsetDate>2006-05-04</tns:onsetDate>
          </tns:diagnose>
          <tns:diagnose>
            <tns:name>name1</tns:name>
            <tns:onsetDate>2006-05-04</tns:onsetDate>
          </tns:diagnose>
        </tns:diagnosis>
        <tns:dominantHand>dominantHand0</tns:dominantHand>
        <tns:weight>0</tns:weight>
        <tns:height>0</tns:height>
        <tns:menopaus>false</tns:menopaus>
        <tns:menopausAge>0</tns:menopausAge>
        <tns:studyProjects>
          <tns:studyProject>Ny koppling</tns:studyProject>
          <tns:studyProject>Ny studie</tns:studyProject>
        </tns:studyProjects>
        <tns:visitDate>2011-01-14</tns:visitDate>
        <tns:visitDoctor>
          <tns:ID>191111111111</tns:ID>
          <tns:givenName>Thomas</tns:givenName>
          <tns:surname>Jansson</tns:surname>
          <tns:HSAID>HSAID2</tns:HSAID>
          <tns:profession>profession1</tns:profession>
        </tns:visitDoctor>
        <tns:CRP>0</tns:CRP>
        <tns:HAQ>2.5</tns:HAQ>
        <tns:globalHealth>0</tns:globalHealth>
        <tns:tenderJoints>0</tns:tenderJoints>
        <tns:swollenJoints>10</tns:swollenJoints>
        <tns:DAS>0</tns:DAS>
        <tns:doctorsGlobal>0</tns:doctorsGlobal>
        <tns:thrombocytes>0</tns:thrombocytes>
        <tns:RF>RF0</tns:RF>
        <tns:antiCcp>0</tns:antiCcp>
        <tns:xrayDate>2006-05-04</tns:xrayDate>
        <tns:erosion>0</tns:erosion>
        <tns:progress>0</tns:progress>
        <tns:drugs>
          <tns:drug>
            <tns:name>BioXtreme</tns:name>
            <tns:startDate>2010-01-11</tns:startDate>
            <tns:type>Biological</tns:type>
            <tns:interval>3</tns:interval>
          </tns:drug>
          <tns:drug>
            <tns:name>Betapred</tns:name>
            <tns:dose>500</tns:dose>
            <tns:startDate>2010-01-01</tns:startDate>
            <tns:endDate>2012-12-31</tns:endDate>
            <tns:endCause>endCause1</tns:endCause>
            <tns:type>DMARD</tns:type>
            <tns:interval>3</tns:interval>
          </tns:drug>
          <tns:drug>
            <tns:name>Trial</tns:name>
            <tns:startDate>2010-01-01</tns:startDate>
            <tns:type>DMARD</tns:type>
            <tns:interval>3</tns:interval>
          </tns:drug>
        </tns:drugs>
        <tns:smoking>false</tns:smoking>
        <tns:tenderJoints68>0</tns:tenderJoints68>
        <tns:swollenJoints66>0</tns:swollenJoints66>
        <tns:basdaiScore>0</tns:basdaiScore>
        <tns:basfiScore>0</tns:basfiScore>
      </tns:registryData>
    </tns:RegisterRaDSData>
   </soapenv:Body>
</soapenv:Envelope>

```

## Service Contract RegisterRaDSData :: Response ##


Response Type: `RegisterRaDSDataResponseType`

| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| `ResultCode` | [string](http://www.w3.org/TR/xmlschema-2/#string) | One of 'OK' or 'ERROR'.  | 1..1 |
| comment | [string](http://www.w3.org/TR/xmlschema-2/#string)  | On an error the comment shall describe a cause in plain text, in such a way that it makes sense to an end-user. | 0..1 |
| registryData | `RegistryDataType`, see below for details | Domain specific  datatype, see detailed description below. If an ERROR has occurred this element is omitted. If the result code is OK but and this element is missing it  means there's no results for the given request input.  | 0..1 |


#### Response SOAP Example ####
```

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Header>
      <mule:header xmlns:mule="http://www.muleumo.org/providers/soap/1.0">
         <mule:MULE_CORRELATION_ID>262d0d86-408d-11e3-a8b5-f17bfe2b6e8e</mule:MULE_CORRELATION_ID>
         <mule:MULE_CORRELATION_GROUP_SIZE>1</mule:MULE_CORRELATION_GROUP_SIZE>
         <mule:MULE_CORRELATION_SEQUENCE>-1</mule:MULE_CORRELATION_SEQUENCE>
      </mule:header>
   </soap:Header>
   <soap:Body>
      <ns2:RegisterRaDSDataResponse xmlns="urn:sll:clinicalprocess:healthcond:description:1" xmlns:ns2="urn:sll:clinicalprocess:healthcond:description:RegisterRaDSDataResponder:1" xmlns:ns3="urn:riv:itintegration:registry:1">
        <ns2:ResultCode>OK</ns2:ResultCode>
      </ns2:RegisterRaDSDataResponse>
   </soap:Body>
</soap:Envelope>

```