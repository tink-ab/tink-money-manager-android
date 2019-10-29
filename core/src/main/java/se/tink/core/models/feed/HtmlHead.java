package se.tink.core.models.feed;

public class HtmlHead {

	private String css;
	private String scripts;
	private String metadata;

	public HtmlHead() {
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}


	public String getScripts() {
		return scripts;
	}

	public void setScripts(String scripts) {
		this.scripts = scripts;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public String toHtmlString() {
		return "<head>" + getCssHtmlString() + getScripts() + "</head>\n";
	}

	private String getCssHtmlString() {
		return "<style>" + getCss() + "</style>\n";
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (!other.getClass().equals(this.getClass())) {
			return false;
		}

		HtmlHead otherHead = (HtmlHead) other;

		if (!(this.getCss() == null && otherHead.getCss() == null) ||
			!(this.getMetadata() == null && otherHead.getMetadata() == null) ||
			!(this.getScripts() == null && otherHead.getScripts() == null) ||
			!this.getCss().equals(otherHead.getCss()) ||
			!this.getScripts().equals(otherHead.getScripts()) ||
			!this.getMetadata().equals((otherHead.getMetadata()))) {
			return false;
		}
		return true;
	}
}
