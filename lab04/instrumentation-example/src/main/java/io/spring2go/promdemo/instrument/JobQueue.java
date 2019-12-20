package io.spring2go.promdemo.instrument;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import io.prometheus.client.Gauge;

/**
 * 作业队列
 * 已被Prometheus度量
 */
public class JobQueue {

    private final Gauge jobQueueSize = Gauge.build()
            .name("job_queue_size")
            .help("Current number of jobs waiting in queue")
            .register();

    private Queue<Job> queue = new LinkedBlockingQueue<Job>();

    public int size() {
        return queue.size();
    }

    public void push(Job job) {
        queue.offer(job);
        jobQueueSize.inc();
    }

    public Job pull() {
        Job job = queue.poll();
        if (job != null) {
            jobQueueSize.dec();
        }
        return job;
    }

}
