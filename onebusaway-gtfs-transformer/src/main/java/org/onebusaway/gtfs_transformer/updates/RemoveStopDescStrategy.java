/**
 * Copyright (C) 2018 Grégoire Fora <gfora@okina.fr>
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

import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;
import org.onebusaway.gtfs_transformer.services.GtfsTransformStrategy;
import org.onebusaway.gtfs_transformer.services.TransformContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Remove stopDesc when stopDesc value = stopName value.
 * @author gfora
 *
 */

public class RemoveStopDescStrategy implements GtfsTransformStrategy {
    private static Logger _log = LoggerFactory.getLogger(RemoveStopDescStrategy.class);

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void run(TransformContext context, GtfsMutableRelationalDao dao) {
        int nbStopDescClear = 0;

        for(Stop stop: dao.getAllStops()){
            if(stop.getName().equals(stop.getDesc())){
                stop.setDesc("");
                nbStopDescClear++;
            }
        }

        UpdateLibrary.clearDaoCache(dao);

        _log.info("cleaned " + nbStopDescClear + " stop desc");
    }
}
