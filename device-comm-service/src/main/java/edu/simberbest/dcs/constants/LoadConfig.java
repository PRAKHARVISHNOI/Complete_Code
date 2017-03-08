/*package edu.simberbest.dcs.constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

public class LoadConfig {


	
	public final Properties prop = new Properties();
	public LoadConfig() {
		
		// Load Config File
			InputStream inStream;
			String configFile = null;
			
			File temp;
			try {
				
				
				 * load config file
				 
				configFile = CommunicationServiceConstants.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();	
				//System.out.println(configFile);
				temp = new File(configFile);
				if(temp.isDirectory())
				{
				configFile += "config.properties";
				}
				else
				{
				configFile = temp.getParent() + File.separator+"config.properties";
				}
				inStream = new FileInputStream(configFile);
				prop.load(inStream);
				
				
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			

				
				
	}
	
	



}
*/