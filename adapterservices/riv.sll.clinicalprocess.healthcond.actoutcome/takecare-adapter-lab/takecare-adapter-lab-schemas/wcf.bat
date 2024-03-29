@REM Licensed to the soi-toolkit project under one or more
@REM contributor license agreements.  See the NOTICE file distributed with
@REM this work for additional information regarding copyright ownership.
@REM The soi-toolkit project licenses this file to You under the Apache License, Version 2.0
@REM (the "License"); you may not use this file except in compliance with
@REM the License.  You may obtain a copy of the License at
@REM
@REM     http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
	
@REM ---------------------------------------------------------------------------------
@REM Generates c# WCF service contracts (interface), client proxies and wcf config
@REM file for the WSDLs + XML Schemas of the service domain, using .Net WCF tool svcuti.exe
@REM ---------------------------------------------------------------------------------	
	
	CD ..
	
	SET SCHEMADIR=schemas
		
	SET X0=%SCHEMADIR%/sample.xsd 	
	SET X1=%SCHEMADIR%/sampleService.wsdl 
	SET SCHEMAS=%X0% %X1%  
	SET OUTFILE=/out:wcf/generated-src/Takecare-adapter-labClient.cs
	SET APPCONFIG=/config:wcf/generated-src/app.config
	SET NAMESPACE=/namespace:*,Takecare-adapter-lab.Schemas
	
	@REM ServiceModel Metadata Utility Tool
	SET SVCUTIL="svcutil.exe"
	%SVCUTIL% /language:cs %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS%