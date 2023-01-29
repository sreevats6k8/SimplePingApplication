package com.project.simpleping;

import com.project.simpleping.business.IcmpPingBusiness;
import com.project.simpleping.business.TraceRouteBusiness;
import com.project.simpleping.cache.HostStatisticsCache;
import com.project.simpleping.model.Host;
import com.project.simpleping.service.HostRetreivalService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SimplePingApplication {
  public static void main(String... args) throws InterruptedException {
    ScheduledExecutorService scheduledExecutorServiceIcmpPing = Executors.newScheduledThreadPool(3);
    for (Host host : HostRetreivalService.getInstance().getAllHosts()) {
      ScheduledFuture<?> scheduledFuture =
          scheduledExecutorServiceIcmpPing.scheduleAtFixedRate(
              () -> {
                IcmpPingBusiness.getInstance().doBusiness(host);
              },
              5,
              1,
              TimeUnit.SECONDS);
    }

    ScheduledExecutorService scheduledExecutorServiceTracert = Executors.newScheduledThreadPool(3);
    for (Host host : HostRetreivalService.getInstance().getAllHosts()) {
      ScheduledFuture<?> scheduledFuture =
          scheduledExecutorServiceTracert.scheduleAtFixedRate(
              () -> {
                TraceRouteBusiness.getInstance().doBusiness(host);
              },
              5,
              1,
              TimeUnit.SECONDS);
    }

    while (true) {
      HostStatisticsCache.getInstance().displayCache();
      Thread.sleep(10000);
    }
  }
}
