package com.example.demo.collector;

import com.example.demo.domain.User;
import com.example.demo.utils.UserDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class UserDataCollector {
    private static final String WORK_DIR = "C:\\";
    private final SynchronousQueue<User> queue = new SynchronousQueue<>(true);
    private final ExecutorService executor = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable);
        thread.setName("UserData");
        return thread;
    });
    private CSVPrinter printer;

    private AtomicBoolean closed = new AtomicBoolean(false);

    public void save(User user) {
        log.debug("Try save {}", user);
        try {
            queue.put(user);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("UserData save", e);
        }
    }

    @PostConstruct
    public void init() throws IOException {
        printer = createCSVPrinter(WORK_DIR);

        executor.execute(() -> {
            while (!Thread.interrupted()) {
                try {
                    write(queue.take());
                } catch (InterruptedException e) {
                    log.trace("", e);
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    log.error("Error in writeToFile", e);
                }
            }
        });
    }

    protected void write(User data) {
        Assert.notNull(data, "ProtocolData for write is null");
        try {
            printer.printRecord(UserDataUtils.prepareRecord(data));
            log.debug("Try write {}", data);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @PreDestroy
    public void destroy() {
        log.info("Start UserDataCollector shutdown");
        if (closed.get()) {
            log.debug("UserDataCollector if closed");
            return;
        }
        executor.shutdownNow();
        try {
            printer.flush();
        } catch (Exception e) {
            log.error("", e);
        } finally {
            IOUtils.closeQuietly(printer);
            closed.set(true);
        }
    }
    private CSVPrinter createCSVPrinter(String reportFileName) throws IOException {

        Path filePatch = Paths.get(reportFileName);
        if (!filePatch.toFile().exists()) {
            Files.createDirectories(filePatch.getParent());
        }
        final Writer writer = Files.newBufferedWriter(filePatch, StandardCharsets.UTF_8, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        return new CSVPrinter(new BufferedWriter(writer), CSVFormat.EXCEL);
    }
}
