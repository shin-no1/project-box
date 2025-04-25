package io.github.haeun.petstats.domain.filter;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.haeun.petstats.domain.region.QRegion;
import io.github.haeun.petstats.web.dto.RegionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class FilterQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<RegionResponse> getRegions() {
        QRegion region = QRegion.region;
        return queryFactory.select(
                Projections.constructor(RegionResponse.class,
                        region.id,
                        region.province))
                .from(region)
                .fetch();
    }

}
