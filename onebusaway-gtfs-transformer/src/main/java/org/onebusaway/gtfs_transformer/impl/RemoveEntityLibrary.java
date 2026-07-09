/**
 * Copyright (C) 2011 Brian Ferris <bdferris@onebusaway.org> Copyright (C) 2012 Google, Inc.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onebusaway.gtfs_transformer.impl;

import java.util.Iterator;
import org.onebusaway.gtfs.model.*;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;

public class RemoveEntityLibrary {

  public void removeAgency(GtfsMutableRelationalDao dao, Agency agency) {
    for (Route route : dao.getRoutesForAgency(agency)) removeRoute(dao, route);
    dao.removeEntity(agency);
  }

  public void removeRoute(GtfsMutableRelationalDao dao, Route route) {
    for (Trip trip : dao.getTripsForRoute(route)) {
      removeTrip(dao, trip);
    }
    for (FareRule fareRule : dao.getFareRulesForRoute(route)) {
      dao.removeEntity(fareRule);
    }
    Iterator<Transfer> transferIterator = dao.getAllTransfers().iterator();
    while (transferIterator.hasNext()) {
      Transfer currentTransfer = transferIterator.next();
      if (currentTransfer.getFromRoute().getId().getId().equals(route.getId().getId())
          || currentTransfer.getToRoute().getId().getId().equals(route.getId().getId())) {
        transferIterator.remove();
      }
    }
    Iterator<RouteNetworkAssignment> routeNetworkIterator =
        dao.getAllRouteNetworkAssignments().iterator();
    while (routeNetworkIterator.hasNext()) {
      RouteNetworkAssignment currentRouteNetwork = routeNetworkIterator.next();
      if (currentRouteNetwork.getRoute().getId().getId().equals(route.getId().getId())) {
        routeNetworkIterator.remove();
      }
    }
    dao.removeEntity(route);
  }

  public void removeTrip(GtfsMutableRelationalDao dao, Trip trip) {
    for (StopTime stopTime : dao.getStopTimesForTrip(trip)) removeStopTime(dao, stopTime);
    for (Frequency frequency : dao.getFrequenciesForTrip(trip)) removeFrequency(dao, frequency);
    for (Transfer transfer : dao.getTransfersForTrip(trip)) removeTransfer(dao, transfer);
    dao.removeEntity(trip);
  }

  public void removeFrequency(GtfsMutableRelationalDao dao, Frequency frequency) {
    dao.removeEntity(frequency);
  }

  public void removeStop(GtfsMutableRelationalDao dao, Stop stop) {
    for (StopTime stopTime : dao.getStopTimesForStop(stop)) removeStopTime(dao, stopTime);
    dao.removeEntity(stop);
  }

  public void removeStopTime(GtfsMutableRelationalDao dao, StopTime stopTime) {
    dao.removeEntity(stopTime);
  }

  public void removeServiceCalendar(GtfsMutableRelationalDao dao, ServiceCalendar calendar) {
    dao.removeEntity(calendar);
  }

  public void removeServiceCalendarDate(
      GtfsMutableRelationalDao dao, ServiceCalendarDate calendarDate) {
    dao.removeEntity(calendarDate);
  }

  public void removeCalendar(GtfsMutableRelationalDao dao, AgencyAndId serviceId) {
    ServiceCalendar calendar = dao.getCalendarForServiceId(serviceId);
    if (calendar != null) {
      removeServiceCalendar(dao, calendar);
    }
    for (ServiceCalendarDate calendarDate : dao.getCalendarDatesForServiceId(serviceId)) {
      removeServiceCalendarDate(dao, calendarDate);
    }
    for (Trip trip : dao.getTripsForServiceId(serviceId)) {
      removeTrip(dao, trip);
    }
  }

  public void removeTransfer(GtfsMutableRelationalDao dao, Transfer transfer) {
    dao.removeEntity(transfer);
  }

  public void removeFeedInfo(GtfsMutableRelationalDao dao, FeedInfo feedInfo) {
    dao.removeEntity(feedInfo);
  }

  public void removeLocation(GtfsMutableRelationalDao dao, Location location) {
    dao.removeEntity(location);
  }

  public void removeLocationGroup(GtfsMutableRelationalDao dao, LocationGroup locationGroup) {
    dao.removeEntity(locationGroup);
  }

  public void removeBookingRule(GtfsMutableRelationalDao dao, BookingRule bookingRule) {
    dao.removeEntity(bookingRule);
  }

  public void removeLocationGroupElement(
      GtfsMutableRelationalDao dao, LocationGroupElement locationGroupElement) {
    dao.removeEntity(locationGroupElement);
  }
}
