package se.tink.helpers;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import java.util.Collection;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public class CollectionsHelper {

	public static <T, R> Collection<T> unionOn(Collection<T> returned,
		final Collection<T> comparedTo, final Function<T, R> acquireValueToCompare) {
		Collection<T> union = Collections2.filter(returned, new Predicate<T>() {
			@Override
			public boolean apply(@Nullable final T input) {
				Collection sameValue = Collections2.filter(comparedTo, new Predicate<T>() {
					@Override
					public boolean apply(@Nullable T input2) {
						return Objects.equals(acquireValueToCompare.apply(input),
							acquireValueToCompare.apply(input2));
					}
				});
				return !sameValue.isEmpty();
			}
		});
		return union;
	}

	public static <T, R> Collection<T> setDifferenceOn(final Collection<T> u, final Collection<T> r,
		final Function<T, R> acquireValueToCompare) {
		Collection<T> difference = Collections2.filter(u, new Predicate<T>() {
			@Override
			public boolean apply(@Nullable final T input) {
				Collection sameValue = Collections2.filter(r, new Predicate<T>() {
					@Override
					public boolean apply(@Nullable T input2) {
						return Objects.equals(acquireValueToCompare.apply(input),
							acquireValueToCompare.apply(input2));
					}
				});
				return sameValue.isEmpty();
			}
		});
		return difference;

	}

	public static <T> T findFirst(Collection<T> collection, Predicate<T> predicate) {
		for(T elem : collection) {
			if(predicate.apply(elem)) {
				return elem;
			}
		}
		return null;
	}

	public static <T> boolean contains(Collection<T> collection, Predicate<T> predicate) {
		for(T elem : collection) {
			if(predicate.apply(elem)) {
				return true;
			}
		}
		return false;
	}
}
