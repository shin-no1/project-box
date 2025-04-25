package io.github.haeun.petstats.service;

import io.github.haeun.petstats.domain.filter.FilterQueryRepository;
import io.github.haeun.petstats.web.dto.RegionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FilterService {
    private final FilterQueryRepository filterQueryRepository;

    public List<RegionResponse> getRegions() {
        return filterQueryRepository.getRegions();
    }
}
