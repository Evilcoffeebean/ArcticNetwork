package dev.arctic.core.object;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Getter;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Zvijer on 27.7.2017..
 */
public class AccountCache {

    private @Getter static LoadingCache<UUID, ArcticPlayer> playerCache;

    static {
        playerCache = CacheBuilder.newBuilder().maximumSize(100L).expireAfterAccess(2L, TimeUnit.HOURS)
                .build(new CacheLoader<UUID, ArcticPlayer>() {

                    @Override
                    @ParametersAreNonnullByDefault
                    public ArcticPlayer load(UUID uuid) throws Exception {
                        return new ArcticPlayer(uuid);
                    }

                });
    }

    public ArcticPlayer getPlayer(final UUID uuid) {
        ArcticPlayer pl = null;
        try {
            pl = playerCache.get(uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pl;
    }

    public void invalidate(final UUID uuid) {
        playerCache.invalidate(uuid);
    }
}
