package com.project.simpleping.business;

import com.project.simpleping.cache.HostStatisticsCache;
import com.project.simpleping.model.Host;
import com.project.simpleping.model.HostStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IcmpPingBusiness {
  private static final Logger LOGGER = LoggerFactory.getLogger(IcmpPingBusiness.class);
  private static volatile IcmpPingBusiness instance = null;
  // private constructor
  private IcmpPingBusiness() {}

  public static IcmpPingBusiness getInstance() {
    if (instance == null) {
      synchronized (IcmpPingBusiness.class) {
        instance = new IcmpPingBusiness();
      }
    }
    return instance;
  }

  public void doBusiness(Host host) {
    if (host.isIcmpPingActive()) {
      LOGGER.warn(
          String.format(
              "Ping is already active for host=[%s]. Hence, do nothing.", host.getHostName()));
    }
    String pingCommand = "ping -n 5 " + host.getHostName();
    String pingResult = "";
    try {
      Runtime r = Runtime.getRuntime();
      Process p = r.exec(pingCommand);
      host.setIcmpPingActive(true);
      BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        // System.out.println(inputLine);
        pingResult += inputLine;
      }
      in.close();

      if (pingResult.contains("100% loss")) {
        LOGGER.warn(String.format("Packet loss for host=[%s]", host.getHostName()));
      } else if (pingResult.contains("0% loss")) {
        LOGGER.info(String.format("ICMP Ping success for for host=[%s]", host.getHostName()));
      } else {
        LOGGER.warn(String.format("Partial packet loss for host=[%s]", host.getHostName()));
      }

      HostStatisticsCache cache = HostStatisticsCache.getInstance();
      if (cache.getCache().get(host) != null) {
        HostStatistics hs = cache.getCache().get(host);
        hs.setIcmpPingStats(pingResult);
      } else {
        HostStatistics hs = new HostStatistics(host);
        hs.setIcmpPingStats(pingResult);
        cache.getCache().put(host, hs);
      }
      host.setIcmpPingActive(false);
    } catch (IOException e) {
      System.out.println(e);
      host.setIcmpPingActive(false);
    }
  }
}
