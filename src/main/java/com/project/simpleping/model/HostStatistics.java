package com.project.simpleping.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HostStatistics {
  private Host host;
  private String icmpPingStats;
  private String tcpIpPingStats;
  private String traceRouteStats;

  public HostStatistics(Host host) {
    this.host = host;
  }

  @Override
  public String toString() {
    return "1. ICMP PING STATUS=["
        + icmpPingStats
        + "]\r\n"
        + "2. TCP IP PING STATUS=["
        + tcpIpPingStats
        + "]\r\n"
        + "3. TRACERT STATUS=["
        + traceRouteStats
        + "]";
  }

  public Host getHost() {
    return host;
  }

  public void setHost(Host host) {
    this.host = host;
  }

  public String getIcmpPingStats() {
    return icmpPingStats;
  }

  public void setIcmpPingStats(String icmpPingStats) {
    this.icmpPingStats =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))
            + " ==>"
            + icmpPingStats;
  }

  public String getTcpIpPingStats() {
    return tcpIpPingStats;
  }

  public void setTcpIpPingStats(String tcpIpPingStats) {
    this.tcpIpPingStats = tcpIpPingStats;
  }

  public String getTraceRouteStats() {
    return traceRouteStats;
  }

  public void setTraceRouteStats(String traceRouteStats) {
    this.traceRouteStats =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))
            + " ==>"
            + traceRouteStats;
  }
}
