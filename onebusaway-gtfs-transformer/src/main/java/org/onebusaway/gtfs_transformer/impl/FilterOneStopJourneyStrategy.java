package org.onebusaway.gtfs_transformer.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;
import org.onebusaway.gtfs_transformer.services.GtfsTransformStrategy;
import org.onebusaway.gtfs_transformer.services.TransformContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterOneStopJourneyStrategy implements GtfsTransformStrategy {

  private static final Logger LOGGER = LoggerFactory.getLogger(FilterOneStopJourneyStrategy.class);
  private static final RemoveEntityLibrary REMOVE_ENTITY_LIBRARY = new RemoveEntityLibrary();

  @Override
  public String getName() {
    return "FilterOneStopJourneyStrategy";
  }

  @Override
  public void run(TransformContext transformContext, GtfsMutableRelationalDao dao) {
    LOGGER.info("Start filtering one-stop vehicle journeys");
    Collection<Trip> allTrips = dao.getAllTrips();
    Set<Trip> tripToRemove = new HashSet<>();
    if (CollectionUtils.isNotEmpty(allTrips)) {
      for (Trip trip : allTrips) {
        List<StopTime> stopTimesForTrip = dao.getStopTimesForTrip(trip);
        if (CollectionUtils.isEmpty(stopTimesForTrip) || stopTimesForTrip.size() == 1) {
          tripToRemove.add(trip);
        }
      }
    }

    for (Trip trip : tripToRemove) {
      LOGGER.info("Removing trip id {}", trip.getId().getId());
      REMOVE_ENTITY_LIBRARY.removeTrip(dao, trip);
    }

    LOGGER.info("End one-stop vehicle journeys filtering");
  }
}
