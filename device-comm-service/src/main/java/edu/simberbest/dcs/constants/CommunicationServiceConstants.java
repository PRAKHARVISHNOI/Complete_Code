package edu.simberbest.dcs.constants;

/**
 * @author sbbpvi
 *
 */
public class CommunicationServiceConstants {

	
	public static LoadConfig loadConfig = new LoadConfig();
	// Constant For Pi Thread Pool
	public static Integer INFORMATION_SERVICE_THREAD_POOL_FOR_PI;	
	// Constant For Instruction Thread Pool
	public static  Integer INSTRUCTION_SERVICE_THREAD_POOL;
	// Constant For Process Instruction
	public static  String CONNECTION_FAILURE;
	// No Information Available 
	public static  String NO_INFORMATION_AVAILABLE;
	// Successfully Information Retrieved 
	public static  String INFORMATION_AVAILABLE;
	//Information Available Details
	public static  String INFORMATION_AVAILABLE_DETAILS="Informtion Retrived Successfully!";
	//Information NOt found Details
	public static  String NOT_FOUND_DETAILS="Connection Is Not Stabilize With RP! Mac could not be available!";
	// Internal server error
	public static  String INTERNAL_SERVER_ERROR_DETAILS="Internal Server Error Occured!";
	// Internal server error
	public static  String INTERNAL_SERVER_ERROR_DETAILS_FOR_ALL="Internal Server Error Occured As System Seems Down!";
	//Execution Details
	public static  String EXECUTION_DETAILS="Command Executed Successfully!";
	//No information available details
	public static  String NO_INFORMATION_AVAILABLE_DETAILS="No Information Available!";
	//Error While Retrieving Information from Cache
	public static  String ERROR_IN_RETRIVAL;
	//Client Port
	public static  Integer CLIENT_PORT;
	//Socket Server Pool
	public static  Integer SOCKET_SERVER_POOL;
	//Server Port
	public static  Integer SERVER_PORT;
	//ALL
	public static  String ALL="all";
	//TIMESTAMP
	public static  String TIMESTAMP="Timestamp";
	//Raspberry
	public static  String RESPBERRY="Raspberry";
	//Plugwise
	public static  String PLUGWISE="Plugwise";
	//POWER
	public static  String POWER="Power";
	//ENERGY
	public static  String ENERGY="Energy";
	//RELAY
	public static  String RELAY= "Relay";
	//UTF
	public static  String UTF= "UTF-8";
	//PI_SERVER_IP
	public static  String PI_SERVER_IP;
	//SSL
	public static  String SSL="SSL";
	//SINGAPORE TIME
	public static  String SINGAPORE_TIME="Singapore Time";
	// GMT+8 Singapore Time
	public static  String GMT="GMT+8";
	//Date Format
	public static  String DATE_FORMAT="yyyy-MM-dd HH:mm:ss.SSSSSS";
	// PI_PASSWORD
	public static  String PI_PASSWORD;
	//Value
	public static  String  VALUE="/value";
	//POST
	public static  String  POST="POST";
	//TimeOut
	public static  Integer TIMEOUT=10000;
	//FORMAT FOR TAG 
	public static  String FORMAT_FOR_TAG="\\\\SBB-5CG5473W1S\\SG.CR11.PLUGLOAD.";
	//ENERGY
	public static  String  ENERGY_TAG=".ENERGY";
	//POWER
	public static  String  POWER_TAG=".POWER";
	//RELAY
	public static  String  RELAY_TAG=".RELAY";
	//PLUGLOAD_ON
	public static  String PLUGLOAD_ON;
	//PLUGLOAD_OFF
	public static  String		PLUGLOAD_OFF;
	//PLUGLOAD_OFFLINE
	public static  String		PLUGLOAD_OFFLINE;
	//IP_NOT_PRESENT
	public static  String IP_NOT_PRESENT;
	//ON
	public static  String ON="ON";
	//OFF
	public static  String OFF="OFF";
	//OFFLINE
	public static  String OFFLINE="OFFLINE";
	public static void loadProperties(){
		INFORMATION_SERVICE_THREAD_POOL_FOR_PI=Integer.parseInt(loadConfig.prop.getProperty("INFORMATION_SERVICE_THREAD_POOL_FOR_PI"));
		INSTRUCTION_SERVICE_THREAD_POOL=Integer.parseInt(loadConfig.prop.getProperty("INSTRUCTION_SERVICE_THREAD_POOL"));
		CONNECTION_FAILURE=loadConfig.prop.getProperty ("CONNECTION_FAILURE");
		NO_INFORMATION_AVAILABLE=loadConfig.prop.getProperty("NO_INFORMATION_AVAILABLE");
		INFORMATION_AVAILABLE=loadConfig.prop.getProperty ("INFORMATION_AVAILABLE");
		ERROR_IN_RETRIVAL=loadConfig.prop.getProperty ("ERROR_IN_RETRIVAL");
		CLIENT_PORT=Integer.parseInt(loadConfig.prop.getProperty("CLIENT_PORT"));
		SOCKET_SERVER_POOL=Integer.parseInt(loadConfig.prop.getProperty("SOCKET_SERVER_POOL"));
		SERVER_PORT=Integer.parseInt(loadConfig.prop.getProperty("SERVER_PORT"));
		PI_SERVER_IP=loadConfig.prop.getProperty ("PI_SERVER_IP");
		PI_PASSWORD=loadConfig.prop.getProperty ("PI_PASSWORD");
		PLUGLOAD_ON=loadConfig.prop.getProperty ("PLUGLOAD_ON");
		PLUGLOAD_OFF=loadConfig.prop.getProperty ("PLUGLOAD_OFF");
		PLUGLOAD_OFFLINE=loadConfig.prop.getProperty ("PLUGLOAD_OFFLINE");
		IP_NOT_PRESENT=loadConfig.prop.getProperty ("IP_NOT_PRESENT");
	}
}
