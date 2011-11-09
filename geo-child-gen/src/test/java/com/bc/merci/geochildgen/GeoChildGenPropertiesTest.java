package com.bc.merci.geochildgen;

import com.bc.util.bean.BeanUtils;
import com.bc.util.sql.DataSourceConfig;
import junit.framework.TestCase;

import java.text.ParseException;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;

public class GeoChildGenPropertiesTest extends TestCase {

    public void testSetGetOriginatorId() {
        assertEquals(GeoChildGenProperties.CHILD_PRODUCT_ORIGINATOR_ID_DEFAULT,
                config.getChildProductOriginatorId());

        config.setChildProductOriginatorId(ID);
        assertEquals(ID, config.getChildProductOriginatorId());

        config.setChildProductOriginatorId(null);
        assertEquals(ID, config.getChildProductOriginatorId());
    }

    public void testSetGetGeometry() {
        assertNotNull(config.getGeometry());
        assertEquals(0, config.getGeometry().length);

        config.setGeometry(GEOMETRIES);
        assertNotNull(config.getGeometry());
        assertEquals(GEOMETRIES.length, config.getGeometry().length);

        for (int i = 0; i < GEOMETRIES.length; ++i) {
            assertEquals(GEOMETRIES[i], config.getGeometry()[i]);
        }

        config.setGeometry(null);
        assertNotNull(config.getGeometry());
        assertEquals(GEOMETRIES.length, config.getGeometry().length);

        for (int i = 0; i < GEOMETRIES.length; ++i) {
            assertEquals(GEOMETRIES[i], config.getGeometry()[i]);
        }
    }

    public void testSetPropertiesUsingBeanUtils() {
        final Properties properties = new Properties();

        properties.put(CHILD_PRODUCT_ORIGINATOR_ID_KEY, ID);

        for (int i = 0; i < GEOMETRIES.length; ++i) {
            properties.put(GEOMETRY_KEY_STUB + "[" + i + "]",
                    GEOMETRIES[i]);
        }

        try {
            BeanUtils.setBeanProperties(config, properties);
        } catch (ParseException e) {
            fail("Test failed due to unexpected ParseException");
        }

        assertNotNull(config.getChildProductOriginatorId());
        assertEquals(ID, config.getChildProductOriginatorId());

        assertNotNull(config.getGeometry());
        assertEquals(GEOMETRIES.length, config.getGeometry().length);

        for (int i = 0; i < GEOMETRIES.length; ++i) {
            assertEquals(GEOMETRIES[i], config.getGeometry()[i]);
        }
    }

    public void testSetGetSourceOption() {
        final String option_conf = "conf";
        final String option_db = "db";

        config.setSourceOption(option_conf);
        assertEquals(option_conf, config.getSourceOption());

        config.setSourceOption(option_db);
        assertEquals(option_db, config.getSourceOption());
    }

    public void testSeGetDataSourceConfig() {
        final DataSourceConfig dbConfig = new DataSourceConfig();

        config.setDataSourceConfig(dbConfig);
        assertEquals(dbConfig, config.getDataSourceConfig());
    }

    public void testReadFromProperties() throws ParseException {
        final Map<String,String> list = new HashMap<String, String>();

        final String originatorId = "MAP";
        final String geometry = "polygon((-22 -20, -20 -20,-20 -25, -22 -25, -22 -20))";
        final String driverName = "com.mysql.jdbc.Driver";
        final String url = "jdbc:mysql://localhost:3306/merci";
        final String userName = "merci";
        final String password = "theSecret";

        list.put("childProductOriginatorId", originatorId);
        list.put("geometry[0]", geometry);
        list.put("DataSourceConfig.driver", driverName);
        list.put("DataSourceConfig.url", url);
        list.put("DataSourceConfig.username", userName);
        list.put("DataSourceConfig.password", password);

        BeanUtils.setBeanProperties(config, list);

        assertEquals(originatorId, config.getChildProductOriginatorId());
        final String[] geometries = config.getGeometry();
        assertEquals(1, geometries.length);
        assertEquals(geometry, geometries[0]);

        final DataSourceConfig dsConfig = config.getDataSourceConfig();
        assertEquals(driverName, dsConfig.getDriver());
        assertEquals(url, dsConfig.getUrl());
        assertEquals(userName, dsConfig.getUsername());
        assertEquals(password, dsConfig.getPassword());
    }

    ///////////////////////////////////////////////////////////////////////////
    /////// END OF PUBLIC
    ///////////////////////////////////////////////////////////////////////////

    private static final String CHILD_PRODUCT_ORIGINATOR_ID_KEY = "childProductOriginatorId";
    private static final String GEOMETRY_KEY_STUB = "geometry";

    private static final String ID = "TST";
    private static final String[] GEOMETRIES = {"point", "rectangle", "polygon"};

    private GeoChildGenProperties config;

    @Override
    protected void setUp() throws Exception {
        config = new GeoChildGenProperties();
    }
}
