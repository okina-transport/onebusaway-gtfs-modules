package org.onebusaway.gtfs.serialization.comparators;

import java.util.Comparator;
import org.onebusaway.gtfs.model.IdentityBean;

public class IdentityBeanIntegerComparator implements Comparator<IdentityBean<Integer>> {
  @Override
  public int compare(IdentityBean<Integer> o1, IdentityBean<Integer> o2) {
    return o1.getId().compareTo(o2.getId());
  }
}
