package com.yohu.smarthome.util;

import com.yohu.smarthome.bean.system.SystemNet;
import com.yohu.smarthome.bean.system.SystemStatus;
import com.yohu.smarthome.bean.system.SystemStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.hardware.PowerSource;
import oshi.hardware.Sensors;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.util.Util;

/**
 * @author huyong
 * @since 2018/12/3
 */
public class SystemStatusUtils {

    private static SystemInfo si = new SystemInfo();

    private static HardwareAbstractionLayer hal = si.getHardware();
    private static OperatingSystem os = si.getOperatingSystem();

    private static void printMemory(GlobalMemory memory, SystemStatus systemStatus) {

        systemStatus.setUsedMemory(FormatUtil.formatBytes(memory.getTotal() - memory.getAvailable()));
        systemStatus.setTotalMemory(FormatUtil.formatBytes(memory.getTotal()));
        System.out.println("Memory: " + FormatUtil.formatBytes(memory.getAvailable()) + "/"
                + FormatUtil.formatBytes(memory.getTotal()));
//        System.out.println("Swap used: " + FormatUtil.formatBytes(memory.getSwapUsed()) + "/"
//                + FormatUtil.formatBytes(memory.getSwapTotal()));
    }

    private static void printCpu(CentralProcessor processor, SystemStatus systemStatus) {

        systemStatus.setUpTime(FormatUtil.formatElapsedSecs(processor.getSystemUptime()));
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long sys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;

        System.out.format(
                "User: %.1f%% Nice: %.1f%% System: %.1f%% Idle: %.1f%% IOwait: %.1f%% IRQ: %.1f%% SoftIRQ: %.1f%% Steal: %.1f%%%n",
                100d * user / totalCpu, 100d * nice / totalCpu, 100d * sys / totalCpu, 100d * idle / totalCpu,
                100d * iowait / totalCpu, 100d * irq / totalCpu, 100d * softirq / totalCpu, 100d * steal / totalCpu);
        systemStatus.setCpuFreq(String.format("%.1f%%", 100d * user / totalCpu));

    }

    private static void printProcesses(OperatingSystem os, GlobalMemory memory) {
        System.out.println("Processes: " + os.getProcessCount() + ", Threads: " + os.getThreadCount());
        // Sort by highest CPU
        List<OSProcess> procs = Arrays.asList(os.getProcesses(5, OperatingSystem.ProcessSort.CPU));

        System.out.println("   PID  %CPU %MEM       VSZ       RSS Name");
        for (int i = 0; i < procs.size() && i < 5; i++) {
            OSProcess p = procs.get(i);
            System.out.format(" %5d %5.1f %4.1f %9s %9s %s%n", p.getProcessID(),
                    100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime(),
                    100d * p.getResidentSetSize() / memory.getTotal(), FormatUtil.formatBytes(p.getVirtualSize()),
                    FormatUtil.formatBytes(p.getResidentSetSize()), p.getName());
        }
    }

    private static void printSensors(Sensors sensors, SystemStatus systemStatus) {
        System.out.format(" CPU Temperature: %.1f°C%n", sensors.getCpuTemperature());
        systemStatus.setCpuTemp(String.format("%.1f°C%n", sensors.getCpuTemperature()));
    }

    private static void printPowerSources(PowerSource[] powerSources, SystemStatus systemStatus) {
        if (powerSources.length == 0) {
            systemStatus.setPowerStatus("Unknown");
            systemStatus.setPowerBattery("Unknown");
        } else {
            double timeRemaining = powerSources[0].getTimeRemaining();
            if (timeRemaining < -1d) {
                systemStatus.setPowerStatus("Charging");
            } else if (timeRemaining < 0d) {
                systemStatus.setPowerStatus("Calculating time remaining");
            } else {
                systemStatus.setPowerStatus(String.format("%d:%02d remaining", (int) (timeRemaining / 3600),
                        (int) (timeRemaining / 60) % 60));
            }
            systemStatus.setPowerBattery(String.format("%n %s @ %.1f%%", powerSources[0].getName(), powerSources[0].getRemainingCapacity() * 100d));
        }

    }


    private static void printFileSystem(FileSystem fileSystem, SystemStatus systemStatus) {
        List<SystemStorage> systemStorageList = new ArrayList<>();
        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {

            long usable = fs.getUsableSpace();
            long total = fs.getTotalSpace();

            SystemStorage systemStorage = new SystemStorage();
            systemStorage.setUsedStorage( FormatUtil.formatBytes(total -usable));
            systemStorage.setTotalStorage(FormatUtil.formatBytes(fs.getTotalSpace()));
            systemStorage.setUsedFreq(String.format("%.1f%%", 100d * (total-usable) / total));
            systemStorage.setDriverName(fs.getName());

            System.out.format(" %s (%s) [%s] %s of %s free (%.1f%%) is %s and is mounted at %s%n", fs.getName(),
                    fs.getDescription().isEmpty() ? "file system" : fs.getDescription(), fs.getType(),
                    FormatUtil.formatBytes(usable), FormatUtil.formatBytes(fs.getTotalSpace()), 100d * usable / total,
                    fs.getVolume(), fs.getMount());

            systemStorageList.add(systemStorage);
        }
        systemStatus.setSystemStorageList(systemStorageList);
    }

    private static void printNetworkInterfaces(NetworkIF[] networkIFs, SystemStatus systemStatus) {
        List<SystemNet> systemNetList = new ArrayList<>();


        for (NetworkIF net : networkIFs) {
            SystemNet systemNet = new SystemNet();
            systemNet.setNetName(net.getName());
            systemNet.setIpAddress(Arrays.toString(net.getIPv4addr()));
            systemNet.setMacAddress(net.getMacaddr());

            systemNetList.add(systemNet);
        }
        systemStatus.setSystemNetList(systemNetList);
    }


    /**
     * 获取系统运行状态
     */
    public static SystemStatus getSystemStatus() {
        SystemStatus systemStatus = new SystemStatus();
        CentralProcessor centralProcessor = hal.getProcessor();

        printCpu(centralProcessor, systemStatus);
        printSensors(hal.getSensors(), systemStatus);
        printPowerSources(hal.getPowerSources(), systemStatus);
        printFileSystem(os.getFileSystem(), systemStatus);
        printNetworkInterfaces(hal.getNetworkIFs(), systemStatus);
        printMemory(hal.getMemory(), systemStatus);
        return systemStatus;
    }
}
