# key-protect-and-cups-example

This document discusses approaches for securing microservices where the consumer and provider microservice are both backend services that are components of the same overall application.  For this scenario, we determined that the use of symmetric shared keys is sufficient for securing these types of microservice interactions.  In scenarios where the consumer and provider are not components of the same overall application assymetric publc key crypotography using PKCS would be more appropriate.

Approaches considered for microservice security for components of the same application:
----------------------------------------------------------------------------------------
Approach 1: Microservice app interacts with Key Protect directly to get shared key for signing and verifying JWT tokens
- Pros:
   - aaa
   - bbb
- Cons:
   - aaa
   - bbb
   
Approach 2: Microservice app interacts with Custom User Provided Service (CUPS) to get shared key for signing and verifying JWT tokens
   - aaa
   - bbb
- Cons:
   - aaa
   - bbb

This example demonstrates how you can call the Bluemix Key Protect API to get a key represening the secret shared key used for signing and verifying JWT tokens.

This example then generates the Bluemix Cloud Foundry command to create a CUPS (customer user provided service) service with a VCAP variable that has the value of the shared key.

Consumer and Producer REST apps that want to communicate securely can bind to the CUPS service to get access to the secret shared key.

High level diagram:
-------------------

![Link to image](diagram.png?raw=true)


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
   

