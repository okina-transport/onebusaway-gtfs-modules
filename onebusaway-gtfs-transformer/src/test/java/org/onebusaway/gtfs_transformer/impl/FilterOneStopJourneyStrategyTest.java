package org.onebusaway.gtfs_transformer.impl;

import org.junit.jupiter.api.Test;
import org.onebusaway.gtfs.impl.GtfsRelationalDaoImpl;
import org.onebusaway.gtfs.model.*;
import org.onebusaway.gtfs_transformer.services.TransformContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterOneStopJourneyStrategyTest {

    private final FilterOneStopJourneyStrategy tested = new FilterOneStopJourneyStrategy();

    @Test
    void runFilterOneStopJourneyTest() {
        GtfsRelationalDaoImpl dao = new GtfsRelationalDaoImpl();
        TransformContext context = new TransformContext();

        Trip emptyTrip = new Trip();
        AgencyAndId id = new AgencyAndId();
        id.setId("empty");
        emptyTrip.setId(id);
        dao.saveEntity(emptyTrip);

        Trip oneStopTrip = new Trip();
        AgencyAndId idOneStop = new AgencyAndId();
        idOneStop.setId("oneStop");
        oneStopTrip.setId(idOneStop);
        dao.saveEntity(oneStopTrip);

        Trip validTrip = new Trip();
        AgencyAndId validTripId = new AgencyAndId();
        validTripId.setId("valid");
        validTrip.setId(validTripId);
        dao.saveEntity(validTrip);

        StopTime stopTimeToRemove = new StopTime();
        stopTimeToRemove.setTrip(oneStopTrip);
        dao.saveEntity(stopTimeToRemove);

        StopTime stopTimeToKeep = new StopTime();
        stopTimeToKeep.setTrip(validTrip);
        dao.saveEntity(stopTimeToKeep);

        StopTime stopTimeToKeep2 = new StopTime();
        stopTimeToKeep2.setTrip(validTrip);
        dao.saveEntity(stopTimeToKeep2);

        Frequency frequencyToRemove = new Frequency();
        frequencyToRemove.setTrip(emptyTrip);
        dao.saveEntity(frequencyToRemove);

        Frequency frequencyToRemove2 = new Frequency();
        frequencyToRemove2.setTrip(oneStopTrip);
        dao.saveEntity(frequencyToRemove2);

        Frequency frequencyToKeep = new Frequency();
        frequencyToKeep.setTrip(validTrip);
        dao.saveEntity(frequencyToKeep);

        Transfer transferToRemove = new Transfer();
        transferToRemove.setFromTrip(emptyTrip);
        transferToRemove.setToTrip(validTrip);
        dao.saveEntity(transferToRemove);

        Transfer transferToRemove2 = new Transfer();
        transferToRemove2.setToTrip(oneStopTrip);
        transferToRemove2.setFromTrip(validTrip);
        dao.saveEntity(transferToRemove2);

        Transfer transferToKeep = new Transfer();
        transferToKeep.setToTrip(validTrip);
        transferToKeep.setFromTrip(validTrip);
        dao.saveEntity(transferToKeep);

        tested.run(context, dao);

        assertEquals(1, dao.getAllTrips().size());
        assertTrue(dao.getAllTrips().contains(validTrip));

        assertEquals(2, dao.getAllStopTimes().size());
        assertTrue(dao.getAllStopTimes().contains(stopTimeToKeep));
        assertTrue(dao.getAllStopTimes().contains(stopTimeToKeep2));

        assertEquals(1, dao.getAllFrequencies().size());
        assertTrue(dao.getAllFrequencies().contains(frequencyToKeep));

        assertEquals(1, dao.getAllTransfers().size());
        assertTrue(dao.getAllTransfers().contains(transferToKeep));
    }

}