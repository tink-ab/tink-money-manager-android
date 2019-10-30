package se.tink.converter.tracking

import se.tink.core.models.tracking.TrackingConfigurationResponse
import se.tink.grpc.v1.rpc.GetTrackingConfigurationResponse
import se.tink.modelConverter.AbstractConverter


class TrackingConfigurationResponseDTOToTrackingConfigurationResponseConverter :
    AbstractConverter<GetTrackingConfigurationResponse, TrackingConfigurationResponse>() {
    override fun convert(source: GetTrackingConfigurationResponse): TrackingConfigurationResponse {
        return TrackingConfigurationResponse(
            trackingUserId = source.trackingUserId,
            trackingUsername = source.trackingUsername
        )
    }

    override fun getSourceClass(): Class<GetTrackingConfigurationResponse> {
        return GetTrackingConfigurationResponse::class.java
    }

    override fun getDestinationClass(): Class<TrackingConfigurationResponse> {
        return TrackingConfigurationResponse::class.java
    }
}