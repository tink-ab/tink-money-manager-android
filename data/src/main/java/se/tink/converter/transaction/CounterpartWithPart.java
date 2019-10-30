package se.tink.converter.transaction;

import androidx.annotation.NonNull;
import java.util.Objects;
import se.tink.grpc.v1.models.Transaction.Counterpart;
import se.tink.grpc.v1.models.Transaction.Part;

final class CounterpartWithPart {

	@NonNull
	private final se.tink.grpc.v1.models.Transaction.Counterpart counterpart;
	@NonNull
	private final Part part;

	CounterpartWithPart(
		@NonNull se.tink.grpc.v1.models.Transaction.Counterpart counterpart,
		@NonNull Part part) {
		this.counterpart = counterpart;
		this.part = part;
	}

	@NonNull
	public Counterpart getCounterpart() {
		return counterpart;
	}

	@NonNull
	public Part getPart() {
		return part;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CounterpartWithPart that = (CounterpartWithPart) o;
		return Objects.equals(counterpart, that.counterpart) &&
			Objects.equals(part, that.part);
	}

	@Override
	public int hashCode() {
		return Objects.hash(counterpart, part);
	}

	@Override
	public String toString() {
		return "CounterpartWithPart{" +
			"counterpart=" + counterpart +
			", part=" + part +
			'}';
	}
}
