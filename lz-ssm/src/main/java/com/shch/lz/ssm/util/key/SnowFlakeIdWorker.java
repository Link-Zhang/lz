package com.shch.lz.ssm.util.key;

/**
 * Created by Link at 14:56 on 4/16/18.
 */
// |--   1--|--       41--|--                       10(5,5)--|--      12--|
// |--sign--|--timestamp--|--machine(DataCenterId,WorkerId)--|--sequence--|
public class SnowFlakeIdWorker {
    // 2018-01-01 0:0:0
    private final long twepoch = 1514736000000L;

    private final long datacenterIdBits = 5L;

    private final long workerIdBits = 5L;

    private final long sequenceBits = 12L;

    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    private final long workerIdShift = sequenceBits;

    private final long datacenterIdShift = sequenceBits + workerIdBits;

    private final long timestampShift = sequenceBits + workerIdBits + datacenterIdBits;

    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId;

    private long datacenterId;

    private long sequence = 0L;

    private long lastTimestamp = -1L;

    public SnowFlakeIdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("Worker Id Error: should between 0 and %d", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("Datacenter Id Error: shoule between 0 and %d", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    protected long timeGenerate() {
        return SystemClock.now();
    }

    protected long tillNextMillis(long lastTimestamp) {
        long timestamp = timeGenerate();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGenerate();
        }
        return timestamp;

    }

    public synchronized long nextId() {
        long timestamp = timeGenerate();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Time Error: refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (0 == sequence) {
                timestamp = tillNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

//    public static void main(String[] args) {
//        long start = System.currentTimeMillis();
//        SnowFlakeIdWorker idWorker0 = new SnowFlakeIdWorker(0, 0);
//        for (int i = 0; i < 100; i++) {
//            long id = idWorker0.nextId();
//            System.out.println(id);
//        }
//        System.out.println("Time Cost：" + (System.currentTimeMillis() - start));
//    }
}
