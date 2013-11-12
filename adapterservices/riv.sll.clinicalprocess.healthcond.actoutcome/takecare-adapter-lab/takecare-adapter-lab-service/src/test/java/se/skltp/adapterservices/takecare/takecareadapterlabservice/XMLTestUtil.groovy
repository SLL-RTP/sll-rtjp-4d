package se.skltp.adapterservices.takecare.takecareadapterlabservice

import javax.xml.bind.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.skltp.adapterservices.takecare.takecareadapterlabservice.labrepliesget.LabRepliesGetTestProducer;


import groovy.xml.StreamingMarkupBuilder




class XMLTestUtil {
    private static final Logger log = LoggerFactory.getLogger(XMLTestUtil.class)

    public static String createErrorResponse(String externaluser, String careunitidtype, String careunitid) {
        
                def builder = new StreamingMarkupBuilder()
                builder.encoding = 'UTF-8'
                def x2message = builder.bind {
                    mkp.xmlDeclaration()
                    X2Message(MsgType:'Error', System:'ProfdocHIS', SystemInstance:'1', Time:'20130520100159', User:"${externaluser}",
                                CareUnitType:"${careunitidtype}", CareUnit:"${careunitid}", Method:'Lab.LabRepliesGet') { 
                        'Error' (Type:'System') {
                            'Code' '3000'
                            'Msg'  'Unknown Error' 
                            
                        } 
                    }
                }
                return groovy.xml.XmlUtil.serialize(wrapAsResponse(x2message))
    }

    public static String createOkResponse(String tcusername, String tcpassword,String externaluser, String careunitidtype, String careunitid, String patientId) {
        def builder = new StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def x2message = builder.bind {
            mkp.xmlDeclaration()
            X2Message(MsgType:'Response', System:'ProfdocHIS', SystemInstance:'1', Time:'20130520100159', User:"${externaluser}",
            CareUnitType:"${careunitidtype}", CareUnit:"${careunitid}", Method:'Lab.LabRepliesGet') {
                PatientOverView {
                    'HealthCareSystemId'  'SE123456789'
                    'PatientId'           "${patientId}"
                    'Units' {
                        'Unit' {
                            'HSAID'                   'SEabc123'
                            'HealthCareFacilityHSAID' 'SE987654321'
                            'UnitName'                'TestEnheten1'
                            'Labs2' {
                                'Answer' {
                                    'Analysis' {
                                        'InstanceId' 'SE123456789.193812217044.3'
                                        'Code' {
                                            'Code'        'MKV234'
                                            'DisplayName' 'B-ASAT'
                                        }
                                        'TakenDateTime'   '2012-01-01T10:14:28'
                                        'Comment'         'Här är en analyskommentar'
                                        'ResultSummary'   '152'
                                        'UnitOfMeasure'   'ug/ml'
                                        'Pathological'    'false'
                                        'Reference'       '124-165'
                                    }
                                    'Analysis' {
                                        'InstanceId'      'SE123456789.193812217044.4'
                                        'Code' {
                                            'Code'        'AF34TR'
                                            'DisplayName' 'B-HB'
                                        }
                                        'TakenDateTime'   '2012-02-02T10:13:28'
                                        'Comment'         ''
                                        'ResultSummary'   '234'
                                        'UnitOfMeasure'   'mmol/l'
                                        'Pathological'    'true'
                                        'Reference'       '124-165'
                                    }
                                    'InstanceId'          'SE123456789.193812217044.2'
                                    'RegisteredDateTime'  '2012-01-01T16:16:16'
                                    'Comment'             'Detta är en svarskommentar...'
                                    'HCProfessional' {
                                        'Name'        'Lars Lars'
                                        'Id'          'SE123456789.1931'
                                    }
                                    'OrderId'     'SE123456789.193812217044.1'
                                }

                            }
                        }
                    }
                }
            }
        }
        
        return groovy.xml.XmlUtil.serialize(x2message)

    }
    
