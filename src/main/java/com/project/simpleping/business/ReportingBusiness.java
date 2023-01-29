package com.project.simpleping.business;

import com.project.simpleping.model.HostStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportingBusiness {
  private static final Logger LOGGER = LoggerFactory.getLogger(ReportingBusiness.class);
  private static volatile ReportingBusiness instance = null;
  // private constructor
  private ReportingBusiness() {}

  public static ReportingBusiness getInstance() {
    if (instance == null) {
      synchronized (ReportingBusiness.class) {
        instance = new ReportingBusiness();
      }
    }
    return instance;
  }

  public void doBusiness(HostStatistics hostStatistics) {

    String statistics =
        "{\"host\":\""
            + hostStatistics.getHost().getHostName()
            + "\",\"icmp_ping\":\""
            + hostStatistics.getIcmpPingStats()
            + "\",\"tcp_ping\":\""
            + hostStatistics.getTcpIpPingStats()
            + "\",\"trace\":\""
            + hostStatistics.getTraceRouteStats()
            + "\"}";

    LOGGER.warn(statistics);

    //Post this message to a Reporting Service URL
  }
}
