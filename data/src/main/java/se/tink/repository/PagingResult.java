package se.tink.repository;

public class PagingResult {

	private final boolean hasMore;

	public PagingResult(boolean hasMore) {
		this.hasMore = hasMore;
	}

	public boolean getHasMore() {
		return hasMore;
	}

}
