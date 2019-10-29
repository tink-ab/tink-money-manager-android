package se.tink.core.models.follow;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.joda.time.DateTime;
import se.tink.core.models.misc.ExactNumber;
import se.tink.core.models.misc.Period;

public class FollowItem implements Parcelable {

	private ExpensesFollowCriteria expensesCriteria;
	private SearchFollowCriteria searchCriteria;
	private SavingsFollowCriteria savingCriteria;
	private FollowData data;
	private String id;
	private String name;
	private ExactNumber progress;
	private DateTime createdDate;

	public FollowItem() {
	}

	protected FollowItem(Parcel in) {
		id = in.readString();
		name = in.readString();
		expensesCriteria = in.readParcelable(ExpensesFollowCriteria.class.getClassLoader());
		searchCriteria = in.readParcelable(SearchFollowCriteria.class.getClassLoader());
		savingCriteria = in.readParcelable(SavingsFollowCriteria.class.getClassLoader());
		data = in.readParcelable(FollowData.class.getClassLoader());
		progress = in.readParcelable(ExactNumber.class.getClassLoader());
		createdDate = new DateTime(in.readLong());
	}

	public static final Creator<FollowItem> CREATOR = new Creator<FollowItem>() {
		@Override
		public FollowItem createFromParcel(Parcel in) {
			return new FollowItem(in);
		}

		@Override
		public FollowItem[] newArray(int size) {
			return new FollowItem[size];
		}
	};

	public ExpensesFollowCriteria getExpensesCriteria() {
		return expensesCriteria;
	}

	public void setExpensesCriteria(ExpensesFollowCriteria expensesCriteria) {
		this.expensesCriteria = expensesCriteria;
	}

	public SearchFollowCriteria getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(SearchFollowCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public SavingsFollowCriteria getSavingCriteria() {
		return savingCriteria;
	}

	public void setSavingCriteria(SavingsFollowCriteria savingCriteria) {
		this.savingCriteria = savingCriteria;
	}

	public FollowData getData() {
		return data;
	}

	public void setData(FollowData data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ExactNumber getProgress() {
		return progress;
	}

	public void setProgress(ExactNumber progress) {
		this.progress = progress;
	}

	public ExactNumber getLastHistoricalAmountOrZero() {
		FollowData data = getData();

		if (data == null) {
			return ExactNumber.ZERO;
		}

		List<PeriodExactNumberPair> historicalAmounts = data.getHistoricalAmounts();

		if (historicalAmounts.isEmpty()) {
			return ExactNumber.ZERO;
		}

		ExactNumber lastHistoricalAmountOrZero = historicalAmounts.get(historicalAmounts.size() - 1).getValue();

		if (lastHistoricalAmountOrZero == null) {
			return ExactNumber.ZERO;
		}

		return lastHistoricalAmountOrZero;
	}

	public ExactNumber getLastHistoricalAmountFor(Period period) {
		ExactNumber lastHistoricalAmountOrZero;
		List<PeriodExactNumberPair> historicalAmounts = getData().getHistoricalAmounts();
		List<PeriodExactNumberPair> filteredAmounts = new LinkedList<>();

		for (PeriodExactNumberPair pair : historicalAmounts) {
			if (Objects.equals(pair.getPeriod(), period)) {
				filteredAmounts.add(pair);
			}
		}

		if (filteredAmounts.isEmpty()) {
			return ExactNumber.ZERO;
		}

		lastHistoricalAmountOrZero = filteredAmounts.get(filteredAmounts.size() - 1).getValue();

		if (lastHistoricalAmountOrZero == null) {
			return ExactNumber.ZERO;
		}

		return lastHistoricalAmountOrZero;
	}

	public boolean hasGoal() {
		if (getSavingCriteria() == null) {
			return false;
		}
		if (!getSavingCriteria().getTargetAmount().isBiggerThan(ExactNumber.ZERO)) {
			return false;
		}
		String targetPeriod = getSavingCriteria().getPeriod();
		if (targetPeriod == null || targetPeriod.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int i) {
		dest.writeString(id);
		dest.writeString(name);
		dest.writeParcelable(expensesCriteria, i);
		dest.writeParcelable(searchCriteria, i);
		dest.writeParcelable(savingCriteria, i);
		dest.writeParcelable(data, i);
		dest.writeParcelable(progress, i);
		dest.writeLong(createdDate.getMillis());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		FollowItem that = (FollowItem) o;

		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
