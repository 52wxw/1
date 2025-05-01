package com.example.util;

import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.FileStore;
import java.nio.file.Files;

public class MonitoringUtil {

    private static final OperatingSystemMXBean osBean = 
            (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public static double getCpuUsage() {
        double cpuLoad = osBean.getSystemCpuLoad();
        return cpuLoad < 0 ? 0 : cpuLoad * 100; // 转换为百分比
    }

    public static double getMemoryUsage() {
        long totalMemory = osBean.getTotalPhysicalMemorySize();
        long freeMemory = osBean.getFreePhysicalMemorySize();
        return 100.0 - (freeMemory * 100.0 / totalMemory);
    }

    public static double getDiskUsage() {
        try {
            FileStore fileStore = Files.getFileStore(new File("/").toPath());
            long totalSpace = fileStore.getTotalSpace();
            long usedSpace = totalSpace - fileStore.getUsableSpace();
            return (usedSpace * 100.0) / totalSpace;
        } catch (IOException e) {
            return 0.0;
        }
    }
}