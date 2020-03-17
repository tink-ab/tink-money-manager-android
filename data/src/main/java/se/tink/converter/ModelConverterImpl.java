package se.tink.converter;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import se.tink.converter.account.AccountDTOToAccountConverter;
import se.tink.converter.account.AccountToAccountDTOConverter;
import se.tink.converter.account.UpdateAccountRequestConverter;
import se.tink.converter.account.accountDetails.AccountDetailsDTOtoAccountDetailsConverter;
import se.tink.converter.account.accountDetails.AccountDetailsToAccountDetailsDTOConverter;
import se.tink.converter.account.loan.LoanToLoanDTOConverter;
import se.tink.converter.authentication.AuthenticationResponseConverter;
import se.tink.converter.authentication.AuthenticationStatusConverter;
import se.tink.converter.authentication.ThirdPartyAppAuthenticationDTOToThirdPartyAppAuthenticationConverter;
import se.tink.converter.category.CategoryDTOToCategoryConverter;
import se.tink.converter.category.CategoryToCategoryDTOConverter;
import se.tink.converter.category.CategoryTreeDTOToCategoryTreeConverter;
import se.tink.converter.date.DateTimeToDateConverter;
import se.tink.converter.date.DateTimeToTimestampConverter;
import se.tink.converter.date.DateToDateTimeConverter;
import se.tink.converter.date.DateToTimestampConverter;
import se.tink.converter.date.TimestampToDateTimeConverter;
import se.tink.converter.date.TimestampToLongConverter;
import se.tink.converter.device.AuthenticationMethodEnumResponsConverter;
import se.tink.converter.device.DeviceConfigurationMarketResponseConverter;
import se.tink.converter.device.DeviceConfigurationResponseConverter;
import se.tink.converter.device.UpdateI18NSettingsRequestConverter;
import se.tink.converter.device.UpdateI18NSettingsResponseConverter;
import se.tink.converter.misc.AmountDTOToAmountConverter;
import se.tink.converter.misc.AmountToAmountDTOConverter;
import se.tink.converter.misc.ExactNumberDTOToExactNumberConverter;
import se.tink.converter.misc.ExactNumberToExactNumberDTOConverter;
import se.tink.converter.misc.FieldDTOToFieldConverter;
import se.tink.converter.misc.FieldToFieldDTOConverter;
import se.tink.converter.misc.ImagesConverter;
import se.tink.converter.misc.PeriodDTOToPeriodConverter;
import se.tink.converter.misc.PeriodToPeriodDTOConverter;
import se.tink.converter.misc.StringToStringValueConverter;
import se.tink.converter.misc.YearMonthDTOToYearMonthConverter;
import se.tink.converter.misc.YearWeekDTOToYearWeekConverter;
import se.tink.converter.provider.ProviderDTOToProviderConverter;
import se.tink.converter.provider.ProviderToProviderDTOConverter;
import se.tink.converter.pushnotifications.DeviceFromDTOConverter;
import se.tink.converter.pushnotifications.DeviceToDeviceDTOConverter;
import se.tink.converter.pushnotifications.RegisterPushNotificationTokenRequestToSendConverter;
import se.tink.converter.pushnotifications.ResponseToRegisterPushNotificationTokenResponseConverter;
import se.tink.converter.request.TransactionToUpdateTransactionRequest;
import se.tink.converter.settings.I18NSettingsConverter;
import se.tink.converter.settings.NotificationSettingsConverter;
import se.tink.converter.settings.NotificationSettingsResponseConverter;
import se.tink.converter.settings.NotificationSettingsUpdateRequestConverter;
import se.tink.converter.settings.PeriodSettingsConverter;
import se.tink.converter.settings.PeriodSettingsConverterUpdateRequest;
import se.tink.converter.statistic.StatisticDTOToStatisticConverter;
import se.tink.converter.statistic.StatisticToStatisticDTOConverter;
import se.tink.converter.statistic.StatisticTreeDTOStatisticTreeConverter;
import se.tink.converter.streaming.StringToStreamingResponseTypeConverter;
import se.tink.converter.tracking.TrackingConfigurationResponseDTOToTrackingConfigurationResponseConverter;
import se.tink.converter.tracking.TrackingEventToTrackingDTOConverter;
import se.tink.converter.tracking.TrackingRequestToTrackingRequestDTOConverter;
import se.tink.converter.tracking.TrackingTimingToTrackingTimingDTOConverter;
import se.tink.converter.tracking.TrackingViewToTrackingViewDTOConverter;
import se.tink.converter.transaction.QueryTransactionResponseDTOToSearchResultMetadataConverter;
import se.tink.converter.transaction.SuggestTransactionConverter;
import se.tink.converter.transaction.SuggestTransactionsResponseConverter;
import se.tink.converter.transaction.TagDTOToTagConverter;
import se.tink.converter.transaction.TagToTagDTOConverter;
import se.tink.converter.transaction.TransactionClusterConverter;
import se.tink.converter.transaction.TransactionDTOToTransactionConverter;
import se.tink.converter.transaction.TransactionDetailsDTOToTransactionDetailsConverter;
import se.tink.converter.transaction.TransactionDetailsToTransactionDetailsDTOConverter;
import se.tink.converter.transaction.TransactionToTransactionDTOConverter;
import se.tink.converter.user.CollectBankIdAuthenticationRequestDTOConverter;
import se.tink.converter.user.DeleteUserReasonConverter;
import se.tink.converter.user.EmailAndPasswordAuthenticationResponseDTOConverter;
import se.tink.converter.user.GetProfileResponseDTOConverter;
import se.tink.converter.user.InitiateBankIdAuthenticationResponseDTOConverter;
import se.tink.converter.user.LoginResponseDTOConverter;
import se.tink.converter.user.RegisterResponseMapper;
import se.tink.converter.user.UpdateEmailResponseDTOConverter;
import se.tink.converter.user.UserConfigurationDTOToUserConfigurationConverter;
import se.tink.converter.user.UserConfigurationToUserConfigurationDTOConverter;
import se.tink.converter.user.UserProfileDTOConverter;
import se.tink.modelConverter.AbstractConverter;
import se.tink.repository.cache.CacheConverterFactory;

