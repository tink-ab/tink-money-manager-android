package se.tink.repository.service;

import java.util.List;
import se.tink.core.models.follow.FollowItem;
import se.tink.core.models.follow.PeriodExactNumberPair;
import se.tink.repository.ChangeObserver;
import se.tink.repository.MutationHandler;

public interface FollowService extends TinkService {

	void subscribe(ChangeObserver<FollowItem> listener);

	void unsubscribe(ChangeObserver<FollowItem> listener);

	void createFollowItem(FollowItem item, final MutationHandler<FollowItem> handler);

	void deleteFollowItem(FollowItem item, final MutationHandler<Void> handler);

	void updateFollowItem(FollowItem item, MutationHandler<FollowItem> mutationHandler);

	void getDailyHistoryForFollowItem(
		FollowItem item,
		MutationHandler<List<PeriodExactNumberPair>> mutationHandler);
}
