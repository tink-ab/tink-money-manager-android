package se.tink.repository.cache;

import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {CacheModule.class})
public interface CacheComponent {

}
