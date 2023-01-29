package com.project.simpleping.cache;

import com.project.simpleping.model.Host;
import com.project.simpleping.model.HostStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class HostStatisticsCache {

  private static final Logger LOGGER = LoggerFactory.getLogger(HostStatisticsCache.class);
  private static volatile HostStatisticsCache instance = null;
  public final ConcurrentHashMap map = new ConcurrentHashMap<>();

  private HostStatisticsCache() {}

  public static HostStatisticsCache getInstance() {
    if (instance == null) {
      synchronized (HostStatisticsCache.class) {
        instance = new HostStatisticsCache();
      }
    }
    return instance;
  }

  public ConcurrentHashMap<Host, HostStatistics> getCache() {
    return map;
  }

  public void displayCache() {
    final StringBuilder statistics = new StringBuilder();

    this.getCache().entrySet().stream()
        .forEach(
            action -> {
              statistics.append(
                  String.format(
                      "Statistics for host %s is as below \r\n[%s]",
                      action.getKey().getHostName(), action.getValue().toString()));
              statistics.append("\r\n");
            });
    LOGGER.info(
        String.format(
            "Statistics at [%s] is as below \r\n [%s]",
            LocalDateTime.now(), statistics.toString()));
    statistics.setLength(0);
  }
}
