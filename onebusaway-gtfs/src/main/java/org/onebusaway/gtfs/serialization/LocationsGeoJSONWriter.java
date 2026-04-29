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
package org.onebusaway.gtfs.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import org.apache.commons.collections4.CollectionUtils;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.onebusaway.gtfs.model.Location;

public class LocationsGeoJSONWriter {

  private static final ObjectWriter FEATURE_COLLECTION_OBJECT_WRITER =
      new ObjectMapper().writerFor(FeatureCollection.class).withDefaultPrettyPrinter();

  private final PrintWriter writer;

  public LocationsGeoJSONWriter(PrintWriter writer) {
    this.writer = writer;
  }

  public void write(Collection<Object> locations) throws IOException {
    FeatureCollection featureCollection = new FeatureCollection();
    if (CollectionUtils.isNotEmpty(locations)) {
      featureCollection.setFeatures(locations.stream().map(this::mapLocation).toList());
    }
    String data = FEATURE_COLLECTION_OBJECT_WRITER.writeValueAsString(featureCollection);
    writer.println(data);
  }

  private Feature mapLocation(Object object) {
    if (!(object instanceof Location location)) {
      throw new IllegalArgumentException("Location must be of type Location");
    }
    Feature feature = new Feature();
    feature.setId(location.getId().getId());
    feature.setGeometry(location.getGeometry());
    feature.getProperties().put("stop_name", location.getName());
    feature.getProperties().put("stop_desc", location.getDesc());
    return feature;
  }
}
