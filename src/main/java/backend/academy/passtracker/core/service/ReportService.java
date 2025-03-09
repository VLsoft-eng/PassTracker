package backend.academy.passtracker.core.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;

public interface ReportService {

    ResponseEntity<byte[]> createReport(
            Instant dateStart,
            Instant dateEnd,
            List<Long> groupIds
    );

}
