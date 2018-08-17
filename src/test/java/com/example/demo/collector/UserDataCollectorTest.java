//package com.example.demo.collector;
//
//import com.example.demo.configuration.properties.ApplicationProperties;
//import com.example.demo.domain.User;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.TemporaryFolder;
//import org.junit.runner.RunWith;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.Assert.assertTrue;
//
///**
// * UserDataCollectorTest test cases of {@link UserDataCollector}
// */
//@RunWith(MockitoJUnitRunner.class)
//public class UserDataCollectorTest {
//
//    private UserDataCollectorWrapper protocolDataCollector;
//
//    @Rule
//    public TemporaryFolder folder = new TemporaryFolder();
//
//    private User data;
//    private CountDownLatch latch;
//
//    @Before
//    public void setUp() throws IOException {
//        latch = new CountDownLatch(1);
//        ApplicationProperties applicationProperties = new ApplicationProperties();
//        applicationProperties.setWorkDir("../log/");
//        protocolDataCollector = new UserDataCollectorWrapper(applicationProperties);
//        protocolDataCollector.init();
//        data = User.builder()
//                .status("a")
//                .admin(true)
//                .build();
//    }
//
//    @After
//    public void clean() {
//        protocolDataCollector.destroy();
//    }
//
//    @Test
//    public void save_validData_successful() throws Exception {
//        protocolDataCollector.write(data);
//        latch.await(1, TimeUnit.SECONDS);
////        String fullFileName = properties.getWorkDir() + String.format(properties.getProtocolFileNameTemplate(), properties.getJobId());
//        assertTrue(" Not found", Paths.get("E:/Files").toFile().exists());
//    }
//
//    private class UserDataCollectorWrapper extends UserDataCollector {
//        public UserDataCollectorWrapper(ApplicationProperties applicationProperties) {
//            super(applicationProperties);
//        }
//
//        @Override
//        protected void write(User data) {
//            super.write(data);
//            latch.countDown();
//        }
//    }
//}
