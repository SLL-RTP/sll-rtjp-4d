GetMicroBiologiLabOrderOutcome.wiki

#summary Service Contract Description

# Introduction #

This service contract is used to retrieve patient lab data from the Research system to the decision support system.

Service Domain Name: `urn:sll:clinicalprocess:healthcond:actoutcome`

Also see:

  * [WSDL](https://code.google.com/p/sll-rtjp-4d/source/browse/contracts/sll/clinicalprocess/healthcond/actoutcome/schemas/interactions/GetMicroBiologiLabOrderOutcomeInteraction/GetMicroBiologiLabOrderOutcomeInteraction_1.0_RIVTABP21.wsdl)
  * [Service Contract Schema](https://code.google.com/p/sll-rtjp-4d/source/browse/contracts/sll/clinicalprocess/healthcond/actoutcome/schemas/interactions/GetMicroBiologiLabOrderOutcomeInteraction/GetMicroBiologiLabOrderOutcomeResponder_1.0.xsd)
  * [Service Domain schema](https://code.google.com/p/sll-rtjp-4d/source/browse/contracts/sll/clinicalprocess/healthcond/actoutcome/schemas/core_components/clinicalprocess_healthcond_actoutcome_1.0.xsd)

## Domain shared data types ##

Simple Data Type: `PatientIdType` based on  [string](http://www.w3.org/TR/xmlschema-2/#string)
Simple Data Type: `HealthcareFacilityIdType` based on  [string](http://www.w3.org/TR/xmlschema-2/#string)


## Request ##

Request Type: `GetMicroBiologiLabOrderOutcomeType`

| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| patientId | [string](http://www.w3.org/TR/xmlschema-2/#string) |  The personal identity number,  on the format 'YYYYMMDDNNNN'  | 1..1 |

#### Request SOAP Example ####
```

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:riv:itintegration:registry:1" xmlns:tns="urn:sll:clinicalprocess:healthcond:actoutcome:GetMicroBiologiLabOrderOutcomeResponder:1">
   <soapenv:Header>
      <urn:LogicalAddress>TEST</urn:LogicalAddress>
   </soapenv:Header>
   <soapenv:Body>
      <tns:GetMicroBiologiLabOrderOutcome>
         <tns:patientId>191212121212</tns:patientId>
      </tns:GetMicroBiologiLabOrderOutcome>
   </soapenv:Body>
</soapenv:Envelope>
```

## Response ##

Response Type: `GetMicroBiologiLabOrderOutcomeOutcomeResponseType`

| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| resultCode | [string](http://www.w3.org/TR/xmlschema-2/#string) | Any of 'OK, 'ERROR', or 'INFO' | 1..1 |
| comment | [string](http://www.w3.org/TR/xmlschema-2/#string)  | Shall contain a message in plain text describing either an 'ERROR' or other valuable information 'INFO' . If a comment is provided it shall make sense to an end-user. | 0..1 |
| ACPAResult | `ACPAResultType`, see below for details | Se below for a detailed description, if an ERROR has occurred no records should be expected. If the result is OK no records means there's no results for the given request input. | 0..`*` |

Lab Result Element Type: `ACPAResultType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| dataOfSample | [date](http://www.w3.org/TR/xmlschema-2/#date) | Date when the result was sampled | 1..1 |
| ACPA | [boolean](http://www.w3.org/TR/xmlschema-2/#boolean) | ?? | 1..1 |
| ccp | [float](http://www.w3.org/TR/xmlschema-2/#float) | The cyclic citrullinated peptide value | 1..1 |
| cit\_c1\_igg | [float](http://www.w3.org/TR/xmlschema-2/#float) | The cit\_c1\_igg value | 1..1 |
| cit\_eno\_igg | [float](http://www.w3.org/TR/xmlschema-2/#float) | The cit\_eno\_igg value | 1..1 |
| cit\_fib\_igg | [float](http://www.w3.org/TR/xmlschema-2/#float) | The cit\_fib\_igg value | 1..1 |


#### Response SOAP Example ####
```

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Header>
   </soap:Header>
   <soap:Body>
      <GetMicroBiologiLabOrderOutcomeResponse xmlns="urn:sll:clinicalprocess:healthcond:actoutcome:GetMicroBiologiLabOrderOutcomeResponder:1" xmlns:core="urn:riv:itintegration:registry:1">
         <ACPAResult>
            <dateOfSample>1997-07-15+02:00</dateOfSample>
            <ACPA>false</ACPA>
            <ccp>1.0</ccp>
            <cit_c1_igg>8.0</cit_c1_igg>
            <cit_eno_igg>5.0</cit_eno_igg>
            <cit_fib_igg>17.0</cit_fib_igg>
         </ACPAResult>
      </GetMicroBiologiLabOrderOutcomeResponse>
   </soap:Body>
</soap:Envelope>

```