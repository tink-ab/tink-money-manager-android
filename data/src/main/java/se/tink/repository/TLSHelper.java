package se.tink.repository;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.security.auth.x500.X500Principal;

public class TLSHelper {

	private static TrustManager[] getTrustManagers(InputStream ca) throws Exception {
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(null);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(ca);
		X500Principal principal = cert.getSubjectX500Principal();
		ks.setCertificateEntry(principal.getName("RFC2253"), cert);
		// Set up trust manager factory to use our key store.
		TrustManagerFactory trustManagerFactory =
			TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustManagerFactory.init(ks);
		return trustManagerFactory.getTrustManagers();
	}

	public static SSLSocketFactory getSslSocketFactory(InputStream ca)
		throws Exception {
		if (ca == null) {
			return (SSLSocketFactory) SSLSocketFactory.getDefault();
		}

		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, getTrustManagers(ca), null);
		return context.getSocketFactory();
	}

}