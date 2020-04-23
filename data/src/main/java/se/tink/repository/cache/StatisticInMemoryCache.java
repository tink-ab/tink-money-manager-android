package se.tink.repository.cache;


import se.tink.core.models.statistic.StatisticTree;
import se.tink.privacy.Clearable;
import se.tink.privacy.DataWipeManager;

public class StatisticInMemoryCache implements StasticCache, Clearable {

	private StatisticTree tree;

	public StatisticInMemoryCache() {
		DataWipeManager.sharedInstance().register(this);
	}

	@Override
	public StatisticTree read() {
		return tree;
	}

	@Override
	public void clearAndInsert(StatisticTree item) {
		tree = item;
	}

	@Override
	public void insert(StatisticTree item) {
		tree = item;
	}

	@Override
	public void update(StatisticTree item) {
		tree.updateWith(item);
	}

	@Override
	public void delete(StatisticTree item) {
		if (item.equals(tree)) {
			tree = null;
		}
	}

	@Override
	public void clear() {
		tree = null;
	}
}
