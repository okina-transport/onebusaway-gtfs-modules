package org.onebusaway.gtfs_transformer.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;

class NonStandardStopTransformerStrategyTest {

  private static final String DEFAULT_NAME = "DEFAULT_NAME";
  private static final double DEFAULT_LATITUDE = 43.481402;
  private static final double DEFAULT_LONGITUDE = -1.514699;
  private static final double DELTA = 0.001;

  private final NonStandardStopTransformerStrategy tested =
      new NonStandardStopTransformerStrategy(DEFAULT_NAME, DEFAULT_LATITUDE, DEFAULT_LONGITUDE);

  @Test
  void shouldSetDefaultValueForStopIfNotSetTest() {
    Stop stop = new Stop();

    tested.handleEntity(stop);

    assertEquals(DEFAULT_NAME, stop.getName());
    assertEquals(DEFAULT_LATITUDE, stop.getLat(), DELTA);
    assertEquals(DEFAULT_LONGITUDE, stop.getLon(), DELTA);
  }

  @Test
  void shouldNotSetDefaultValueForStopIfValueExistsTest() {
    Stop stop = new Stop();
    stop.setName("NAME");
    stop.setLat(48);
    stop.setLon(-1);

    tested.handleEntity(stop);

    assertNotEquals(DEFAULT_NAME, stop.getName());
    assertNotEquals(DEFAULT_LATITUDE, stop.getLat(), DELTA);
    assertNotEquals(DEFAULT_LONGITUDE, stop.getLon(), DELTA);
  }

  @Test
  void shouldNotMakeAnyTransformationForOtherTypesTest() {
    Route route = new Route();

    tested.handleEntity(route);

    assertNull(route.getShortName());
    assertNull(route.getLongName());
  }
}
