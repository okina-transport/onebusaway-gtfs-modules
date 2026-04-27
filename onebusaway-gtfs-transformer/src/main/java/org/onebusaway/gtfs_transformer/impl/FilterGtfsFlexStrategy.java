package org.onebusaway.gtfs_transformer.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import org.onebusaway.gtfs.model.*;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;
import org.onebusaway.gtfs_transformer.services.GtfsTransformStrategy;
import org.onebusaway.gtfs_transformer.services.TransformContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterGtfsFlexStrategy implements GtfsTransformStrategy {

  private static final Logger LOGGER = LoggerFactory.getLogger(FilterGtfsFlexStrategy.class);
  private static final RemoveEntityLibrary REMOVE_ENTITY_LIBRARY = new RemoveEntityLibrary();

  public static final Predicate<StopTime> IS_FLEX =
      (stopTime -> {
        if (stopTime.getPickupBookingRule() != null || stopTime.getDropOffBookingRule() != null) {
          return true;
        }

        if (stopTime.getPickupType() == 2 || stopTime.getPickupType() == 3) {
          return true;
        }

        if (stopTime.getDropOffType() == 2 || stopTime.getDropOffType() == 3) {
          return true;
        }

        if (stopTime.getContinuousPickup() == 2 || stopTime.getContinuousPickup() == 3) {
          return true;
        }

        if (stopTime.getContinuousDropOff() == 2 || stopTime.getContinuousDropOff() == 3) {
          return true;
        }

        if (stopTime.getLocationGroup() != null) {
          return true;
        }

        return stopTime.getLocation() != null;
      });

  @Override
  public String getName() {
    return "FilterGtfsFlexStrategy";
  }

  @Override
  public void run(TransformContext context, GtfsMutableRelationalDao dao) {
    LOGGER.info("Starting FLEX filtering");

    Set<Trip> tripsToRemove = new HashSet<>();
    for (Trip trip : dao.getAllTrips()) {
      if (dao.getStopTimesForTrip(trip).stream().noneMatch(IS_FLEX)) {
        tripsToRemove.add(trip);
      }
    }

    for (Trip trip : tripsToRemove) {
      LOGGER.info("Removing trip id {}", trip.getId().getId());
      REMOVE_ENTITY_LIBRARY.removeTrip(dao, trip);
    }
  }
}
