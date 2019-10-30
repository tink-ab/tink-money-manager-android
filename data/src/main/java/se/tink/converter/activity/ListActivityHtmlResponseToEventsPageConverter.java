package se.tink.converter.activity;

import se.tink.converter.ModelConverter;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.feed.EventsPage;
import se.tink.grpc.v1.rpc.ListActivityHtmlResponse;
import se.tink.modelConverter.AbstractConverter;

public class ListActivityHtmlResponseToEventsPageConverter extends
	AbstractConverter<ListActivityHtmlResponse, EventsPage> {

	private ModelConverter modelConverter;

	public ListActivityHtmlResponseToEventsPageConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public EventsPage convert(ListActivityHtmlResponse source) {
		EventsPage page = new EventsPage();
		page.setHtml(source.getHtmlPage());
		page.setNextPageOffset(source.getNextPageOffset());
		if (source.getActivityKeysCount() > 0) {
			page.setKeys(source.getActivityKeysList());
		}
		if (source.getFeedActivityIdentifiersListCount() > 0) {
			page.setIdentifiers(source.getFeedActivityIdentifiersListList());
		}
		return page;
	}

	@Override
	public Class<ListActivityHtmlResponse> getSourceClass() {
		return ListActivityHtmlResponse.class;
	}

	@Override
	public Class<EventsPage> getDestinationClass() {
		return EventsPage.class;
	}
}
