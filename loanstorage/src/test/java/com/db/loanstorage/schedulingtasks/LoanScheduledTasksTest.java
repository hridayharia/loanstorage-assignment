package com.db.loanstorage.schedulingtasks;

import com.db.loanstorage.LoanStorageApplication;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import java.util.concurrent.TimeUnit;

@SpringJUnitConfig(LoanStorageApplication.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanScheduledTasksTest {

    @SpyBean
    private LoanScheduledTasks LoanScheduledTasks;

    @Test
    public void whenWaitOneSecond_thenScheduledIsCalledAtLeastTenTimes() {
        await().atMost(30, TimeUnit.MINUTES)
                .untilAsserted(() -> verify(LoanScheduledTasks,atLeast(2)).reportCurrentTime());
    }

}