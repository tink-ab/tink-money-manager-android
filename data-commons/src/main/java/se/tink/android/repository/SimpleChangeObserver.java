package se.tink.android.repository;

import java.util.List;
import se.tink.android.livedata.ListChangeObserver;

public class SimpleChangeObserver<T> implements ListChangeObserver<T> {

	@Override
	public void onCreate(List<? extends T> items) {

	}

	@Override
	public void onRead(List<? extends T> items) {

	}

	@Override
	public void onUpdate(List<? extends T> items) {

	}

	@Override
	public void onDelete(List<? extends T> items) {

	}
}
