# Component Overview #

See diagram below for main components and logical interfaces/references.  Please note that the purpose of this diagram is to describe relevant dependencies for the driving use-cases only, i.e. there's no ambition to try to describe the overall system landscape.

![https://sll-rtjp-4d.googlecode.com/git/docs/images/component-overview.png](https://sll-rtjp-4d.googlecode.com/git/docs/images/component-overview.png)

_Logical overview over all components and their associations. Integration components such as service integration plattforms (SLL RTjP, NTjP) and API gateway (APIGW) are not a part of this logical view. The API gateway is a technology to exchange data between the personal controlled and the healthcare controlled domain._

### Decision support app. (RA, KI) ###

---


RA registry is a decision support system for arthritis developed by Carmona. The same plattform is used for other purposes as well. Existing point-point integrations with Take Care and PER are replaced by new RIV-TA APIs to be deployed in the regional service plattform (SLL RTjP).

**Produces**
  * Services to get and register clinical RA decision data (C1, C2) typically used by an EMR system
  * Services to get and register personal RA decision data (C3, C4) typically used by an external personal health record app
  * The standardized process notification service (C7)

**Consumes**
  * T-Med  Fusion ACPA results (C5)
  * Clinical lab data from an EMR system (C6)

### Medical record (Take Care, SLL) ###

---


Take Care by `CompuGroup` Medical is the main EMR system in Stockholm, and also point-to-point integrated with the decisions support system (RA).  Replacing legacy point-to-point integrations with standardized services opens up for other EMR systems to be connected to RA . To minimize impact on Take Care the legacy integrations are adapted into the new set of services, also see component Take Care adapter.

**Produces**
  * Legacy chemistry lab data interface (adapted to C6)

**Consumes**
  * Legacy Get and Register decision support data (adapted to C1 and C2)

### Research database (T-Med Fusion) ###

---


T-Med Fusion is a federated research lab system maintained by Karolinska Institutet. Initially it provides  ACPA indicators, but long term more kinds of indicators shall be supported.

**Produces**
  * ACPA results (C5)

**Consumes**
  * E-Index updates when a patient record has been updated (C8)

### Engagement Index (SLL RTjP) ###

---


Engagement index is a central part of the federated architecture and keeps track of patient engagements, i.e. in which system are particular patient data located.  The service in question is a part of the regional service platform (SLL RTjP).
Two use-cases are of interest for the involvement of engagement index. First when T-Med Fusion has new or updated data regarding a patient, and secondly when a patient has completed registration of form data. In both cases the decision support (RA) system is targeted as the main interested party.

In this context a couple of the E-Index services are of interest.

**Produces**
  * Update of engagements (C8)

**Consumes**
  * Process notifications, i.e. a  system has registered interest in updates of engagement records (C7)

### Take Care Adapter (SLL RTjP) ###

---


Too minimize impact on the Take Care EMR system and adaptar is used as a facade between the new standardized and legacy services, i.e. from Take Cares point of view it's still about a point-to-point integration with the RA system. The adapter component is deployed in the regional service platform (SLL RTjP).

**Produces**
  * New standardized service to get chemistry lab data (C6)
  * Legacy services to register and get decisions support data (C11)

**Consumes**
  * New standardized service to get and register decisions support data (C1 and C2)
  * Legacy service to get chemistry lab data (C10)

### Form Engine (SLL) ###

---


The Form Engine is a generic plattform service to gather structured patient information. In this context it's about registration of relevant data to be fed to the decision support system (RA). All form engagements are registered in the Engagement index.

The Form Engine component is deployed in the SLL data center "MVK Cloud".

**Produces**
  * The standardized services to register and retrieve from information (C9)

**Consumes**
  * Engagement index Update service  (C8)

### Patient app. (PER) ###

---


PER is an application for the patient to register specifik data into the decision support system (RA). Currently PER is tightly connected to RA, and the objective is to make it more loosely coupled and also to fit into the SLL service landscape with Form Engine, and also to make it an integral part of the upcoming personal health record service.

> _**Please Note:** The detailed level of design of the next generation of PER is yet to be defined. Among others there is a question if PER should be divided into 2 different applications depending on which side of the security border it's running on (personal, clinical). Furthermore the actual PHR system has to be in place in order to understand exactly how PER will fit into this. Anyway, most certainly the picture will change over time._

**Consumes**
  * Patient view of decision support services  (C3 and C4)
  * Form Engine services (C9)
  * Personal health record data storage (C12)

### Subscription service (JPN) ###

---


The subscription service is a part of the project "Journal på nätet", and makes it possible for citizens to subscribe on particular health care (EMR) information. Personal data is in a controlled manner transferred from health care EMR systems to the personal health record in question.

**Consumes**
  * Patient view of the decision support system (C3)
  * Personal health record data storage (C12)

### Personal health database (Health for me) ###

---


A public service to keep track of personal health information, which currently is under procurement at Apotekets service (on behalf of Ministry of Social Affairs).

**Produces**
  * Personal health record data storage (C12), yet to be defined!

# Generic Overview #

---


Since several applications are about utilizing the same pattern as this RA project the more generic logical view below can be used to illustrate involved components.


![https://sll-rtjp-4d.googlecode.com/git/docs/images/component-overview-generic.png](https://sll-rtjp-4d.googlecode.com/git/docs/images/component-overview-generic.png)