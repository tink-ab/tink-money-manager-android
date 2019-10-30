package se.tink.repository.cache;

import se.tink.core.models.feed.EventsPage;
import se.tink.core.models.feed.HtmlHead;
import se.tink.privacy.Clearable;
import se.tink.privacy.DataWipeManager;

public class FeedInMemoryCache implements Clearable {

	HtmlHead head;
	EventsPage page;

	public FeedInMemoryCache() {
		DataWipeManager.sharedInstance().register(this);
	}

	public HtmlHead getHead() {
		return head;
	}

	public void setHead(HtmlHead head) {
		this.head = head;
	}

	public EventsPage getPage() {
		return page;
	}

	public void setPage(EventsPage page) {
		this.page = page;
	}

	public void clear() {
		head = null;
		page = null;
	}
}
