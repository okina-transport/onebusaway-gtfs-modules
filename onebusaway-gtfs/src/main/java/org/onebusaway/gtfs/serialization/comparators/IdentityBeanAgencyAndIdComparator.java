package org.onebusaway.gtfs.serialization.comparators;

import java.util.Comparator;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.IdentityBean;

public class IdentityBeanAgencyAndIdComparator implements Comparator<IdentityBean<AgencyAndId>> {
  @Override
  public int compare(IdentityBean<AgencyAndId> o1, IdentityBean<AgencyAndId> o2) {
    return o1.getId().compareTo(o2.getId());
  }
}
