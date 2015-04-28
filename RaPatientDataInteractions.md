# Introduction #

Service contracts to exchange data with the decision support system (RA).

Service Domain Name: `urn:sll:clinicalprocess:healthcond:description`

Also see:

  * [GetRaPatientData WSDL](https://code.google.com/p/sll-rtjp-4d/source/browse/contracts/riv/sll/clinicalprocess/healthcond/description/schemas/interactions/GetRaPatientDataInteraction/GetRaPatientDataInteraction_1.0_RIVTABP21.wsdl)
  * [GetRaPatientData Service Contract Schema](https://code.google.com/p/sll-rtjp-4d/source/browse/contracts/riv/sll/clinicalprocess/healthcond/description/schemas/interactions/GetRaPatientDataInteraction/GetRaPatientDataResponder_1.0.xsd)
  * [RegisterRaPatientData WSDL](https://code.google.com/p/sll-rtjp-4d/source/browse/contracts/riv/sll/clinicalprocess/healthcond/description/schemas/interactions/RegisterRaPatientDataInteraction/RegisterRaPatientDataInteraction_1.0_RIVTABP21.wsdl)
  * [RegisterRaPatientData Service Contract Schema](https://code.google.com/p/sll-rtjp-4d/source/browse/contracts/riv/sll/clinicalprocess/healthcond/description/schemas/interactions/RegisterRaPatientDataInteraction/RegisterRaPatientDataResponder_1.0.xsd)
  * [Service Domain Schema](https://code.google.com/p/sll-rtjp-4d/source/browse/contracts/riv/sll/clinicalprocess/healthcond/description/schemas/core_components/clinicalprocess_healthcond_description_1.0.xsd)

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

Complex Type `DatePeriodType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| start | [string](http://www.w3.org/TR/xmlschema-2/#string) | Date in format 'YYYYMMDD'  | 1..1 |
| end |  [string](http://www.w3.org/TR/xmlschema-2/#string)  | Date in format 'YYYYMMDD' | 1..1 |


## Service Contract GetRaPatientData :: Request ##

Request Type: `GetRaPatientDataType`

| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| careUnitHSAid | HSAidType | Filter by PDL-unit, source of the informationen. | 0..|
| patientId | PatientIDType | The patient identity  | 1..1 |
| timePeriod | DatePeriodType | Visit date. Limiting the search in time | 0..1 |
| sourceSystemHSAid | HSAidType | Limiting the search in time | 0..1 |
| careContactId | HSAidType | Limiting the search in time | 0..1 |



#### Request SOAP Example ####
```

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:int="urn:riv:itintegration:registry:1" xmlns:tns="urn:sll:clinicalprocess:healthcond:description:GetRaPatientDataResponder:1" xmlns:core="urn:sll:clinicalprocess:healthcond:description:1">
   <soapenv:Header>
      <int:LogicalAddress>TEST</int:LogicalAddress>
   </soapenv:Header>
   <soapenv:Body>
      <tns:GetRaPatientData>
         <!--Zero or more repetitions:-->
         <tns:careUnitHSAid>HSA-VKY567</tns:careUnitHSAid>
         <tns:patientId>191212121212</tns:patientId>
         <tns:timePeriod>
            <core:start>20130201</core:start>
            <core:end>20131001</core:end>
         </tns:timePeriod>
      </tns:GetRaPatientData>
   </soapenv:Body>
</soapenv:Envelope>

```

## Service Contract GetRaPatientData :: Response ##


Response Type: `GetRaPatientDataResponseType`

| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| patientData | `PatientDataType`, see below for details | Domain specific  datatype, see detailed description below. If an ERROR has occurred this element is omitted. If the result code is OK but and this element is missing it  means there's no results for the given request input.  | 0..|
| `ResultCode` | [string](http://www.w3.org/TR/xmlschema-2/#string) | One of 'OK' or 'ERROR'.  | 1..1 |
| comment | [string](http://www.w3.org/TR/xmlschema-2/#string)  | On an error the comment shall describe a cause in plain text, in such a way that it makes sense to an end-user. | 0..1 |

Registry Result Element Type: `PatientDataType`

| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| patientVariables | `PatientVariablesType`, see below | Information from patient  | 0..1 |
| doctorVariables | `DoctorVariablesType`, see below | Information from doctor  | 0..1 |
| labVariables | `LabVariablesType`, see below | Information from lab result  | 0..1 |
| drugs | A collection of `DrugType`, see below | Prescription drug(s)  | 0..1 |

Element Type: `PatientVariablesType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| workability |  [integer](http://www.w3.org/TR/xmlschema-2/#integer)  | The patients workability. | 0..1 |
| globalHealth |  [integer](http://www.w3.org/TR/xmlschema-2/#integer)  | The globalHealth of the patient. | 0..1 |
| pain |  [integer](http://www.w3.org/TR/xmlschema-2/#integer)  | Patient perceptions concerning pain | 0..1 |
| eq5d |  [double](http://www.w3.org/TR/xmlschema-2/#double)  | eq5d | 0..1 |
| haq |  [double](http://www.w3.org/TR/xmlschema-2/#double)  | haq | 0..1 |
| tenderJoints28 |  [integer](http://www.w3.org/TR/xmlschema-2/#integer)  | Tender Joints 28. | 0..1 |
| swollenJoints28 |  [integer](http://www.w3.org/TR/xmlschema-2/#integer)  | Swollen Joints 28. | 0..1 |

Element Type: `DoctorVariablesType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| doctorGlobal |  DoctorGlobalEnum, see below  | Doctors medical assessment | 0..1 |
| tenderJoints28 |  [integer](http://www.w3.org/TR/xmlschema-2/#integer)  | tenderJoints28. | 0..1 |
| DAS28 |  [float](http://www.w3.org/TR/xmlschema-2/#float)  | Disease Activity Score of 28 joints. | 0..1 |
| DAS28CRP |  [float](http://www.w3.org/TR/xmlschema-2/#float)  | DAS28CRP | 0..1 |

Element Type: `DoctorsGlobalEnum`
| **Comment** | **Basic Type** | **Values** |
|:-----------------------------|:-----------|
|  | [string](http://www.w3.org/TR/xmlschema-2/#string) | 'none', 'low', 'moderate', 'high', 'maximal'  |

Element Type: `LabVariablesType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| SR |  [float](http://www.w3.org/TR/xmlschema-2/#float) | sedimentation rate | 0..1 |
| CRP |  [float](http://www.w3.org/TR/xmlschema-2/#float) | c-reactive protein CRP | 0..1 |

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

#### Response XML Example ####
```

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Header>
      <mule:header xmlns:mule="http://www.muleumo.org/providers/soap/1.0">
         <mule:MULE_CORRELATION_ID>10bc64b7-4075-11e3-95ed-23b4adf64455</mule:MULE_CORRELATION_ID>
         <mule:MULE_CORRELATION_GROUP_SIZE>1</mule:MULE_CORRELATION_GROUP_SIZE>
         <mule:MULE_CORRELATION_SEQUENCE>-1</mule:MULE_CORRELATION_SEQUENCE>
      </mule:header>
   </soap:Header>
   <soap:Body>
      <GetRaPatientDataResponse xmlns="urn:sll:clinicalprocess:healthcond:description:GetRaPatientDataResponder:1" xmlns:ns2="urn:sll:clinicalprocess:healthcond:description:1" xmlns:ns3="urn:riv:itintegration:registry:1">
         <patientData>
            <patientVariables>
               <workability>4</workability>
               <globalHealth>1</globalHealth>
               <pain>0</pain>
               <eq5d>6.3</eq5d>
               <haq>2.4</haq>
               <tenderJoints28>5</tenderJoints28>
               <swollenJoints28>2</swollenJoints28>
            </patientVariables>
            <doctorsVariables>
               <doctorGlobal>high</doctorGlobal>
               <tenderJoints28>5</tenderJoints28>
               <swollenJoints28>2</swollenJoints28>
               <DAS28>13.2</DAS28>
               <DAS28CRP>7.1</DAS28CRP>
            </doctorsVariables>
            <labVariables>
               <SR>147.0</SR>
               <CRP>105.0</CRP>
            </labVariables>
         </patientData>
         <patientData>
            <patientVariables>
               <workability>5</workability>
               <globalHealth>3</globalHealth>
               <pain>2</pain>
               <eq5d>12.5</eq5d>
               <haq>6.4</haq>
               <tenderJoints28>3</tenderJoints28>
               <swollenJoints28>5</swollenJoints28>
            </patientVariables>
            <doctorsVariables>
               <doctorGlobal>maximal</doctorGlobal>
               <tenderJoints28>5</tenderJoints28>
               <swollenJoints28>2</swollenJoints28>
               <DAS28>19.9</DAS28>
               <DAS28CRP>19.7</DAS28CRP>
            </doctorsVariables>
            <labVariables>
               <SR>63.0</SR>
               <CRP>22.0</CRP>
            </labVariables>
         </patientData>
         <patientData>
            <patientVariables>
               <workability>3</workability>
               <globalHealth>1</globalHealth>
               <pain>1</pain>
               <eq5d>6.1</eq5d>
               <haq>15.3</haq>
               <tenderJoints28>1</tenderJoints28>
               <swollenJoints28>4</swollenJoints28>
            </patientVariables>
            <doctorsVariables>
               <doctorGlobal>maximal</doctorGlobal>
               <tenderJoints28>1</tenderJoints28>
               <swollenJoints28>3</swollenJoints28>
               <DAS28>5.8</DAS28>
               <DAS28CRP>7.5</DAS28CRP>
            </doctorsVariables>
            <labVariables>
               <SR>66.0</SR>
               <CRP>125.0</CRP>
            </labVariables>
         </patientData>
         <patientData>
            <patientVariables>
               <workability>2</workability>
               <globalHealth>2</globalHealth>
               <pain>3</pain>
               <eq5d>13.5</eq5d>
               <haq>2.2</haq>
               <tenderJoints28>3</tenderJoints28>
               <swollenJoints28>1</swollenJoints28>
            </patientVariables>
            <doctorsVariables>
               <doctorGlobal>high</doctorGlobal>
               <tenderJoints28>2</tenderJoints28>
               <swollenJoints28>2</swollenJoints28>
               <DAS28>12.5</DAS28>
               <DAS28CRP>7.8</DAS28CRP>
            </doctorsVariables>
            <labVariables>
               <SR>22.0</SR>
               <CRP>131.0</CRP>
            </labVariables>
         </patientData>
         <patientData>
            <patientVariables>
               <workability>1</workability>
               <globalHealth>2</globalHealth>
               <pain>4</pain>
               <eq5d>13.5</eq5d>
               <haq>18.2</haq>
               <tenderJoints28>5</tenderJoints28>
               <swollenJoints28>2</swollenJoints28>
            </patientVariables>
            <doctorsVariables>
               <doctorGlobal>maximal</doctorGlobal>
               <tenderJoints28>3</tenderJoints28>
               <swollenJoints28>0</swollenJoints28>
               <DAS28>18.6</DAS28>
               <DAS28CRP>9.3</DAS28CRP>
            </doctorsVariables>
            <labVariables>
               <SR>166.0</SR>
               <CRP>99.0</CRP>
            </labVariables>
         </patientData>
         <patientData>
            <patientVariables>
               <workability>2</workability>
               <globalHealth>2</globalHealth>
               <pain>5</pain>
               <eq5d>3.4</eq5d>
               <haq>18.7</haq>
               <tenderJoints28>3</tenderJoints28>
               <swollenJoints28>3</swollenJoints28>
            </patientVariables>
            <doctorsVariables>
               <doctorGlobal>low</doctorGlobal>
               <tenderJoints28>0</tenderJoints28>
               <swollenJoints28>5</swollenJoints28>
               <DAS28>7.9</DAS28>
               <DAS28CRP>15.8</DAS28CRP>
            </doctorsVariables>
            <labVariables>
               <SR>86.0</SR>
               <CRP>121.0</CRP>
            </labVariables>
         </patientData>
         <patientData>
            <patientVariables>
               <workability>2</workability>
               <globalHealth>1</globalHealth>
               <pain>0</pain>
               <eq5d>10.5</eq5d>
               <haq>13.2</haq>
               <tenderJoints28>0</tenderJoints28>
               <swollenJoints28>5</swollenJoints28>
            </patientVariables>
            <doctorsVariables>
               <doctorGlobal>none</doctorGlobal>
               <tenderJoints28>1</tenderJoints28>
               <swollenJoints28>0</swollenJoints28>
               <DAS28>1.3</DAS28>
               <DAS28CRP>9.7</DAS28CRP>
            </doctorsVariables>
            <labVariables>
               <SR>70.0</SR>
               <CRP>110.0</CRP>
            </labVariables>
         </patientData>
         <patientData>
            <patientVariables>
               <workability>4</workability>
               <globalHealth>4</globalHealth>
               <pain>2</pain>
               <eq5d>15.9</eq5d>
               <haq>11.8</haq>
               <tenderJoints28>5</tenderJoints28>
               <swollenJoints28>0</swollenJoints28>
            </patientVariables>
            <doctorsVariables>
               <doctorGlobal>none</doctorGlobal>
               <tenderJoints28>5</tenderJoints28>
               <swollenJoints28>2</swollenJoints28>
               <DAS28>15.4</DAS28>
               <DAS28CRP>1.8</DAS28CRP>
            </doctorsVariables>
            <labVariables>
               <SR>66.0</SR>
               <CRP>129.0</CRP>
            </labVariables>
         </patientData>
         <comment>Test result for [191212121212]</comment>
      </GetRaPatientDataResponse>
   </soap:Body>
</soap:Envelope>

```

## Service Contract RegisterRaPatientData :: Request ##

Request Type: `RegisterRaPatientDataType`

| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| raData | `RaDataType`, see below | Information from patient  | 0..1 |

Element Type: `RaDataType`
| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| aValue |  [string](http://www.w3.org/TR/xmlschema-2/#string)  |  | 0..1 |


#### Request SOAP Example ####
```


```

## Service Contract RegisterRaPatientData :: Response ##


Response Type: `RegisterRaPatientDataResponseType`

| **Element** | **Basic Type** | **Comment** | **Cardinality** |
|:------------|:---------------|:------------|:----------------|
| `ResultCode` | [string](http://www.w3.org/TR/xmlschema-2/#string) | One of 'OK' or 'ERROR'.  | 1..1 |
| comment | [string](http://www.w3.org/TR/xmlschema-2/#string)  | On an error the comment shall describe a cause in plain text, in such a way that it makes sense to an end-user. | 0..1 |


#### Response XML Example ####
```


```