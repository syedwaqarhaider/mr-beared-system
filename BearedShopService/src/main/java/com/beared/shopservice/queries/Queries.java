package com.beared.shopservice.queries;

public  class Queries {
    public static final String geoGraphicalQuery="""
        SELECT * FROM shops
        WHERE status = 'O'
        AND earth_distance(
          ll_to_earth(:lat, :lon),
          ll_to_earth(latitude, longitude)
        ) <= :radius
        """;

    public static final String ratingQuery="SELECT * FROM Shops  WHERE avg_rating >= :minRating AND status = 'O'";

    public static final String nameAndAddressQuery="""
    SELECT * FROM Shops WHERE status = 'O'
    AND (
        LOWER(shop_name) LIKE LOWER(CONCAT('%', :query, '%'))
        OR LOWER(shop_address) LIKE LOWER(CONCAT('%', :query, '%'))
    )
""";


}
