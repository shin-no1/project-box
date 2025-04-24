package io.github.haeun.petstats.domain.animal_stat;


import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalStatsRepository extends JpaRepository<AnimalStats, Integer> {
}
