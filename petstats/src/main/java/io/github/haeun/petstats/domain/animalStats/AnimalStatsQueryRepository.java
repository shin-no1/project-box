package io.github.haeun.petstats.domain.animalStats;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.haeun.petstats.domain.animalType.QAnimalType;
import io.github.haeun.petstats.domain.region.QRegion;
import io.github.haeun.petstats.domain.species.QSpecies;
import io.github.haeun.petstats.web.dto.RegionResponse;
import io.github.haeun.petstats.web.dto.RegionTopAnimalTypeRequest;
import io.github.haeun.petstats.web.dto.RegionTopAnimalTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class AnimalStatsQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<RegionTopAnimalTypeResponse> getTopBreedsBySpecies(Integer regionId, Integer speciesId) {
        QAnimalStats stats = QAnimalStats.animalStats;
        QAnimalType type = QAnimalType.animalType;
        QSpecies species = QSpecies.species;

        return queryFactory
                .select(Projections.constructor(
                        RegionTopAnimalTypeResponse.class,
                        species.name,
                        type.name,
                        stats.animalCount.sum()
                ))
                .from(stats)
                .join(stats.animalType, type)
                .join(type.species, species)
                .where(
                        regionId != null ? stats.region.id.eq(regionId) : null,
                        species.id.eq(speciesId)
                )
                .groupBy(type.id, species.name, type.name)
                .orderBy(stats.animalCount.sum().desc())
                .limit(10)
                .fetch();
    }

}
