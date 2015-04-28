# Use Case Overview #

The main actors are patients and professionals, and the following driving use cases defines the requirements on information exchange:

Decision support app. (RA)
  1. As a professional I want to feedback structured data from the decision support system into the medical record
  1. As a professional I want to have access to a decision support dashboard for the patient in question
  1. As a professional I want to feedback structured data from a patients medical record into the decision support system
  1. As a professional I want to have a timeline of research lab results in the decision support dashboard

In this project with legacy `TakeCare` integrations in place,  Use Case 3 is realized as an optional combined fragment in Use Case 2, i.e. 2 and 3 is combined in the same solution.

Patient app. (PER)
  1. As a patient I want to access and get an overview of my status, so I don't have to contact health care each time
  1. As a patient I want appropriate forms to input requested data

## Decision support (RA) ##

### Use Case RA-1 :: As a professional I want to feedback structured data from the decision support system into the medical record ###

![https://sll-rtjp-4d.googlecode.com/git/docs/images/use-case-professional-save-ra-data-to-medical-record.png](https://sll-rtjp-4d.googlecode.com/git/docs/images/use-case-professional-save-ra-data-to-medical-record.png)

**Prerequisites**

  1. The professional has logged on to the EMR system

**Steps**

  1. The professional retrieves a particular form template
  1. The professional selects to import data from the decision support system (RA) into the from template
  1. The `TakeCare` Client uses a legacy `WebService` realized by the `TakeCare` adapter to get data
  1. The `TakeCare` adapter invokes the new RIV-TA standardized service (C1) to fetch actual data from the decision support system (RA)
  1. The professional selects to save the form data to the medical record
  1. The `TakeCare` client saves data to the server


---


### Use Case RA-2 and RA-3 :: As a professional I want to have access to a decision support dashboard for the patient in question ###

An already authorized (logged-in) professional user of the EMR system wants to open the decision support dashboard for a certain patient.
The sequence describes both Use Case 2 and 3, and Use Case 3 is then the optional combined fragment in the diagram below.

![https://sll-rtjp-4d.googlecode.com/git/docs/images/use-case-professional-open-dashboard.png](https://sll-rtjp-4d.googlecode.com/git/docs/images/use-case-professional-open-dashboard.png)

**Prerequisites**

  1. The professional has logged on to the EMR system

**Steps**

  1. The professional finds medical record data for the patient in question
  1. The professional selects to open the decision support system and a patient dashboard. `TakeCare` Client prepares the request which includes the URL to the Web Server, Patient ID and optionally more relevant data about the visit and drugs etc. Data always is encrypted using a shared key, i.e. shared between the EMR and RA system.
  1. Since RA is a Web application a separate Web Browser Window is opened, and the Browser is instructed to make a HTTP POST call to the `TakeCare` adapter.
  1. The new Web Browser Windows make the actual  HTTP POST call to the `TakeCare` adapter.
  1. If Data exists in the POST call the `TakeCare` adapter decrypts the data, and uses the new RIV-TA standardized Web Service (C2) to update the decision support system
  1. The `TaleCare` adapter returns a HTTP redirect response to automatically connect to and retrieve dashboard information from the decision support system (RA)
  1. The Browser connects to the decision support system and requests the dashboard index page for the patient in question
  1. The decision support system (RA) assembles information from the local database into a dashboard page
  1. Dashboard Web (HTML) page is returned to the Browser


---


### Use Case RA-4 :: As a professional I want to have a timeline of research lab results in the decision support dashboard ###

The actual timeline data is displayed in the same dashboard as described in Use Case 2 above. The diagram below describes how research lab data ends up in the decision support system (RA), i.e. enabler to be able to display this kind of information in the decision support system.

![https://sll-rtjp-4d.googlecode.com/git/docs/images/use-case-update-lab-results.png](https://sll-rtjp-4d.googlecode.com/git/docs/images/use-case-update-lab-results.png)

**Prerequisites**

  1. A patient has carried out some kind of lab test which is of interest to the research lab database (T-Med Fusion)

**Steps**

  1. A negative test result is registered in the research lab system (T-Med Fusion)
  1. The research lab system updates the Engagement index with an updated engagement for the patients in question. This update very well might involve a batch of patients. (C8)
  1. The Engagement  index service notifies the decision support system about updated engagements. (C7)
  1. The decision support system saves the actual updates to be able to process them later on.
  1. For each updated patient engagement the decision support system retrieves research lab data. In this first step it's about ACPA results (C5)
  1. The actual research lab result is saved to the patients timeline in the decision support system, i.e. the timeline of data is stored and maintained by the decision support system


---


## Patient app. (PER) ##

### Use Case PER-1 :: As a patient I want to access and get an overview of my status, so I don't have to contact health care each time ###

In this use-case the patient gets an overview of self registered health information complemented by a professional statement (rate). Among others a health status indicator is calculated as the product between self-rated, and professional rated health status. Since the personal health platform at this time is unknown it's not yet possible to describe the authentication method in detail.

> _**Note:** The patient use-cases has to be further analyzed, and the current sequence shall be regarded as a first draft._

![https://sll-rtjp-4d.googlecode.com/git/docs/images/use-case-patient-open-dashboard.png](https://sll-rtjp-4d.googlecode.com/git/docs/images/use-case-patient-open-dashboard.png)

**Prerequisites**

  1. A patient is authenticated in the actual application platform and can navigate to the application (PER).

**Steps**

  1. An authenticated patient uses the Web Browser to navigate to the patient Web application.
  1. The patient selects to open the dashboard page.
  1. The patient application fetches clinical data including health status rate from the decision support system. The new RIV-TA  `GetRaPatientData` (C3)  service is used.
  1. The patient application fetches data from the personal health database (C12) using the yet to be defined proprietary API.
  1. The patient application assembles the dashboard Web page.
  1. The Web page is returned to the Web Browser.


---


### Use Case PER-2 :: As a patient I want appropriate forms to input requested data ###

Depending on diagnosis the patient is requested to input data using pre-defined form templates, and the assumption is to utilize the standard form service and corresponding RIV-TA services.
To simplify readability are not all interactions to maintain form data outlined int the sequence diagram below, but hopefully enough to understand the main flow and necessary interactions between involved components.

> _**Note:** The patient use-cases has to be further analyzed, and the current sequence shall be regarded as a first draft._

![https://sll-rtjp-4d.googlecode.com/git/docs/images/use-case-patient-inputs-form.png](https://sll-rtjp-4d.googlecode.com/git/docs/images/use-case-patient-inputs-form.png)

**Prerequisites**

  1. A patient is authenticated in the actual application platform and can navigate to the application (PER).
  1. The actual form request has been carried out (yet to be defined how the request is carried out).

**Steps**

  1. An authenticated patient uses the Web Browser to navigate to the patient Web application.
  1. The patient has been requested to fill out a form  (yet to be described), and opens the actual form.
  1. A form of the actual type is created on behalf of the patient (C9).
  1. The patient application renders an appropriate Web form in HTML.
  1. The HTML form is returned to the patient application.
  1. The patient enters data and might also depending on the HTML application save changes while working on filling out the form.
  1. The patient decides that the form is complete and selects to finish input and save the form.
  1. The patient application saves the form to the form service (C9) .
  1. The form service updates engagement index with the actual form, i.e. a patient has submitted a particular form (C8).
  1. The engament index notifies all interested listeners and in this case the decision support system about the update in the engagement index, i.e. a patient has submitted a form (C7).
  1. The decision support system gets actual form data from the form service (C9).
  1. The decision support system saves patient form input into it's database. Currently it's not clear if the form input shall remain in the form service, or if it shall be replicated. The overall form life-cycle management process has to be further detailed.