public class ModelConverterImpl implements ModelConverter {

    private final Map<Integer, AbstractConverter> converterMap =
            new HashMap<Integer, AbstractConverter>();

    public ModelConverterImpl() {
        addConverters();
    }

    private <D> AbstractConverter getConverter(Class<?> aClass, Class<D> destinationType) {
        return converterMap.get(getHash(aClass, destinationType));
    }

    private <D> Integer getHash(Class<?> aClass, Class<D> destinationType) {
        int hashCode = 17;
        hashCode = hashCode * 31 + aClass.hashCode();
        hashCode = hashCode * 31 + destinationType.hashCode();
        return hashCode;
    }

    @Override
    public <KR, VR, KI, VI> Map<KR, VR> map(
            Map<KI, VI> source, Class<KR> destinationKeyType, Class<VR> destinationValueType) {
        Map<KR, VR> returnMap = Maps.newHashMap();
        Set<KI> keySet = source.keySet();
        for (KI key : keySet) {
            VI value = source.get(key);
            returnMap.put(map(key, destinationKeyType), map(value, destinationValueType));
        }
        return returnMap;
    }

    @Override
    public <S, D> List<D> map(Collection<S> source, Class<D> destinationType) {
        ArrayList<D> destinationList = new ArrayList<>();
        for (S sourceObject : source) {
            destinationList.add(map(sourceObject, destinationType));
        }
        return destinationList;
    }

    @Override
    public <S, D> D map(S source, Class<D> destinationType) {
        if (source.getClass().equals(destinationType)) {
            return (D) source;
        }
        AbstractConverter<S, D> converter = getConverter(source.getClass(), destinationType);
        return converter.convert(source);
    }

    private void addConverters() {
        setupAuthenticationConverters();
        setupAccountConverters();
        setupProviderConverters();
        setupStatisticConverters();
        setupTransactionConverters();
        setupSupportModelConverters();
        setupCategoryConverters();
        setupTrackingConverters();
        setupStreamingResponseConverter();
        setupRequestConverters();
        setupUserConfigurationConverters();
        setupUserConverters();
        setupSettingsConverters();
        setupPushNotificationsConverters();
        setupDeviceConfigurationConverters();
        setupCacheEntityConverters();
    }

    private void addConverter(AbstractConverter abstractConverter) {
        converterMap.put(
                getHash(
                        abstractConverter.getSourceClass(),
                        abstractConverter.getDestinationClass()),
                abstractConverter);
    }

    private void addConverters(List<AbstractConverter<?, ?>> converters) {
        for (AbstractConverter converter : converters) {
            addConverter(converter);
        }
    }

    private void setupAuthenticationConverters() {
        addConverter(new AuthenticationStatusConverter());
        addConverter(new AuthenticationResponseConverter(this));
        addConverter(
                new ThirdPartyAppAuthenticationDTOToThirdPartyAppAuthenticationConverter());
    }

    private void setupSettingsConverters() {
        addConverter(new PeriodSettingsConverter(this));
        addConverter(new PeriodSettingsConverterUpdateRequest(this));

        addConverter(new NotificationSettingsConverter(this));
        addConverter(new NotificationSettingsUpdateRequestConverter(this));
        addConverter(new NotificationSettingsResponseConverter(this));
    }

    private void setupUserConverters() {
        addConverter(new UserProfileDTOConverter());
        addConverter(new LoginResponseDTOConverter(this));
        addConverter(new CollectBankIdAuthenticationRequestDTOConverter(this));
        addConverter(new EmailAndPasswordAuthenticationResponseDTOConverter(this));
        addConverter(new UpdateEmailResponseDTOConverter(this));
        addConverter(new GetProfileResponseDTOConverter(this));
        addConverter(new RegisterResponseMapper(this));
        addConverter(new InitiateBankIdAuthenticationResponseDTOConverter(this));
        addConverter(new DeleteUserReasonConverter(this));
    }

