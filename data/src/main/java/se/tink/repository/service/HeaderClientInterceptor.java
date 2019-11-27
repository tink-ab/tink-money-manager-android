package se.tink.repository.service;

import com.google.common.base.Strings;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall.SimpleForwardingClientCall;
import io.grpc.ForwardingClientCallListener.SimpleForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.stub.MetadataUtils;


public class HeaderClientInterceptor implements ClientInterceptor {

	public static final Metadata.Key<String> AUTHORIZATION = Metadata.Key
		.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
	public static final Metadata.Key<String> CLIENT_KEY_HEADER_NAME = Metadata.Key
		.of("X-Tink-Client-Key", Metadata.ASCII_STRING_MARSHALLER);
	public static final Metadata.Key<String> DEVICE_ID_HEADER_NAME = Metadata.Key
		.of("X-Tink-Device-ID", Metadata.ASCII_STRING_MARSHALLER);
	public static final Metadata.Key<String> OAUTH_CLIENT_ID_HEADER_NAME = Metadata.Key
		.of("X-Tink-OAuth-Client-ID", Metadata.ASCII_STRING_MARSHALLER);
	public static final Metadata.Key<String> REMOTE_ADDRESS = Metadata.Key
		.of("X-Forwarded-For", Metadata.ASCII_STRING_MARSHALLER);
	public static final Metadata.Key<String> USER_AGENT = Metadata.Key
		.of("User-Agent", Metadata.ASCII_STRING_MARSHALLER);

	private String accessToken;
	private final String deviceId;
	private String userAgent;

	public HeaderClientInterceptor(String deviceId, String userAgent) {
		this.deviceId = deviceId;
		this.userAgent = userAgent;
	}

	@Override
	public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
		CallOptions callOptions, final Channel next) {
		return new SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {

			@Override
			public void start(Listener<RespT> responseListener, Metadata headers) {
				/* Set custom headers */
				if (!Strings.isNullOrEmpty(deviceId)) {
					headers.put(DEVICE_ID_HEADER_NAME, deviceId);
				}
				if (!Strings.isNullOrEmpty(accessToken)) {
					headers.put(AUTHORIZATION, "Bearer " + accessToken);
				}

				super.start(new SimpleForwardingClientCallListener<RespT>(responseListener) {
					@Override
					public void onHeaders(Metadata headers) {
						/*
						 * if you don't need receive header from server,
						 * you can use {@link io.grpc.stub.MetadataUtils#attachHeaders}
						 * directly to send header
						 */
						MetadataUtils.newAttachHeadersInterceptor(headers);
						super.onHeaders(headers);
					}
				}, headers);
			}
		};

	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
