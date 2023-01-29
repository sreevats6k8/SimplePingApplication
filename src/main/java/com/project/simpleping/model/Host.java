package com.project.simpleping.model;

import java.util.Objects;

public class Host {
  private String hostName;

  private boolean isTcpPingActive;
  private boolean isIcmpPingActive;
  private boolean isTracertActive;

  public Host(String hostName) {
    this.hostName = hostName;
  }

  public boolean isTcpPingActive() {
    return isTcpPingActive;
  }

  public void setTcpPingActive(boolean tcpPingActive) {
    isTcpPingActive = tcpPingActive;
  }

  public boolean isIcmpPingActive() {
    return isIcmpPingActive;
  }

  public void setIcmpPingActive(boolean icmpPingActive) {
    isIcmpPingActive = icmpPingActive;
  }

  public boolean isTracertActive() {
    return isTracertActive;
  }

  public void setTracertActive(boolean tracertActive) {
    isTracertActive = tracertActive;
  }

  @Override
  public String toString() {
    return "Host{" + "hostName='" + hostName + '\'' + '}';
  }

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Host host = (Host) o;
    return hostName.equals(host.hostName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hostName);
  }

  public String doTcpIpPing() {
    return this.getHostName() + "doTcpIpPing";
  }

  public String doTraceRoute() {
    return this.getHostName() + "doTraceRoute";
  }
}