    private void setupStreamingResponseConverter() {
        addConverter(new StringToStreamingResponseTypeConverter(this));
    }

    private void setupTrackingConverters() {
        addConverter(new TrackingEventToTrackingDTOConverter(this));
        addConverter(new TrackingRequestToTrackingRequestDTOConverter(this));
        addConverter(new TrackingTimingToTrackingTimingDTOConverter(this));
        addConverter(new TrackingViewToTrackingViewDTOConverter(this));
        addConverter(
                new TrackingConfigurationResponseDTOToTrackingConfigurationResponseConverter());
    }

    private void setupCategoryConverters() {
        addConverter(new CategoryDTOToCategoryConverter(this));
        addConverter(new CategoryToCategoryDTOConverter(this));
        addConverter(new CategoryTreeDTOToCategoryTreeConverter(this));
    }

    private void setupTransactionConverters() {
        addConverter(new TransactionDTOToTransactionConverter(this));
        addConverter(new TransactionToTransactionDTOConverter(this));

        addConverter(new TransactionDetailsDTOToTransactionDetailsConverter(this));
        addConverter(new TransactionDetailsToTransactionDetailsDTOConverter(this));

        addConverter(new TagToTagDTOConverter(this));
        addConverter(new TagDTOToTagConverter(this));

        addConverter(new SuggestTransactionConverter(this));
        addConverter(new SuggestTransactionsResponseConverter(this));
        addConverter(new TransactionClusterConverter(this));

        addConverter(new QueryTransactionResponseDTOToSearchResultMetadataConverter(this));
    }

    private void setupStatisticConverters() {
        addConverter(new StatisticTreeDTOStatisticTreeConverter(this));
        addConverter(new StatisticToStatisticDTOConverter(this));
        addConverter(new StatisticDTOToStatisticConverter(this));
    }

    private void setupProviderConverters() {
        addConverter(new ProviderDTOToProviderConverter(this));
        addConverter(new ProviderToProviderDTOConverter(this));
    }

    private void setupSupportModelConverters() {
        addConverter(new FieldToFieldDTOConverter(this));
        addConverter(new FieldDTOToFieldConverter(this));

        addConverter(new PeriodDTOToPeriodConverter(this));
        addConverter(new PeriodToPeriodDTOConverter(this));

        addConverter(new ExactNumberDTOToExactNumberConverter());
        addConverter(new ExactNumberToExactNumberDTOConverter(this));

        addConverter(new AmountDTOToAmountConverter());
        addConverter(new AmountToAmountDTOConverter(this));

        addConverter(new DateToDateTimeConverter());
        addConverter(new DateTimeToDateConverter());
        addConverter(new DateTimeToTimestampConverter());
        addConverter(new DateToTimestampConverter());

        addConverter(new TimestampToDateTimeConverter());
        addConverter(new TimestampToLongConverter(this));

        addConverter(new YearWeekDTOToYearWeekConverter());
        addConverter(new YearMonthDTOToYearMonthConverter());

        addConverter(new StringToStringValueConverter(this));
        addConverter(new ImagesConverter());
    }

    private void setupAccountConverters() {
        addConverter(new AccountDTOToAccountConverter(this));
        addConverter(new AccountToAccountDTOConverter(this));

        addConverter(new AccountDetailsToAccountDetailsDTOConverter(this));
        addConverter(new AccountDetailsDTOtoAccountDetailsConverter(this));

        addConverter(new UpdateAccountRequestConverter(this));

        addConverter(new LoanToLoanDTOConverter(this));
    }

    private void setupRequestConverters() {
        addConverter(new TransactionToUpdateTransactionRequest(this));
    }

    private void setupUserConfigurationConverters() {
        addConverter(new UserConfigurationToUserConfigurationDTOConverter(this));
        addConverter(new UserConfigurationDTOToUserConfigurationConverter(this));
    }

    private void setupPushNotificationsConverters() {
        addConverter(new RegisterPushNotificationTokenRequestToSendConverter(this));
        addConverter(new ResponseToRegisterPushNotificationTokenResponseConverter(this));
    }

    private void setupDeviceConfigurationConverters() {
        addConverter(new DeviceConfigurationMarketResponseConverter(this));
        addConverter(new DeviceConfigurationResponseConverter(this));
        addConverter(new DeviceFromDTOConverter(this));
        addConverter(new DeviceToDeviceDTOConverter(this));

        addConverter(new AuthenticationMethodEnumResponsConverter(this));
        addConverter(new UpdateI18NSettingsRequestConverter(this));
        addConverter(new UpdateI18NSettingsResponseConverter(this));
        addConverter(new I18NSettingsConverter());
    }

    private void setupCacheEntityConverters() {
        addConverters(CacheConverterFactory.getCacheConverters(this));
    }
}
