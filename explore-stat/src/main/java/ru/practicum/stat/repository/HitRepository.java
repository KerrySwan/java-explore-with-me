package ru.practicum.stat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.stat.commons.dto.ViewStatsDto;
import ru.practicum.stat.commons.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HitRepository extends JpaRepository<EndpointHit, Long>, JpaSpecificationExecutor<EndpointHit> {

    @Query("SELECT " +
            "new ru.practicum.stat.commons.dto.ViewStatsDto(s.app, s.uri, COUNT(s.ip)) " +
            "FROM EndpointHit AS s " +
            "WHERE s.timestamp > :start " +
            "and s.timestamp < :end " +
            "GROUP BY s.app, s.uri")
    List<ViewStatsDto> countTotalIp(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    @Query("SELECT " +
            "new ru.practicum.stat.commons.dto.ViewStatsDto(s.app, s.uri, COUNT(DISTINCT s.ip)) " +
            "FROM EndpointHit AS s " +
            "WHERE s.timestamp > :start " +
            "and s.timestamp < :end " +
            "GROUP BY s.app, s.uri")
    List<ViewStatsDto> countTotalIpDistinct(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

}
