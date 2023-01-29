package com.project.simpleping.service;

import com.project.simpleping.model.Host;

import java.util.List;

/** Service class in charge of retrieving list of all hosts from the config */
public class HostRetreivalService {
  private static volatile HostRetreivalService instance = null;

  private HostRetreivalService() {}

  public static HostRetreivalService getInstance() {
    if (instance == null) {
      synchronized (HostRetreivalService.class) {
        instance = new HostRetreivalService();
      }
    }
    return instance;
  }

  public List<Host> getAllHosts() {
    return List.of(
        new Host("jasmin.com"),
        new Host("oranum.com"),
        new Host("youtube.com"),
        new Host("google.com"),
        new Host("yahoo.com"));
  }
}
