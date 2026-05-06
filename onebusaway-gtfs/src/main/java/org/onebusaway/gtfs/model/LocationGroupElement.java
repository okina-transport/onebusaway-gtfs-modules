/**
 * Copyright (C) 2020 Kyyti Group Ltd
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
package org.onebusaway.gtfs.model;

import java.io.Serial;
import org.onebusaway.csv_entities.schema.annotations.CsvField;
import org.onebusaway.csv_entities.schema.annotations.CsvFields;
import org.onebusaway.gtfs.serialization.mappings.EntityFieldMappingFactory;
import org.onebusaway.gtfs.serialization.mappings.StopLocationFieldMappingFactory;

@CsvFields(filename = "location_group_stops.txt", required = false)
public class LocationGroupElement extends IdentityBean<AgencyAndId> {

  @Serial private static final long serialVersionUID = 1L;

  @CsvField(name = "location_group_id", mapping = EntityFieldMappingFactory.class)
  private LocationGroup locationGroup;

  @CsvField(name = "stop_id", mapping = StopLocationFieldMappingFactory.class)
  private StopLocation stop;

  @Override
  public AgencyAndId getId() {
    return new AgencyAndId(
        locationGroup.getId().getAgencyId(),
        String.format("%s_%s", locationGroup.getId().getId(), stop.getId().getId()));
  }

  @Override
  public void setId(AgencyAndId id) {
    // id is generated dynamically
  }

  public LocationGroup getLocationGroup() {
    return locationGroup;
  }

  public void setLocationGroup(LocationGroup locationGroup) {
    this.locationGroup = locationGroup;
  }

  public StopLocation getStop() {
    return stop;
  }

  public void setStop(StopLocation stop) {
    this.stop = stop;
  }
}
