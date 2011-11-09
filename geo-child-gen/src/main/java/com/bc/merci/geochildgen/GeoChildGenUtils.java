package com.bc.merci.geochildgen;

import com.bc.util.geom.Geometry;
import com.bc.util.geom.GeometryUtils;
import com.bc.util.geom.PointGeometry;
import com.bc.util.sql.BcDatabase;
import com.bc.util.sql.QueryForListTransaction;
import com.bc.util.sql.QueryForObjectTransaction;
import com.bc.util.sql.dto.Location;
import com.bc.util.sql.dto.SiteCategory;
import com.bc.util.sql.dto.SiteContainer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class GeoChildGenUtils {

    static List<Geometry> getGeometriesFromDb(BcDatabase db, String[] categoryNames) throws SQLException {
        final List<Geometry> result = new ArrayList<Geometry>();

        List list;
        if ((categoryNames == null) || (categoryNames.length == 0)) {
            list = getAllSites(db);
        } else {
            list = getAllSitesForCategories(categoryNames, db);
        }

        final Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            SiteContainer site = (SiteContainer) iterator.next();
            final String metaSiteId = site.getMetaSiteId();
            if (!site.getId().equals(metaSiteId)) {
                site = getSiteById(metaSiteId, db);
            }

            final Location location = getLocationById(site.getLocationId(), db);
            if (location != null) {
                final double radius = site.getRadius();
                addGeometryToResult(location, radius, result);
            }
        }
        return result;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private static final String SQL_SELECT_ALL_SITES = "SELECT " +
            "  locationId as locationId, " +
            "  radius as radius, " +
            "  id as id, " +
            "  metaSiteId as metaSiteId " +
            "FROM " +
            "  Site " +
            "WHERE " +
            "  IsSystematicDownload = true";

    private static final String SQL_SELECT_SITE_CATEGORY = "SELECT " +
            "  id as id " +
            "FROM " +
            "  SiteCategory " +
            "WHERE " +
            "  name = ${value}";

    private static final String SQL_SELECT_SITES_BY_CAT_ID = "SELECT " +
            "  Site.locationId as locationId, " +
            "  Site.radius as radius, " +
            "  Site.id as id, " +
            "  Site.metaSiteId as metaSiteId " +
            "FROM " +
            "  Site, " +
            "  SiteCategory, " +
            "  SiteCategorySite " +
            "WHERE " +
            "  SiteCategorySite.SiteId = Site.Id " +
            "AND " +
            "  SiteCategorySite.SiteCategoryId = ${value} " +
            "AND " +
            "  Site.IsSystematicDownload = true";

    private static final String SQL_SELECT_SITE_BY_ID = "SELECT " +
            "  locationId as locationId, " +
            "  radius as radius " +
            "FROM " +
            "  Site " +
            "WHERE " +
            "  Id = ${value} " +
            "AND " +
            "  IsSystematicDownload = true";

    private static final String SELECT_LOCATION = "SELECT " +
            "  id as id, " +
            "  AsText(Geometry) as geometry " +
            "FROM " +
            "  Location " +
            "WHERE " +
            "  Id = ${value}";

    private static List getAllSites(BcDatabase db) throws SQLException {
        final QueryForListTransaction ts = new QueryForListTransaction(SQL_SELECT_ALL_SITES, SiteContainer.class, null);
        db.execute(ts);
        return ts.fetchResultList();
    }

    private static List getAllSitesForCategories(String[] categoryNames, BcDatabase db) throws SQLException {
        final List list = new ArrayList<Geometry>();
        for (int i = 0; i < categoryNames.length; i++) {
            final QueryForObjectTransaction tc = new QueryForObjectTransaction(SQL_SELECT_SITE_CATEGORY, SiteCategory.class, categoryNames[i]);
            db.execute(tc);
            final SiteCategory category = (SiteCategory) tc.fetchResultObject();

            if (category != null) {
                final QueryForListTransaction ts = new QueryForListTransaction(SQL_SELECT_SITES_BY_CAT_ID, SiteContainer.class, category.getId());
                db.execute(ts);
                List sitesForCategory = ts.fetchResultList();
                //noinspection unchecked
                list.addAll(sitesForCategory);
            }
        }
        return list;
    }

    private static SiteContainer getSiteById(String metaSiteId, BcDatabase db) throws SQLException {
        final QueryForObjectTransaction tm = new QueryForObjectTransaction(SQL_SELECT_SITE_BY_ID, SiteContainer.class, metaSiteId);
        db.execute(tm);
        return (SiteContainer) tm.fetchResultObject();
    }

    private static Location getLocationById(String locationId, BcDatabase db) throws SQLException {
        final QueryForObjectTransaction tl = new QueryForObjectTransaction(SELECT_LOCATION, Location.class, locationId);
        db.execute(tl);
        return (Location) tl.fetchResultObject();
    }

    private static void addGeometryToResult(Location location, double radius, List<Geometry> result) {
        final Geometry geometry = location.getGeometry();
        if (geometry instanceof PointGeometry) {
            final PointGeometry point = (PointGeometry) geometry;
            final Geometry polygonGeometry = GeometryUtils.createPointRadiusGeometry(point.getY(), point.getX(), radius);
            addUniqueToResult(result, polygonGeometry);
        } else {
            addUniqueToResult(result, geometry);
        }
    }

    private static void addUniqueToResult(List<Geometry> result, Geometry geometry) {
        if (!result.contains(geometry)) {
            result.add(geometry);
        }
    }
}
