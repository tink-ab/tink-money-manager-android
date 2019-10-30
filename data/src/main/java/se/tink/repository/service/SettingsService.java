package se.tink.repository.service;

import se.tink.core.models.device.UpdateI18NSettingsRequest;
import se.tink.core.models.device.UpdateI18NSettingsResponse;
import se.tink.core.models.user.NotificationSettings;
import se.tink.core.models.user.PeriodSetting;
import se.tink.core.models.user.UserProfile;
import se.tink.repository.MutationHandler;

public interface SettingsService {

	void getNotificationSettings(MutationHandler<NotificationSettings> mutationHandler);

	void updateNotificationSettings(NotificationSettings notificationSettings,
		MutationHandler<NotificationSettings> mutationHandler);

	void getPeriodSettings(MutationHandler<PeriodSetting> mutationHandler);

	void updatePeriodSettings(PeriodSetting periodSettings,
		MutationHandler<PeriodSetting> mutationHandler);

	void updateI18NSettings(UpdateI18NSettingsRequest request,
		MutationHandler<UpdateI18NSettingsResponse> mutationHandler);

	void getUserProfile(MutationHandler<UserProfile> mutationHandler);
}
