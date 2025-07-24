package com.beared.shopservice.repository;

import com.beared.shopservice.queries.Queries;
import com.beared.shopservice.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    @Query(value = Queries.geoGraphicalQuery,
            nativeQuery = true)
    List<Shop> findNearbyShops(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("radius") double radiusMeters);

    @Query(value = Queries.ratingQuery, nativeQuery = true)
    List<Shop> findByMinAvgRating(@Param("minRating") Double minRating);

    @Query(value = Queries.nameAndAddressQuery, nativeQuery = true)
    List<Shop> searchByNameOrAddress(@Param("query") String query);

}
