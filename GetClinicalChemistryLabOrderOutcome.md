# Introduction #

This service contract is used to retrieve patient lab data from the EMR system to the decision support system.

Service Domain Name: `urn:sll:clinicalprocess:healthcond:actoutcome`

Also see:

  * [WSDL](https://code.google.com/p/sll-rtjp-4d/source/browse/contracts/sll/clinicalprocess/healthcond/actoutcome/schemas/interactions/GetClinicalChemistryLabOrderOutcomeInteraction/GetClinicalChemistryLabOrderOutcomeInteraction_1.0_RIVTABP21.wsdl)
  * [Service Contract Schema](https://code.google.com/p/sll-rtjp-4d/source/browse/contracts/sll/clinicalprocess/healthcond/actoutcome/schemas/interactions/GetClinicalChemistryLabOrderOutcomeInteraction/GetClinicalChemistryLabOrderOutcomeResponder_1.0.xsd)
  * [Service Domain schema](https://code.google.com/p/sll-rtjp-4d/source/browse/contracts/sll/clinicalprocess/healthcond/actoutcome/schemas/core_components/clinicalprocess_healthcond_actoutcome_1.0.xsd)

## Domain shared data types ##

Simple Data Type: `PatientIdType` based on  [string](http://www.w3.org/TR/xmlschema-2/#string)
Simple Data Type: `HealthcareFacilityIdType` based on  [string](http://www.w3.org/TR/xmlschema-2/#string)


## Request ##

Request Type: `GetClinicalChemistryLabOrderOutcomeType`

| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| healthcareFacilityId | [string](http://www.w3.org/TR/xmlschema-2/#string) | The HSA identity of the health care facility in question  | 1..1 |
| patientId | [string](http://www.w3.org/TR/xmlschema-2/#string) |  The personal identity number,  on the format 'YYYYMMDDNNNN'  | 1..1 |
| startDate | [date](http://www.w3.org/TR/xmlschema-2/#date) | The period start date, on the format 'YYYY-MM-DD' | 0..1 |
| endDate | [date](http://www.w3.org/TR/xmlschema-2/#date) | The query period end date on the format 'YYYY-MM-DD' | 0..1 |

#### Request SOAP Example ####
```

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:riv:itintegration:registry:1" xmlns:urn1="urn:sll:clinicalprocess:healthcond:actoutcome:GetClinicalChemistryLabOrderOutcomeResponder:1">
   <soapenv:Header>
      <urn:LogicalAddress>takecare</urn:LogicalAddress>
   </soapenv:Header>
   <soapenv:Body>
      <urn1:GetClinicalChemistryLabOrderOutcome>
         <urn1:healthcareFacilityId>SE2321000016-1HZ3</urn1:healthcareFacilityId>
         <urn1:patientId>193008077723</urn1:patientId>
         <urn1:startTime>2013-01-12T09:00:00</urn1:startTime>
         <urn1:endTime>2013-05-30T09:00:00</urn1:endTime>
      </urn1:GetClinicalChemistryLabOrderOutcome>
   </soapenv:Body>
</soapenv:Envelope>

```

## Response ##

Response Type: `GetClinicalChemistryLabOrderOutcomeResponseType`

| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| resultCode | [string](http://www.w3.org/TR/xmlschema-2/#string) | Any of 'OK, 'ERROR', or 'INFO' | 1..1 |
| comment | [string](http://www.w3.org/TR/xmlschema-2/#string)  | Shall contain a message in plain text describing either an 'ERROR' or other valuable information 'INFO' . If a comment is provided it shall make sense to an end-user. | 0..1 |
| labResults | `LabResultType`, see below for details | Se below for a detailed description, if an ERROR has occurred no records should be expected. If the result is OK no records means there's no results for the given request input. | 0..`*` |

Lab Result Element Type: `LabResultType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| analysis | `Analysis Items`, see below | See detailed description below | 1..`*` |
| instanceId | [string](http://www.w3.org/TR/xmlschema-2/#string) | Identifier for the actual lab request | 1..1 |
| registeredDateTime | [dateTime](http://www.w3.org/TR/xmlschema-2/#dateTime) | Identifier for the actual lab request | 1..1 |
| comment | [string](http://www.w3.org/TR/xmlschema-2/#string) | Any additional comment about the results | 0..1 |

Analysis Items: `Analysis Items` is an embedded declaration of the element analysis
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| instanceId | [string](http://www.w3.org/TR/xmlschema-2/#string)  | Identifier for the analysis item | 1..1 |
| code | `CodeType`, see below | See detailed description below | 1..1 |
| takenDateTime | [dateTime](http://www.w3.org/TR/xmlschema-2/#dateTime)  | See detailed description below | 1..1 |
| comment | [string](http://www.w3.org/TR/xmlschema-2/#string)| Any additional comment | 0..1 |
| sampleType | [string](http://www.w3.org/TR/xmlschema-2/#string)| Any type of sample | 0..1 |
| resultSummary | [string](http://www.w3.org/TR/xmlschema-2/#string)| Summary of result | 0..1 |
| unitOfMeasure | [string](http://www.w3.org/TR/xmlschema-2/#string)| The actual used unit | 0..1 |
| pathological | [boolean](http://www.w3.org/TR/xmlschema-2/#boolean) | Indicates a pathological result or not | 0..1 |
| reference | [string](http://www.w3.org/TR/xmlschema-2/#string) | Any additional reference | 0..1 |

Code Type: `CodeType`

| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| code | [string](http://www.w3.org/TR/xmlschema-2/#string)  | The code of the sample | 0..1 |
| displayName | [string](http://www.w3.org/TR/xmlschema-2/#string)  | The full display name of the actual code  | 0..1 |

#### Response SOAP Example ####
```

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <tns:GetClinicalChemistryLabOrderOutcomeResponse xmlns:tns="urn:sll:clinicalprocess:healthcond:actoutcome:GetClinicalChemistryLabOrderOutcomeResponder:1" xmlns:core="urn:sll:clinicalprocess:healthcond:actoutcome:1" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
         <tns:resultCode>OK</tns:resultCode>
         <tns:labResults>
            <tns:analysis>
               <tns:instanceId>SE2321000016-6RK5.193008077723.66.201302221156.NPU03230</tns:instanceId>
               <tns:code>
                  <tns:code>NPU03230</tns:code>
                  <tns:displayName>P-Kalium</tns:displayName>
               </tns:code>
               <tns:takenDateTime>2013-02-22T09:21:00</tns:takenDateTime>
               <tns:resultSummary>4,5</tns:resultSummary>
               <tns:unitOfMeasure>mmol/L</tns:unitOfMeasure>
               <tns:pathological>0</tns:pathological>
               <tns:reference>3,5-4,6</tns:reference>
            </tns:analysis>
            <tns:analysis>
               <tns:instanceId>SE2321000016-6RK5.193008077723.66.201302221156.NPU03096</tns:instanceId>
               <tns:code>
                  <tns:code>NPU03096</tns:code>
                  <tns:displayName>P-Fosfat</tns:displayName>
               </tns:code>
               <tns:takenDateTime>2013-02-22T09:21:00</tns:takenDateTime>
               <tns:resultSummary>0,90</tns:resultSummary>
               <tns:unitOfMeasure>mmol/L</tns:unitOfMeasure>
               <tns:pathological>0</tns:pathological>
               <tns:reference>0,80-1,5</tns:reference>
            </tns:analysis>
            <tns:analysis>
               <tns:instanceId>SE2321000016-6RK5.193008077723.66.201302221156.NPU02593</tns:instanceId>
               <tns:code>
                  <tns:code>NPU02593</tns:code>
                  <tns:displayName>B-Leukocyter</tns:displayName>
               </tns:code>
               <tns:takenDateTime>2013-02-22T09:21:00</tns:takenDateTime>
               <tns:resultSummary>5,0</tns:resultSummary>
               <tns:unitOfMeasure>x10(9)/L</tns:unitOfMeasure>
               <tns:pathological>0</tns:pathological>
               <tns:reference>3,5-8,8</tns:reference>
            </tns:analysis>
            <tns:analysis>
               <tns:instanceId>SE2321000016-6RK5.193008077723.66.201302221156.NPU01960</tns:instanceId>
               <tns:code>
                  <tns:code>NPU01960</tns:code>
                  <tns:displayName>B-Erytrocyter</tns:displayName>
               </tns:code>
               <tns:takenDateTime>2013-02-22T09:21:00</tns:takenDateTime>
               <tns:resultSummary>2,5</tns:resultSummary>
               <tns:unitOfMeasure>x10(12)/L</tns:unitOfMeasure>
               <tns:pathological>1</tns:pathological>
               <tns:reference>3,9-5,2</tns:reference>
            </tns:analysis>
            <tns:analysis>
               <tns:instanceId>SE2321000016-6RK5.193008077723.66.201302221156.SWE05074</tns:instanceId>
               <tns:code>
                  <tns:code>SWE05074</tns:code>
                  <tns:displayName>B-Hemoglobin</tns:displayName>
               </tns:code>
               <tns:takenDateTime>2013-02-22T09:21:00</tns:takenDateTime>
               <tns:resultSummary>152</tns:resultSummary>
               <tns:unitOfMeasure>g/L</tns:unitOfMeasure>
               <tns:pathological>0</tns:pathological>
               <tns:reference>117-153</tns:reference>
            </tns:analysis>
            <tns:analysis>
               <tns:instanceId>SE2321000016-6RK5.193008077723.66.201302221156.NPU01961</tns:instanceId>
               <tns:code>
                  <tns:code>NPU01961</tns:code>
                  <tns:displayName>B-EVF</tns:displayName>
               </tns:code>
               <tns:takenDateTime>2013-02-22T09:21:00</tns:takenDateTime>
               <tns:resultSummary>0,50</tns:resultSummary>
               <tns:pathological>1</tns:pathological>
               <tns:reference>0,35-0,46</tns:reference>
            </tns:analysis>
            <tns:analysis>
               <tns:instanceId>SE2321000016-6RK5.193008077723.66.201302221156.NPU01944</tns:instanceId>
               <tns:code>
                  <tns:code>NPU01944</tns:code>
                  <tns:displayName>Erc(B)-MCV</tns:displayName>
               </tns:code>
               <tns:takenDateTime>2013-02-22T09:21:00</tns:takenDateTime>
               <tns:resultSummary>85</tns:resultSummary>
               <tns:unitOfMeasure>fL</tns:unitOfMeasure>
               <tns:pathological>0</tns:pathological>
               <tns:reference>82-98</tns:reference>
            </tns:analysis>
            <tns:analysis>
               <tns:instanceId>SE2321000016-6RK5.193008077723.66.201302221156.SWE05129</tns:instanceId>
               <tns:code>
                  <tns:code>SWE05129</tns:code>
                  <tns:displayName>Erc(B)-MCH</tns:displayName>
               </tns:code>
               <tns:takenDateTime>2013-02-22T09:21:00</tns:takenDateTime>
               <tns:resultSummary>25</tns:resultSummary>
               <tns:unitOfMeasure>pg</tns:unitOfMeasure>
               <tns:pathological>1</tns:pathological>
               <tns:reference>27-33</tns:reference>
            </tns:analysis>
            <tns:analysis>
               <tns:instanceId>SE2321000016-6RK5.193008077723.66.201302221156.SWE05128</tns:instanceId>
               <tns:code>
                  <tns:code>SWE05128</tns:code>
                  <tns:displayName>Erc(B)-MCHC</tns:displayName>
               </tns:code>
               <tns:takenDateTime>2013-02-22T09:21:00</tns:takenDateTime>
               <tns:resultSummary>350</tns:resultSummary>
               <tns:unitOfMeasure>g/L</tns:unitOfMeasure>
               <tns:pathological>0</tns:pathological>
               <tns:reference>317-357</tns:reference>
            </tns:analysis>
            <tns:analysis>
               <tns:instanceId>SE2321000016-6RK5.193008077723.66.201302221156.NPU03568</tns:instanceId>
               <tns:code>
                  <tns:code>NPU03568</tns:code>
                  <tns:displayName>B-Trombocyter</tns:displayName>
               </tns:code>
               <tns:takenDateTime>2013-02-22T09:21:00</tns:takenDateTime>
               <tns:resultSummary>360</tns:resultSummary>
               <tns:unitOfMeasure>x10(9)/L</tns:unitOfMeasure>
               <tns:pathological>0</tns:pathological>
               <tns:reference>165-387</tns:reference>
            </tns:analysis>
            <tns:analysis>
               <tns:instanceId>SE2321000016-6RK5.193008077723.66.201302221156.NPU19763</tns:instanceId>
               <tns:code>
                  <tns:code>NPU19763</tns:code>
                  <tns:displayName>S-Ferritin (DxI)</tns:displayName>
               </tns:code>
               <tns:takenDateTime>2013-02-22T09:21:00</tns:takenDateTime>
               <tns:resultSummary>25</tns:resultSummary>
               <tns:unitOfMeasure>mikrog/L</tns:unitOfMeasure>
               <tns:pathological>0</tns:pathological>
               <tns:reference>20-250</tns:reference>
            </tns:analysis>
            <tns:analysis>
               <tns:instanceId>SE2321000016-6RK5.193008077723.66.201302221156.NPU01685</tns:instanceId>
               <tns:code>
                  <tns:code>NPU01685</tns:code>
                  <tns:displayName>P-PK(INR)</tns:displayName>
               </tns:code>
               <tns:takenDateTime>2013-02-22T09:21:00</tns:takenDateTime>
               <tns:comment>Riktområde (vid AVK-beh.) 2,1 - 3,0. Provet har möjligen aktiverats före$$NL$$analys, detta kan bero på; 1. Mkt snabb koagulation. 2. Fel vid$$NL$$provtagning/hantering.</tns:comment>
               <tns:resultSummary>&lt;0,8</tns:resultSummary>
               <tns:unitOfMeasure>INR</tns:unitOfMeasure>
               <tns:pathological>0</tns:pathological>
               <tns:reference>&lt;1,2</tns:reference>
            </tns:analysis>
            <tns:instanceId>SE2321000016-6RK5.193008077723.66</tns:instanceId>
            <tns:registeredDateTime>2013-02-22T11:56:38</tns:registeredDateTime>
            <tns:comment>Svar på beställning 28238642</tns:comment>
         </tns:labResults>
      </tns:GetClinicalChemistryLabOrderOutcomeResponse>
   </soap:Body>
</soap:Envelope>

```