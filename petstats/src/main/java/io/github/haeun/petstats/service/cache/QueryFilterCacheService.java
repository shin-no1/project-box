package io.github.haeun.petstats.service.cache;

import io.github.haeun.petstats.domain.animalStats.AnimalStatsQueryRepository;
import io.github.haeun.petstats.domain.animalStats.AnimalStatsRepository;
import io.github.haeun.petstats.domain.region.Region;
import io.github.haeun.petstats.domain.region.RegionRepository;
import io.github.haeun.petstats.domain.rfidType.RfidType;
import io.github.haeun.petstats.domain.rfidType.RfidTypeRepository;
import io.github.haeun.petstats.domain.species.Species;
import io.github.haeun.petstats.domain.species.SpeciesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class QueryFilterCacheService {
    private final AnimalStatsQueryRepository animalStatsQueryRepository;

    final int CACHE_EXPIRATION = 60 * 60 * 24;

    @Cacheable(value = "birthYears")
    public List<Integer> getBirthYears() {
        log.info("[Cacheable] birthYears");
        return animalStatsQueryRepository.getBirthYears();
    }

    @Scheduled(fixedDelay = CACHE_EXPIRATION)
    @CacheEvict(value = "birthYears", allEntries = true)
    public void evictBirthYears() {
        log.info("[CacheEvict] birthYears");
    }

}
