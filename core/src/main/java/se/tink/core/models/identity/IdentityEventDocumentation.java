package se.tink.core.models.identity;

public class IdentityEventDocumentation {

	private String helpBody;
	private String helpTitle;
	private String infoBody;
	private String infoTitle;

	public void setHelpBody(String helpBody) {
		this.helpBody = helpBody;
	}

	public void setHelpTitle(String helpTitle) {
		this.helpTitle = helpTitle;
	}

	public void setInfoBody(String infoBody) {
		this.infoBody = infoBody;
	}

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	public String getInfoTitle() {
		return infoTitle;
	}

	public String getInfoBody() {
		return infoBody;
	}

	public String getHelpTitle() {
		return helpTitle;
	}

	public String getHelpBody() {
		return helpBody;
	}
}
