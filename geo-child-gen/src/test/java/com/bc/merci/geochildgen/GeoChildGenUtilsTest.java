package com.bc.merci.geochildgen;

import com.bc.util.geom.Geometry;
import com.bc.util.geom.GeometryParser;
import com.bc.util.geom.GeometryUtils;
import com.bc.util.geom.PointGeometry;
import com.bc.util.sql.BcDatabase;
import com.bc.util.sql.DataSourceConfig;
import com.bc.util.sql.SqlUtils;
import com.bc.util.sql.dto.Location;
import com.bc.util.sql.dto.Site;
import com.bc.util.sql.dto.SiteCategory;
import junit.framework.TestCase;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class GeoChildGenUtilsTest extends TestCase {

    public void testGetGeometriesFromDb() throws ParseException, SQLException {
        final List<Geometry> results = GeoChildGenUtils.getGeometriesFromDb(db, null);
        assertEquals(3, results.size());

        Geometry siteGeometry = getSiteGeometry(LOCATION_1);
        Geometry geometry = results.get(0);
        assertEquals(siteGeometry.getAsText(), geometry.getAsText());

        siteGeometry = getSiteGeometry(META_LOCATION);
        geometry = results.get(1);
        assertEquals(siteGeometry.getAsText(), geometry.getAsText());

        siteGeometry = getSiteGeometry(LOCATION_3);
        geometry = results.get(2);
        assertEquals(siteGeometry.getAsText(), geometry.getAsText());
    }

    public void testGetGeometriesFromDb_oneCategory() throws SQLException, ParseException {
        List<Geometry> results = GeoChildGenUtils.getGeometriesFromDb(db, new String[]{CATEGORY_1_NAME});
        assertEquals(2, results.size());

        Geometry siteGeometry = getSiteGeometry(LOCATION_1);
        Geometry geometry = results.get(0);
        assertEquals(siteGeometry.getAsText(), geometry.getAsText());

        siteGeometry = getSiteGeometry(LOCATION_3);
        geometry = results.get(1);
        assertEquals(siteGeometry.getAsText(), geometry.getAsText());

        results = GeoChildGenUtils.getGeometriesFromDb(db, new String[]{CATEGORY_2_NAME});
        assertEquals(1, results.size());

        siteGeometry = getSiteGeometry(META_LOCATION);
        geometry = results.get(0);
        assertEquals(siteGeometry.getAsText(), geometry.getAsText());
    }

    public void testGetGeometriesFromDb_twoCategories() throws SQLException, ParseException {
        List<Geometry> results = GeoChildGenUtils.getGeometriesFromDb(db, new String[]{CATEGORY_1_NAME, CATEGORY_2_NAME});
        assertEquals(3, results.size());

        Geometry siteGeometry = getSiteGeometry(LOCATION_1);
        Geometry geometry = results.get(0);
        assertEquals(siteGeometry.getAsText(), geometry.getAsText());

        siteGeometry = getSiteGeometry(LOCATION_3);
        geometry = results.get(1);
        assertEquals(siteGeometry.getAsText(), geometry.getAsText());

        siteGeometry = getSiteGeometry(META_LOCATION);
        geometry = results.get(2);
        assertEquals(siteGeometry.getAsText(), geometry.getAsText());
    }

    ////////////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ////////////////////////////////////////////////////////////////////////////////

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost/test";
    private static final String DATABASE_USER_NAME = "calval";
    private static final String DATABASE_PASSWORD = "lavlac";
    private static final String LOCATION_1 = "POINT(12 33)";
    private static final String LOCATION_2 = "POINT(16 38)";
    private static final String LOCATION_3 = "POINT(3 18)";
    private static final String LOCATION_4 = "POINT(9 108)";
    private static final String META_LOCATION = "POINT(22 14)";
    private static final String RADIUS = "155.7";
    private static final String CATEGORY_1_NAME = "category 1";
    private static final String CATEGORY_2_NAME = "category 2";

    private static final String SQL_CREATE_SCS = "CREATE TABLE SiteCategorySite (\n" +
            " SiteId           VARCHAR(32)    NOT NULL,\n" +
            " SiteCategoryId   VARCHAR(32)    NOT NULL,\n" +
            "\n" +
            " UNIQUE (SiteId, SiteCategoryId),\n" +
            " FOREIGN KEY (SiteId)          REFERENCES Site (Id) ON DELETE CASCADE,\n" +
            " FOREIGN KEY (SiteCategoryId)  REFERENCES SiteCategory (Id) ON DELETE CASCADE\n" +
            ");";
    private static final String SQL_DROP_SCS = "DROP TABLE IF EXISTS SiteCategorySite;";
    private static final String SQL = "INSERT INTO SiteCategory VALUES('510', '" + CATEGORY_1_NAME + "', null);\n" +
            "INSERT INTO SiteCategory VALUES('511', '" + CATEGORY_2_NAME + "', null);\n" +
            "INSERT INTO Location VALUES(1004, GeomFromText('" + LOCATION_1 + "'));\n" +
            "INSERT INTO Location VALUES(1005, GeomFromText('" + LOCATION_2 + "'));\n" +
            "INSERT INTO Location VALUES(1006, GeomFromText('" + LOCATION_3 + "'));\n" +
            "INSERT INTO Location VALUES(1007, GeomFromText('" + META_LOCATION + "'));\n" +
            "INSERT INTO Location VALUES(1008, GeomFromText('" + LOCATION_4 + "'));\n" +
            "INSERT INTO Site VALUES(99, 'site_wo_meta', 'a test site without a meta site', 1004, " + RADIUS + ", GeomFromText('" + LOCATION_1 + "'), 234, true, 1, 99, null, TRUE, FALSE);\n" +
            "INSERT INTO Site VALUES(100, 'site_w_meta', 'a test site with a meta site', 1005, " + RADIUS + ", GeomFromText('" + LOCATION_2 + "'), 251, true, 1, 101, null, TRUE, FALSE);\n" +
            "INSERT INTO Site VALUES(101, 'meta_site', 'a test meta site', 1007, " + RADIUS + ", GeomFromText('" + META_LOCATION + "'), 99, true, 1, 101, null, TRUE, TRUE);\n" +
            "INSERT INTO Site VALUES(102, 'cat_site', 'a categorized site', 1006, " + RADIUS + ", GeomFromText('" + LOCATION_3 + "'), 99, true, 1, 102, null, TRUE, FALSE);\n" +
            "INSERT INTO Site VALUES(103, 'non_systematic_site', 'a non_systematic site', 1008, " + RADIUS + ", GeomFromText('" + LOCATION_4 + "'), 99, true, 1, 103, null, FALSE, FALSE);\n" +
            "INSERT INTO SiteCategorySite VALUES(99, 510);\n" +
            "INSERT INTO SiteCategorySite VALUES(102, 510);\n" +
            "INSERT INTO SiteCategorySite VALUES(100, 511);";

    private BcDatabase db;
    private GeometryParser parser;

    protected void setUp() throws Exception {
        final DataSourceConfig dataSourceConfig = new DataSourceConfig(JDBC_DRIVER, DATABASE_URL, DATABASE_USER_NAME, DATABASE_PASSWORD);
        final BasicDataSource datasource = SqlUtils.createDatasource(dataSourceConfig);
        db = new BcDatabase(datasource);

        try {
            db.runScriptFromText(Location.SQL_CREATE_TABLE, null);
            db.runScriptFromText(Site.SQL_CREATE_TABLE, null);
            db.runScriptFromText(SiteCategory.SQL_CREATE_TABLE, null);
            db.runScriptFromText(SQL_CREATE_SCS, null);
            db.runScriptFromText(SQL, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        parser = new GeometryParser();
    }

    protected void tearDown() throws Exception {
        db.runScriptFromText(Site.SQL_DROP_TABLE, null);
        db.runScriptFromText(Location.SQL_DROP_TABLE, null);
        db.runScriptFromText(SiteCategory.SQL_DROP_TABLE, null);
        db.runScriptFromText(SQL_DROP_SCS, null);
    }

    private Geometry getSiteGeometry(String locationWKT) throws ParseException {
        PointGeometry pointGeometry = (PointGeometry) parser.parseWKT(locationWKT);
        return GeometryUtils.createPointRadiusGeometry(pointGeometry.getY(), pointGeometry.getX(), Double.parseDouble(RADIUS));
    }
}
