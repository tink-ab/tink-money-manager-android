package se.tink.converter.activity;

import se.tink.converter.ModelConverter;
import se.tink.converter.ModelConverterImpl;
import se.tink.core.models.feed.HtmlHead;
import se.tink.grpc.v1.rpc.ActivityHtmlHeadResponse;
import se.tink.modelConverter.AbstractConverter;

public class ActivityHtmlHeadResponseConverter extends
	AbstractConverter<ActivityHtmlHeadResponse, HtmlHead> {

	private ModelConverter modelConverter;

	public ActivityHtmlHeadResponseConverter(ModelConverterImpl modelConverter) {
		this.modelConverter = modelConverter;
	}

	@Override
	public HtmlHead convert(ActivityHtmlHeadResponse source) {
		HtmlHead head = new HtmlHead();
		head.setCss(source.getCss());
		head.setMetadata(source.getMetadata());
		head.setScripts(source.getScripts());
		return head;
	}

	@Override
	public Class<ActivityHtmlHeadResponse> getSourceClass() {
		return ActivityHtmlHeadResponse.class;
	}

	@Override
	public Class<HtmlHead> getDestinationClass() {
		return HtmlHead.class;
	}
}
