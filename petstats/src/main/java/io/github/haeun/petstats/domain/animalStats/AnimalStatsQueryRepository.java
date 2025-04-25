package io.github.haeun.petstats.domain.animalStats;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.haeun.petstats.domain.animalType.QAnimalType;
import io.github.haeun.petstats.domain.rfidType.QRfidType;
import io.github.haeun.petstats.domain.species.QSpecies;
import io.github.haeun.petstats.web.dto.RegionTopAnimalTypeResponse;
import io.github.haeun.petstats.web.dto.TopRfidTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

    public List<TopRfidTypeResponse> getTopRfidType(Integer birthYear, Integer rfidTypeId) {
        QAnimalStats stats = QAnimalStats.animalStats;
        QAnimalType type = QAnimalType.animalType;
        QRfidType rfidType = QRfidType.rfidType;

        return queryFactory
                .select(Projections.constructor(
                        TopRfidTypeResponse.class,
                        rfidType.name,
                        type.name,
                        stats.animalCount.sum()
                ))
                .from(stats)
                .join(stats.animalType, type)
                .join(stats.rfidType, rfidType)
                .where(
                        birthYear != null ? stats.birthYear.eq(birthYear) : null,
                        rfidType.id.eq(rfidTypeId)
                )
                .groupBy(stats.animalType.id)
                .orderBy(stats.animalCount.sum().desc())
                .limit(5)
                .fetch();
    }

    public List<Integer> getBirthYears() {
        QAnimalStats stats = QAnimalStats.animalStats;
        return queryFactory
                .select(Projections.constructor(
                        Integer.class,
                        stats.birthYear
                ))
                .from(stats)
                .groupBy(stats.birthYear)
                .orderBy(stats.birthYear.desc())
                .fetch();
    }

}
