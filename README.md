# key-protect-and-cups-example

This document discusses an approach for securing microservices where the consumer and provider microservice are both backend services that are components of the same overall application.  For this scenario, we determined that the use of symmetric shared keys is sufficient for securing these types of microservice interactions.  In scenarios where the consumer and provider are not components of the same overall application assymetric publc key crypotography using PKCS would be more appropriate.


High level diagram:
-------------------
Each microservice apps interacts with Custom User Provided Service (CUPS) to get shared key for signing and verifying JWT tokens.

![Link to image](diagram.png?raw=true)

Pros and Cons of approach:
---------------------------
- Pros:
   - Enhanced reliablity as each Microservice is gauranteed to always have access to the secret key.  Microservice does not depend on Key Protect service being available. 
- Cons:
   - Secret key is exposed as a VCAP variable.  Whomever has access to the Bluemix account can discover the secret key.
   - Secret key in VCAP variable should be Base64 encoded for enhanced security.

Alernative Approach: 
---------------------

Microservice app interacts with Key Protect directly to get shared key for signing and verifying JWT tokens
- Pros:
   - More secure as secret key is not stored anywhere
- Cons:
   - If Key Protect service becomes unavailable then microservices will not be able to function as it can not sign or verify JWTs.


Key issues that need to be considered:
--------------------------------------
1) How long is the key (symmetric shared key) valid for?  How often does the key need to be refreshed?
   - Policies need to be established to determine this.
   
2) When a consumer microservice generates a JWT token,  how long is the token valid for?
   - An order of magnitude of seconds is sufficient for a synchronous invocation (e.g. 20 seconds should be more then enough time).

Description of sample code in this repo:
-------------------------------------------
This example demonstrates how you can call the Bluemix Key Protect API to get a key represening the secret shared key used for signing and verifying JWT tokens.

This example then generates the Bluemix Cloud Foundry command to create a CUPS (customer user provided service) service with a VCAP variable that has the value of the shared key.

Consumer and Producer REST apps that want to communicate securely can bind to the CUPS service to get access to the secret shared key.


Configuration Steps Required:
-----------------------------
Note: Currently this example requires hard coding values into the source.  It will be updated to inject config properties using Spring Boot injection using property files.

Create a Key Protect service in your Bluemix account and create a key.

Update following values in the config\ApplicationConfig.java file:
   - bluemixToken
   - bluemixOrg
   - bluemixSpace
   - keyId

The Bluemix commands to get the necessary config info are:    
   - bluemix token: 
       - cf oauth-token
   - bluemix org:  
       - cf org <your_organization_name> --guid
   - bluemix space: 
       - cf space <your_space_name> --guid
   - to loockup 'org name' and 'space name': 
       - cf target

Build and run the app:
------------------------
mvn package

The app runs as a Spring Boot app and logs the secret key info as well as the command to create the CUPS service.
Again, this is just an example.  You would not want to log this info in a real world application.

This example can be used in an automated toolchain.
For example, as part of a build, this program can be executed to get the secret key and create the CUPS service. When the REST consumer and producer apps are built and deployed, they can be bound to the CUPS service to get access to the secret key.

See the git repo: https://github.com/gkovan/secure-microservices-example  for an example of how to create secure microservice interactions.
   

