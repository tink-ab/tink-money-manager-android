package se.tink.repository.service;

import se.tink.repository.PagingResult;
import se.tink.repository.TinkNetworkErrorable;

public interface PagingHandler extends TinkNetworkErrorable {

	void onCompleted(PagingResult result);
}
