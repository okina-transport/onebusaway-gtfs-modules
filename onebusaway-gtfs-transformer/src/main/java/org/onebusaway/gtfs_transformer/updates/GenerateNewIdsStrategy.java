/**
 * Copyright (C) 2019 Grégoire Fora <gfora@okina.fr>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onebusaway.gtfs_transformer.updates;

import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;
import org.onebusaway.gtfs_transformer.services.GtfsTransformStrategy;
import org.onebusaway.gtfs_transformer.services.TransformContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Change trips and routes ids to avoid duplicate ids.
 * @author gfora
 *
 */

public class GenerateNewIdsStrategy implements GtfsTransformStrategy {

    private static Logger _log = LoggerFactory.getLogger(GenerateNewIdsStrategy.class);

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void run(TransformContext context, GtfsMutableRelationalDao dao) {
        int nbIdsUpdated = 0;
        String date = String.valueOf(System.currentTimeMillis());

        for(Route route: dao.getAllRoutes()){
            for(Trip trip: dao.getAllTrips()){
                if(trip.getRoute().getId().equals(route.getId())){
                    AgencyAndId agencyAndIdRoute = route.getId();
                    agencyAndIdRoute.setId(agencyAndIdRoute.getId() + "_" + date);

                    AgencyAndId agencyAndIdTrip = trip.getId();
                    agencyAndIdTrip.setId(agencyAndIdTrip.getId() + "_" + date);
                }
            }

            nbIdsUpdated++;
        }

        UpdateLibrary.clearDaoCache(dao);

        _log.info("updated " + nbIdsUpdated + " ids");
    }
}
