/**
 * Copyright (C) 2011 Brian Ferris <bdferris@onebusaway.org> Copyright (C) 2011 Google, Inc.
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
package org.onebusaway.gtfs.serialization;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.onebusaway.csv_entities.schema.DefaultEntitySchemaFactory;
import org.onebusaway.csv_entities.schema.EntitySchemaFactoryHelper;
import org.onebusaway.csv_entities.schema.beans.CsvEntityMappingBean;
import org.onebusaway.gtfs.model.*;
import org.onebusaway.gtfs.serialization.comparators.*;

public class GtfsEntitySchemaFactory {

  private GtfsEntitySchemaFactory() {}

  public static List<Class<?>> getEntityClasses() {
    List<Class<?>> entityClasses = new ArrayList<>();
    entityClasses.add(FeedInfo.class);
    entityClasses.add(Agency.class);
    entityClasses.add(Area.class);
    entityClasses.add(Block.class);
    entityClasses.add(BookingRule.class);
    entityClasses.add(ShapePoint.class);
    entityClasses.add(Route.class);
    entityClasses.add(RouteNetworkAssignment.class);
    entityClasses.add(Level.class);
    entityClasses.add(Stop.class);
    entityClasses.add(StopAreaElement.class);
    entityClasses.add(Location.class);
    entityClasses.add(LocationGroup.class);
    entityClasses.add(LocationGroupElement.class);
    entityClasses.add(Trip.class);
    entityClasses.add(StopTime.class);
    entityClasses.add(ServiceCalendar.class);
    entityClasses.add(ServiceCalendarDate.class);
    entityClasses.add(RiderCategory.class);
    entityClasses.add(FareMedium.class);
    entityClasses.add(FareProduct.class);
    entityClasses.add(FareLegRule.class);
    entityClasses.add(FareAttribute.class);
    entityClasses.add(FareRule.class);
    entityClasses.add(FareTransferRule.class);
    entityClasses.add(Frequency.class);
    entityClasses.add(Pathway.class);
    entityClasses.add(Transfer.class);
    entityClasses.add(Ridership.class);
    entityClasses.add(Vehicle.class);
    entityClasses.add(Facility.class);
    entityClasses.add(FacilityPropertyDefinition.class);
    entityClasses.add(FacilityProperty.class);
    entityClasses.add(DirectionEntry.class);
    entityClasses.add(Icon.class);
    entityClasses.add(Network.class);
    entityClasses.add(Timeframe.class);
    entityClasses.add(Translation.class);
    return entityClasses;
  }

  public static Map<Class<?>, Comparator<?>> getEntityComparators() {
    Map<Class<?>, Comparator<?>> comparators = new HashMap<>();
    comparators.put(Agency.class, new IdentityBeanStringComparator());
    comparators.put(Area.class, new IdentityBeanAgencyAndIdComparator());
    comparators.put(Block.class, new IdentityBeanIntegerComparator());
    comparators.put(Route.class, new IdentityBeanAgencyAndIdComparator());
    comparators.put(Stop.class, new IdentityBeanAgencyAndIdComparator());
    comparators.put(Trip.class, new IdentityBeanAgencyAndIdComparator());
    comparators.put(StopTime.class, new StopTimeComparator());
    comparators.put(ShapePoint.class, new ShapePointComparator());
    comparators.put(ServiceCalendar.class, new ServiceCalendarComparator());
    comparators.put(ServiceCalendarDate.class, new ServiceCalendarDateComparator());
    comparators.put(Vehicle.class, new IdentityBeanAgencyAndIdComparator());
    comparators.put(Icon.class, new IdentityBeanAgencyAndIdComparator());
    return comparators;
  }

  public static DefaultEntitySchemaFactory createEntitySchemaFactory() {

    DefaultEntitySchemaFactory factory = new DefaultEntitySchemaFactory();
    EntitySchemaFactoryHelper helper = new EntitySchemaFactoryHelper(factory);

    CsvEntityMappingBean agencyId = helper.addEntity(AgencyAndId.class);
    helper.addIgnorableField(agencyId, "agencyId");

    return factory;
  }
}
