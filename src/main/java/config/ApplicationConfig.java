package config;

public class ApplicationConfig {	
	
    // WARNING, the config information is hardcoded in this file.  For best practices security, the config info needs to be externalized.
    // right now I have hard coded the header info based on my personal Bluemix account.
    //  See link to get Bluemix values for token, org and space: https://github.com/IBM-Bluemix/key-protect-helloworld-python
    // to get the bluemix token:  'cf oauth-token'
    // to get the bluemix org:  'cf org <your_organization_name> --guid'
    // to get the bluemix space: 'cf space <your_space_name> --guid'
    // to loockup 'org name' and 'space name': cf target
    
	public static String bluemixToken = "";
    
	public static String blueMixOrg = "";
    
	public static String bluemixSpace = "";
    
    public static String keyProtectSerivceUrl = 
    		"https://ibm-key-protect.edge.bluemix.net/api/v2/secrets/";
    
    
    public static String keyId = "";
    
    
    public static String bluemixCupsServiceName = "shared-key-cups-service";
    public static String cupsServiceVCAPvarName = "secret-key";
    public static String bluemixCFCupsCommand = "bx cf cups %s -p {\"%s\":\"%s\"}";

}
