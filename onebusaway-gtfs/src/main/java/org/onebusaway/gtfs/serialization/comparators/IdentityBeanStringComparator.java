package org.onebusaway.gtfs.serialization.comparators;

import java.util.Comparator;
import org.onebusaway.gtfs.model.IdentityBean;

public class IdentityBeanStringComparator implements Comparator<IdentityBean<String>> {
  @Override
  public int compare(IdentityBean<String> o1, IdentityBean<String> o2) {
    return o1.getId().compareTo(o2.getId());
  }
}
