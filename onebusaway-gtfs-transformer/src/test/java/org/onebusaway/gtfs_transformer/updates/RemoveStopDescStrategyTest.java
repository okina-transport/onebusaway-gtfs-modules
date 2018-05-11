package org.onebusaway.gtfs_transformer.updates;

import org.junit.Before;
import org.junit.Test;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;
import org.onebusaway.gtfs.services.MockGtfs;
import org.onebusaway.gtfs_transformer.services.TransformContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RemoveStopDescStrategyTest {

    private MockGtfs _gtfs;

    @Before
    public void before() throws IOException {
        _gtfs = MockGtfs.create();
    }
    @Test
    public void test()  throws IOException {
        RemoveStopDescStrategy _strategy = new RemoveStopDescStrategy();
        _gtfs.putAgencies(1);
        _gtfs.putStopsWithStopDesc();
        _gtfs.putRoutes(1);
        _gtfs.putCalendars(1, "start_date=20120903", "end_date=20121016",
                "mask=1111100");
        _gtfs.putTrips(1, "r0", "sid0");
        _gtfs.putLines("stop_times.txt",
                "trip_id,stop_id,stop_sequence,arrival_time,departure_time,drop_off_type,pickup_type",
                "t0,s3,0,01:00:00,01:05:00,1,1",
                "t0,s1,1,01:30:00,01:30:00,0,0",
                "t0,s2,2,02:30:00,02:30:00,1,1",
                "t0,s3,3,03:00:00,03:00:00,0,0",
                "t0,s2,4,03:30:00,03:30:00,1,1");

        GtfsMutableRelationalDao dao = _gtfs.read();
        _strategy.run(new TransformContext(), dao);

        List<Stop> stops = new ArrayList<>(dao.getAllStops());
        assertEquals("", stops.get(0).getDesc());
        assertEquals("", stops.get(1).getDesc());
        assertEquals("Desc3", stops.get(2).getDesc());

    }

}