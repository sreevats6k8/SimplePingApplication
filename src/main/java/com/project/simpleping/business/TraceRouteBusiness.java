package com.project.simpleping.business;

import com.project.simpleping.cache.HostStatisticsCache;
import com.project.simpleping.model.Host;
import com.project.simpleping.model.HostStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TraceRouteBusiness {
  private static final Logger LOGGER = LoggerFactory.getLogger(TraceRouteBusiness.class);
  private static volatile TraceRouteBusiness instance = null;
  // private constructor
  private TraceRouteBusiness() {}

  public static TraceRouteBusiness getInstance() {
    if (instance == null) {
      synchronized (TraceRouteBusiness.class) {
        instance = new TraceRouteBusiness();
      }
    }
    return instance;
  }

  public void doBusiness(Host host) {
    if (host.isTracertActive()) {
      LOGGER.warn(
          String.format(
              "Tracert is already active for host=[%s]. Hence, do nothing.", host.getHostName()));
    }

    String traceRtCommand = "tracert " + host.getHostName();
    LOGGER.info(traceRtCommand + " executed by thread" + Thread.currentThread().getName());
    String traceRtResult = "";
    try {
      Runtime r = Runtime.getRuntime();
      Process p = r.exec(traceRtCommand);
      host.setTracertActive(true);
      BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        /* if (host.getHostName().equals("oranum.com")) {
          System.out.println("oranum.com status tracert:: " + inputLine);
        }*/

        // System.out.println(inputLine);
        traceRtResult += inputLine;
      }
      in.close();

      HostStatisticsCache cache = HostStatisticsCache.getInstance();
      if (cache.getCache().get(host) != null) {
        HostStatistics hs = cache.getCache().get(host);
        hs.setTraceRouteStats(traceRtResult);
      } else {
        HostStatistics hs = new HostStatistics(host);
        hs.setTraceRouteStats(traceRtResult);
        cache.getCache().put(host, hs);
      }

      if (traceRtResult.contains("Trace complete")) {
        LOGGER.info(String.format("Tracert success for host=[%s]", host.getHostName()));
      } else {
        ReportingBusiness.getInstance().doBusiness(cache.getCache().get(host));
      }
      host.setTracertActive(false);

    } catch (IOException e) {
      System.out.println(e);
      host.setTracertActive(false);
    }
  }
}
