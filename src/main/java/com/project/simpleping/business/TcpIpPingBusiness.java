package com.project.simpleping.business;

public class TcpIpPingBusiness {
  private static volatile TcpIpPingBusiness instance = null;
  // private constructor
  private TcpIpPingBusiness() {}

  public static TcpIpPingBusiness getInstance() {
    if (instance == null) {
      synchronized (TcpIpPingBusiness.class) {
        instance = new TcpIpPingBusiness();
      }
    }
    return instance;
  }
}
