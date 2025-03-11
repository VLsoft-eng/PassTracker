package backend.academy.passtracker.core.controller;

import backend.academy.passtracker.core.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
@Tag(name = "Отчёт", description = "Контроллер, отвечающий за отчёт пропусков студентов")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    private ResponseEntity<byte[]> createReport(
            @RequestParam Instant dateStart,
            @RequestParam Instant dateEnd,
            @RequestParam List<Long> groupIds
    ) {
        return reportService.createReport(dateStart, dateEnd, groupIds);
    }

}
