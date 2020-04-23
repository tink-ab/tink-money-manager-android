package se.tink.privacy;


import java.util.LinkedList;
import java.util.List;

public class DataWipeManager {

	private List<Clearable> clearables = new LinkedList<>();

	private static DataWipeManager instance;

	private DataWipeManager() {
	}

	public static DataWipeManager sharedInstance() {
		if (instance == null) {
			instance = new DataWipeManager();
		}
		return instance;
	}

	public void register(Clearable c) {
		clearables.add(c);
	}

	public void wipeData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (Clearable c : clearables) {
					c.clear();
				}
			}
		}).start();
	}
}
