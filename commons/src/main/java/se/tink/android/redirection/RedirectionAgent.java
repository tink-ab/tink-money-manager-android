package se.tink.android.redirection;

public interface RedirectionAgent {
	boolean redirectWithUrl(String url, RedirectionReceiver redirectionReceiver, boolean topOnly);
}
