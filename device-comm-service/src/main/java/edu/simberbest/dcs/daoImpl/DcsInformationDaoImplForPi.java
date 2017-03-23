package edu.simberbest.dcs.daoImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import edu.simberbest.dcs.constants.CommunicationServiceConstants;
import edu.simberbest.dcs.entity.PlugLoadInformationPacket;

public class DcsInformationDaoImplForPi {
	@Value("${PI_SERVER_IP}")
	public String PI_SERVER_IP;
	@Value("${PI_PASSWORD}")
	public  String PI_PASSWORD;
	private static final Logger logger = LoggerFactory.getLogger(DcsInformationDaoImplForPi.class);
/*	private   String BASE_PiS_URI_POST = "https://" + PI_SERVER_IP
			+ "/piwebapi/streams/";
	private   String BASE_PiS_URI_GET_ELEMENT = "https://" + PI_SERVER_IP
			+ "/piwebapi/points?path=";
*/	private static Calendar calendar;
	private static SimpleDateFormat simpleDateFormat;
	private static String base64encodedUserIDandPassword;
	private static String strTimestamp;
	

	public boolean insertCurrentFeedToPi(PlugLoadInformationPacket infoPcket) {
		String BASE_PiS_URI_POST,BASE_PiS_URI_GET_ELEMENT;
		logger.info("Enter DcsInformationDaoImplForPi method insertCurrentFeedToPi: Param # " + infoPcket+":"+PI_SERVER_IP+":"+PI_PASSWORD);
		BASE_PiS_URI_POST = "https://" + PI_SERVER_IP+ "/piwebapi/streams/";
		 BASE_PiS_URI_GET_ELEMENT = "https://" + PI_SERVER_IP+ "/piwebapi/points?path=";
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}

		} };
		SSLContext sc;
		try {
			sc = SSLContext.getInstance(CommunicationServiceConstants.SSL);
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			logger.error(e.toString());
		}
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		TimeZone tz = Calendar.getInstance().getTimeZone();
		TimeZone timeZone = TimeZone.getTimeZone(CommunicationServiceConstants.GMT);
		calendar = Calendar.getInstance(timeZone);
		simpleDateFormat = new SimpleDateFormat(CommunicationServiceConstants.DATE_FORMAT);
		simpleDateFormat.setTimeZone(timeZone);
		strTimestamp = simpleDateFormat.format(new Date(Long.parseLong(infoPcket.getTimestamp())));
		String webElementIdPower = infoPcket.getPowerWebId();
		String webElementIdEnergy = infoPcket.getEnergyWebId();
		String webElementIdRelay = infoPcket.getRelayWebId();
		String strEngTimestamp;
		String strPwTimestamp;
		String strRlyTimestamp;
		
		String UserIDandPassword = PI_PASSWORD;
		try {
			base64encodedUserIDandPassword = Base64.getEncoder()
					.encodeToString(UserIDandPassword.getBytes(CommunicationServiceConstants.UTF));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.toString());
		}
		if (webElementIdPower == null || webElementIdPower.equals("") || webElementIdEnergy == null
				|| webElementIdEnergy.equals("") || webElementIdRelay == null || webElementIdRelay.equals("")) {
			updateWebId(infoPcket,BASE_PiS_URI_POST,BASE_PiS_URI_GET_ELEMENT);
		}
		URL urlEnergy = null, urlPower = null, urlRelay = null;
		try {
			urlEnergy = new URL(BASE_PiS_URI_POST + infoPcket.getEnergyWebId() + CommunicationServiceConstants.VALUE);
			urlPower = new URL(BASE_PiS_URI_POST + infoPcket.getPowerWebId() + CommunicationServiceConstants.VALUE);
			urlRelay = new URL(BASE_PiS_URI_POST + infoPcket.getRelayWebId() + CommunicationServiceConstants.VALUE);
		} catch (MalformedURLException e) {
			logger.error(e.toString());
		}
		try {
			if (infoPcket.getEnergy() != null) {
				strEngTimestamp = simpleDateFormat.format(new Date(Long.parseLong(infoPcket.getEnTimeStamp())));
				getConnectionAndFlushdata(urlEnergy, infoPcket.getEnergy(),strEngTimestamp);
			}
			if (infoPcket.getPower() != null) {
				strPwTimestamp = simpleDateFormat.format(new Date(Long.parseLong(infoPcket.getPwTimeStamp())));
				getConnectionAndFlushdata(urlPower, infoPcket.getPower(),strPwTimestamp);
			}
			if (infoPcket.getRelay() != null) {
				strRlyTimestamp = simpleDateFormat.format(new Date(Long.parseLong(infoPcket.getRlyTimeStamp())));
				getConnectionAndFlushdata(urlRelay, infoPcket.getRelay(),strRlyTimestamp);
			}
		} catch (IOException e) {
			logger.error(e.toString());
		}
		logger.info("Exit DcsInformationDaoImplForPi method insertCurrentFeedToPi" );
		return true;
	}

	private void updateWebId(PlugLoadInformationPacket infoPcket, String bASE_PiS_URI_POST, String bASE_PiS_URI_GET_ELEMENT) {
		logger.info("Enter PlugLoadInformationPacket " );
		URL urlEnergy = null, urlPower = null, urlRelay = null;
		try {
			urlEnergy = new URL(bASE_PiS_URI_GET_ELEMENT +CommunicationServiceConstants.FORMAT_FOR_TAG+infoPcket.getMacId()+CommunicationServiceConstants.ENERGY_TAG);
			urlPower = new URL(bASE_PiS_URI_GET_ELEMENT +CommunicationServiceConstants.FORMAT_FOR_TAG+infoPcket.getMacId()+CommunicationServiceConstants.POWER_TAG);
			urlRelay = new URL(bASE_PiS_URI_GET_ELEMENT +CommunicationServiceConstants.FORMAT_FOR_TAG+infoPcket.getMacId()+CommunicationServiceConstants.RELAY_TAG);
		} catch (MalformedURLException e) {
			logger.error("MalformedURLException in FormHTTPRequestGetElement()"+e.toString());
		}

		if (infoPcket.getEnergyWebId() == null || infoPcket.getEnergyWebId().equals("")) {
			infoPcket.setEnergyWebId(returnWebId(urlEnergy));
		}
		if (infoPcket.getPowerWebId() == null || infoPcket.getPowerWebId().equals("")) {
			infoPcket.setPowerWebId(returnWebId(urlPower));
		}
		if (infoPcket.getRelayWebId() == null || infoPcket.getRelayWebId().equals("")) {
			infoPcket.setRelayWebId(returnWebId(urlRelay));
		}
		logger.info("Exit PlugLoadInformationPacket " );
	}

	private String returnWebId(URL url) {
		logger.info("Enter PlugLoadInformationPacket # param "+url );
		try {
			//System.out.println(url);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Authorization", "Basic " + base64encodedUserIDandPassword);
		//	System.out.println("Trying to connect");
			conn.connect();
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String stringOutput = br.readLine();
			conn.setConnectTimeout(10000);
			conn.disconnect();
			//System.out.println(stringOutput);
			try {
				JSONObject obj = new JSONObject(stringOutput);

				//System.out.println(obj);
				return (new JSONObject(stringOutput)).get("WebId").toString();
			} catch (JSONException e) {
				logger.error("JSONException"+e.toString());
			}
		} catch (IOException e) {
			logger.error("IOException"+e.toString());
		}
		logger.info("Exit PlugLoadInformationPacket" );
		return "";
		
	}

	private void getConnectionAndFlushdata(URL url, String value, String strTimestamp) throws IOException {
		logger.info("Enter getConnectionAndFlushdata ## param "+url+"--"+value );
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod(CommunicationServiceConstants.POST);
		conn.setRequestProperty("Content-Type", "text/json");
		conn.setRequestProperty("Authorization", "Basic " + base64encodedUserIDandPassword);
		String jsonTagPiS = "{\"Timestamp\":\"" + strTimestamp + "\"," + "\"Value\":" + value + "}";
		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(jsonTagPiS);
		wr.flush();
		wr.close();
		conn.setConnectTimeout(CommunicationServiceConstants.TIMEOUT);
		conn.connect();
		if ((conn.getResponseCode() != 202) && (conn.getResponseCode() != 204)) {
			conn.disconnect();
			//System.out.println("Error" + conn.getResponseCode());
		} else {
			//System.out.println("Written Successfully");
		}
		conn.disconnect();
		logger.info("Exit getConnectionAndFlushdata" );
	}
}