    public static String createCommonResponse(String tcusername, String tcpassword,String externaluser, String careunitidtype, String careunitid, String patientId) {

        if (!patientId) {
            patientId = '191212121212'
        }
        
        def builder = new StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def x2message = builder.bind {
            mkp.xmlDeclaration()
            X2Message(MsgType:'Response', System:'ProfdocHIS', SystemInstance:'1', Time:'20130520100159', User:"${externaluser}",
            CareUnitType:"${careunitidtype}", CareUnit:"${careunitid}", Method:'Lab.LabRepliesGet') {
                PatientOverView {
                    'HealthCareSystemId'  'SE123456789'
                    'PatientId'           "${patientId}"
                    'Units' {
                        'Unit' {
                            'HSAID'                   'SEabc123'
                            'HealthCareFacilityHSAID' 'SE987654321'
                            'UnitName'                'TestEnheten1'
                            'Labs2' {
                                'Answer' {
                                    'Analysis' {
                                        'InstanceId' 'SE123456789.193812217044.3'
                                        'Code' {
                                            'Code'        'MKV234'
                                            'DisplayName' 'B-ASAT'
                                        }
                                        'TakenDateTime'   '2012-01-01T10:14:28'
                                        'Comment'         'Här är en analyskommentar'
                                        'ResultSummary'   '152'
                                        'UnitOfMeasure'   'ug/ml'
                                        'Pathological'    'false'
                                        'Reference'       '124-165'
                                    }
                                    'Analysis' {
                                        'InstanceId'      'SE123456789.193812217044.4'
                                        'Code' {
                                            'Code'        'AF34TR'
                                            'DisplayName' 'B-HB'
                                        }
                                        'TakenDateTime'   '2012-02-02T10:13:28'
                                        'Comment'         ''
                                        'ResultSummary'   '234'
                                        'UnitOfMeasure'   'mmol/l'
                                        'Pathological'    'true'
                                        'Reference'       '124-165'
                                    }
                                    'InstanceId'          'SE123456789.193812217044.2'
                                    'RegisteredDateTime'  '2012-01-01T16:16:16'
                                    'Comment'             'Detta är en svarskommentar...'
                                    'HCProfessional' {
                                        'Name'        'Lars Lars'
                                        'Id'          'SE123456789.1931'
                                    }
                                    'OrderId'     'SE123456789.193812217044.1'
                                }
                                'Answer' {
                                    'Analysis' {
                                        'InstanceId'  'SE123456789.193812217044.10'
                                        'Code' {
                                            'Code'    'ML345'
                                        }
                                        'TakenDateTime'   '2012-02-03T10:14:28'
                                        'ResultSummary'   '152'
                                        'Pathological'    'false'
                                    }
                                    'InstanceId'          'SE123456789.193812217044.2'
                                    'RegisteredDateTime'  '2012-01-01T16:16:16'
                                    'HCProfessional' {
                                        'Name'        'Lars Lars'
                                        'Id'          'SE123456789.1931'
                                    }
                                    'OrderId'         'SE123456789.193812217044.12'
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return groovy.xml.XmlUtil.serialize(x2message)
    }

	private static String wrapAsResponse(Writable x2message) {
        log.debug("wrapping the following message: " + x2message)
        StreamingMarkupBuilder builder = new StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def labRepliesGetResponse = builder.bind {
            mkp.xmlDeclaration()
            mkp.declareNamespace("" : 'http://tempuri.org/') 
            'LabRepliesGetResponse' {
                'LabRepliesGetResult' {
                     mkp.yieldUnescaped "<![CDATA[${x2message}]]>"
                }
            }
        }
        log.debug("wrapped response: " + groovy.xml.XmlUtil.serialize(labRepliesGetResponse))
		return groovy.xml.XmlUtil.serialize(labRepliesGetResponse)
	}
}