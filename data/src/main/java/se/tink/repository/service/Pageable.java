package se.tink.repository.service;

public interface Pageable<T> {

	void next(PagingHandler handler);

	boolean getHasMore();
}