package uk.co.stircomp.emojemap.data;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class BloombergRequest {

	public static final String apiUrl = "https://http-api.openbloomberg.com"
			+ "/request?ns=blp&service=refdata&type=ReferenceDataRequest";
	public static final String keyStorePW = "secure";
	public static final String trustStorePW = "secure2";
	public static final String clientCert = "client.p12";
	public static final String bbCert = "bloomberg.jks";
	
	private String[] tickers, fields;

	public BloombergRequest(String[] tickers, String[] fields) {
		
		this.tickers = tickers;
		this.fields = fields;		
		
		make();

	}
	
	private void buildJSON(HttpsURLConnection urlConn) throws IOException {
		
		// Example
		//String example = "{\"securities\": [\"EULD60GR Index\"],\"fields\": [ \"REGION_OR_COUNTRY\",\"PX_LAST\"]}";
		
		DataOutputStream wr = new DataOutputStream(urlConn.getOutputStream());
		
		// Open
		wr.write("{".getBytes());
		System.out.print("{");
		
		// Write securities.
		wr.write("\"securities\":[".getBytes());	
		System.out.print("\"securities\":[");
		for (int s = 0; s < this.tickers.length; s++) {
			wr.write("\"".getBytes());
			System.out.print("\"");
			wr.write(tickers[s].getBytes());
			System.out.print(tickers[s]);
			if (s < tickers.length - 1) {
				wr.write("\",".getBytes());
				System.out.print("\",");
			} else {
				wr.write("\"".getBytes());
				System.out.print("\"");
			}
		}		
		wr.write("],".getBytes());
		System.out.print("],");
		
		// Write fields.
				wr.write("\"fields\":[".getBytes());	
				System.out.print("\"");
				for (int s = 0; s < this.fields.length; s++) {
					wr.write("\"".getBytes());
					wr.write(fields[s].getBytes());
					if (s < fields.length - 1) {
						wr.write("\",".getBytes());
						System.out.print("\",");
					} else {
						wr.write("\"".getBytes());
						System.out.print("\"");
					}
				}		
				wr.write("]".getBytes());
		
		// Close
		wr.write("}".getBytes());
		System.out.print("}");
		
		wr.flush();
		wr.close();
		
	}

	private void make() {

		try {
			// load the client public/private key from PKCS12
			KeyStore clientStore = KeyStore.getInstance("PKCS12");
			clientStore.load(new FileInputStream(clientCert),
					keyStorePW.toCharArray());

			KeyManagerFactory kmf = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(clientStore, keyStorePW.toCharArray());
			KeyManager[] kms = kmf.getKeyManagers();

			// load the public key of the CA from JKS,
			// so we can verify the server certificate.
			KeyStore trustStore = KeyStore.getInstance("JKS");
			trustStore.load(new FileInputStream(bbCert),
					trustStorePW.toCharArray());

			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(trustStore);
			TrustManager[] tms = tmf.getTrustManagers();

			// initialize the SSLContext with the keys,
			// KeyManager: client public/private key, TrustManager: server
			// public key
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(kms, tms, new SecureRandom());

			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext 
					.getSocketFactory());
			URL url = new URL(apiUrl);

			// open connection to the server
			HttpsURLConnection urlConn = (HttpsURLConnection) url
					.openConnection();

			urlConn.setRequestMethod("POST");
			urlConn.setRequestProperty("User-Agent", "blpapi-http-java-example");
			urlConn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			urlConn.setDoOutput(true);
			urlConn.setRequestProperty("Content-Type",
					"application/json; charset=utf8");

			// write the json request to the output stream
			buildJSON( urlConn );
			
			// read the whatever we get back
			int responseCode = urlConn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			System.out.println(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
