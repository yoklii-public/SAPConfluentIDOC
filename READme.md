# SAP IDOC Kafka producer

> This project allows IDOCs from SAP to be sent to Kafka topics. You may utilize any of the SAP IDOC outbound capabilities with this project.

> The project is developed in java and runs as a standalone component connecting to both the kafka(Confluent Cloud) and SAP systems. It requires two SAP connector libraries. You'll need an SAP and a Kafka system to utilize this demo, steps to create those are shown in the Installation.

Keywords SAP ABAP KAFKA CONFLUENT IDOC

![enter image description here](https://static.swimlanes.io/fae1c4571c2a033b69148d99f6b21b1c.png)

## Table of Contents 


- [Installation](#installation)
- [Environment Configuration ](#config)
- [Features](#features)
- [Contributing](#contributing)
- [FAQ](#faq)
- [License](#license)


---


## Installation

 

 1. Check you have the dependent systems. You may already have SAP systems, or confluent cloud environments, if not, for development or demo purposes you can use the steps below to create.
    	        a)  [An SAP ABAP  environment](https://developers.sap.com/tutorials/aoh-setup-cal-instance.html) 
I will generally use the free/developer instance available on CAL - the current version is SAP NetWeaver AS ABAP 7.51 SP02 on ASE
            	b) [A Confluent cloud environment](https://confluent.cloud) 
            	
    
 2. [Clone or download this repo](https://github.com/yoklii-public/SAPConfluentIDOC.git)
 3. [Download the SAP jco jar files](https://support.sap.com/en/product/connectors/jco.html)

 4. Configure the environment, including SAP configuration, various properties, parameter files, Confluent Cloud topics and environment variables ( all are described below, in the Environment Configuration section.)
 5. Create JKS / Java Key Stores, for trust path, and
 
 7.  Start the application idocserverexample.java and check the console output for connection messages.

		> running the application

		```shell
		$ mvn package
		$ java -jar target/...sap_ale_kafka_demo....jar
## Environment Configuration

**Network Consideration**
All components require connectivity. The jCo server is going to need SAP's message server ports, and gateway services ports open to the SAP system. These are often 32xx and 3300xx where xx is the SAP instance, i.e 00-99. For this reason, if you deploy to the SAP CAL instance, I suggest you deploy the jCo connector on the RDP/Jump server host. If you deploy in your own environment, make sure these ports are open.  Confluent will require bootstrap(9092) and http or https access too. 

**SAP Configuration**
Grab a coffee, this is going to take more than a minute or two.  Nearly all the configuration will be done in the SAP user GUI, aka SAPgui.  If you installed an environment from the SAP Cloud Appliance Library (CAL), you can access the pre-installed SAPgui via RDP. See 2.2 in [this guide](https://caldocs.hana.ondemand.com/caldocs/help/Getting_Started_NWasABAP751_SP02_ASE.pdf)





## Features
## Usage (Optional)
## Documentation (Optional)
## Tests (Optional)

SM39 IDOC test tool

---

## Contributing

> Please contribute. Some ideas for helping are in the issues list, but you may have other ways to improve this project.

### Step 1

- **Option 1**
    - 🍴 Fork this repo!

- **Option 2**
    - 👯 Clone this repo to your local machine using `https://github.com/joanaz/HireDot2.git`

### Step 2

- **HACK AWAY!** 🔨🔨🔨

### Step 3

- 🔃 Create a new pull request using <a href="https://github.com/joanaz/HireDot2/compare/" target="_blank">`https://github.com/joanaz/HireDot2/compare/`</a>.

---

## Team

> Or Contributors/People

---

## FAQ

**What are IDOCs?**
- [Good overview of IDOCs in SAP](https://blogs.sap.com/2012/12/31/idoc-basics-for-functional-consultants/)

    - 
 - List item

**Where di I get SAP Jco jar files**
Download with an SAP S user ID from [here](https://support.sap.com/en/product/connectors/jco.html)

---

## Support

Reach out to me at one of the following places!



---

## Donations 

Please consider supporting this and future yoklii projects by becoming a patreon at the link below.

<a href="https://www.patreon.com/bePatron?u=4167417" data-patreon-widget-type="become-patron-button">Become a Patron!</a><script async src="https://c6.patreon.com/becomePatronButton.bundle.js"></script>

Any amount is welcome, as a thank you we commit to supporting others with at least 10% of the proceeds from Patreon.


## License

![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)
**[GPLv3 license]( https://www.gnu.org/licenses/gpl-3.0)**

Copyright 2020 © <a href="https://yoklii.com" target="_blank">yoklii.com</a>.

