package se.tink.core.models.statistic;

import java.util.HashMap;
import java.util.Map;
import se.tink.core.models.misc.Amount;
import se.tink.core.models.misc.Period;

public class Statistic {

	private Period period;
	private Amount amount;
	private Map<String, Statistic> children;

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public Map<String, Statistic> getChildren() {
		if (children == null) {
			return null;
		}
		return new HashMap<>(children);
	}

	public boolean hasChildren() {
		return children != null;
	}

	public void setChildren(Map<String, Statistic> children) {
		this.children = children;
	}

	public enum Type {
		TYPE_UNKNOWN,
		TYPE_BALANCES_BY_ACCOUNT,
		TYPE_BALANCES_BY_ACCOUNT_TYPE_GROUP,
		TYPE_LEFT_TO_SPEND,
		TYPE_LEFT_TO_SPEND_AVERAGE,
		TYPE_BY_CATEGORY,
	}
}
