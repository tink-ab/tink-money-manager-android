package se.tink.core.models.consents;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Consent implements Parcelable {

	private String key;
	private String title;
	private String body;
	private String checksum;
	private String version;
	private List<ConsentMessage> messageList;
	private Map<String, String> attachmentsMap;

	public Consent() {
	}

	protected Consent(Parcel in) {
		key = in.readString();
		title = in.readString();
		body = in.readString();
		checksum = in.readString();
		version = in.readString();
		int size = in.readInt();

		attachmentsMap = new HashMap<>();
		for (int i = 0; i < size; i++) {
			String key = in.readString();
			String value = in.readString();
			attachmentsMap.put(key, value);
		}
	}

	public static final Creator<Consent> CREATOR = new Creator<Consent>() {
		@Override
		public Consent createFromParcel(Parcel in) {
			return new Consent(in);
		}

		@Override
		public Consent[] newArray(int size) {
			return new Consent[size];
		}
	};

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public List<ConsentMessage> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<ConsentMessage> messageList) {
		this.messageList = messageList;
	}

	public Map<String, String> getAttachmentsMap() {
		return attachmentsMap;
	}

	public void setAttachmentsMap(Map<String, String> attachmentsMap) {
		this.attachmentsMap = attachmentsMap;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(key);
		dest.writeString(title);
		dest.writeString(body);
		dest.writeString(checksum);
		dest.writeString(version);
		dest.writeMap(attachmentsMap);
		dest.writeInt(attachmentsMap.size());
		for (Map.Entry<String, String> entry : attachmentsMap.entrySet()) {
			dest.writeString(entry.getKey());
			dest.writeString(entry.getValue());
		}
	}
}
