package org.onebusaway.gtfs_transformer.impl;

import static org.onebusaway.gtfs_transformer.impl.FilterGtfsFlexStrategy.IS_FLEX;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;
import org.onebusaway.gtfs_transformer.services.GtfsTransformStrategy;
import org.onebusaway.gtfs_transformer.services.TransformContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoveFlexStrategy implements GtfsTransformStrategy {

  private static final Logger LOGGER = LoggerFactory.getLogger(RemoveFlexStrategy.class);
  private static final RemoveEntityLibrary REMOVE_ENTITY_LIBRARY = new RemoveEntityLibrary();

  @Override
  public String getName() {
    return "RemoveFlexStrategy";
  }

  @Override
  public void run(TransformContext context, GtfsMutableRelationalDao dao) {

    Set<Trip> tripsToRemove = new HashSet<>();
    for (Trip trip : dao.getAllTrips()) {
      if (dao.getStopTimesForTrip(trip).stream().anyMatch(IS_FLEX)) {
        tripsToRemove.add(trip);
      }
    }

    for (Trip trip : tripsToRemove) {
      LOGGER.info("Removing trip id {}", trip.getId().getId());
      REMOVE_ENTITY_LIBRARY.removeTrip(dao, trip);
    }

    var routes = new ArrayList<>(dao.getAllRoutes());
    for (var route : routes) {
      var trips = dao.getTripsForRoute(route);
      if (tripsToRemove.containsAll(trips)) {
        LOGGER.info("Removing route id {}", route.getId().getId());
        REMOVE_ENTITY_LIBRARY.removeRoute(dao, route);
      }
    }

    var locationGroupElements = new ArrayList<>(dao.getAllLocationGroupElements());
    for (var locationGroupElement : locationGroupElements) {
      LOGGER.info("Removing location group element id {}", locationGroupElement.getId());
      REMOVE_ENTITY_LIBRARY.removeLocationGroupElement(dao, locationGroupElement);
    }

    var locations = new ArrayList<>(dao.getAllLocations());
    for (var location : locations) {
      LOGGER.info("Removing location id {}", location.getId().getId());
      REMOVE_ENTITY_LIBRARY.removeLocation(dao, location);
    }

    var locationGroups = new ArrayList<>(dao.getAllLocationGroups());
    for (var locationGroup : locationGroups) {
      LOGGER.info("Removing location group id {}", locationGroup.getId().getId());
      REMOVE_ENTITY_LIBRARY.removeLocationGroup(dao, locationGroup);
    }

    var bookingRules = new ArrayList<>(dao.getAllBookingRules());
    for (var bookingRule : bookingRules) {
      LOGGER.info("Removing booking rule id {}", bookingRule.getId().getId());
      REMOVE_ENTITY_LIBRARY.removeBookingRule(dao, bookingRule);
    }
  }
}
