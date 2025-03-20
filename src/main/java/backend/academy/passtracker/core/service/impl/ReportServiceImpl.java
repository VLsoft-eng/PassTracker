package backend.academy.passtracker.core.service.impl;

import backend.academy.passtracker.core.exception.BadRequestException;
import backend.academy.passtracker.core.service.PassRequestService;
import backend.academy.passtracker.core.service.ReportService;
import backend.academy.passtracker.rest.model.pass.request.ShortPassRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private static final Long DAY_IN_SECONDS = 86400L;

    private final PassRequestService passRequestService;

    @Override
    public ResponseEntity<byte[]> createReport(Instant dateStart, Instant dateEnd, List<Long> groupIds) {

        if (groupIds == null || groupIds.isEmpty()) {
            throw new BadRequestException("Выберите хотя бы одну группу");
        }

        if (dateStart.isAfter(dateEnd)) {
            throw new BadRequestException("Дата начала не должна быть позднее даты конца");
        }

        try {
            List<ShortPassRequestDTO> requests = passRequestService.getPassRequests(
                    null,
                    null,
                    dateStart,
                    dateEnd,
                    null,
                    groupIds,
                    true
            );

            DateTimeFormatter formatterWithYear = DateTimeFormatter.ofPattern("yyyy.MM.dd")
                    .withZone(ZoneId.systemDefault());
            DateTimeFormatter formatterWithoutYear = DateTimeFormatter.ofPattern("MM.dd")
                    .withZone(ZoneId.systemDefault());

            String formattedDateStart = formatterWithYear.format(dateStart);
            String formattedDateEnd = formatterWithYear.format(dateEnd);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Пропуски " + formattedDateStart + "-" + formattedDateEnd);

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Студент");

            int columnCount = 1;
            Instant currentDate = dateStart;
            Map<LocalDate, Integer> dateColumnIndex = new HashMap<>();

            while (!currentDate.isAfter(dateEnd)) {
                String formattedDate = formatterWithoutYear.format(currentDate);
                headerRow.createCell(columnCount).setCellValue(formattedDate);
                dateColumnIndex.put(currentDate.atZone(ZoneId.systemDefault()).toLocalDate(), columnCount);
                currentDate = currentDate.plusSeconds(DAY_IN_SECONDS);
                columnCount++;
            }

            Map<String, Map<LocalDate, Boolean>> studentAbsences = new HashMap<>();

            for (ShortPassRequestDTO request : requests) {
                String studentName = request.getUser().getFullName();
                Instant requestStart = request.getDateStart();
                Instant requestEnd = request.getDateEnd();

                studentAbsences.putIfAbsent(studentName, new HashMap<>());

                Instant absenceDate = requestStart;
                while (!absenceDate.isAfter(requestEnd)) {
                    if (absenceDate.isAfter(dateEnd)) break;
                    if (absenceDate.isBefore(dateStart)) {
                        absenceDate = absenceDate.plusSeconds(DAY_IN_SECONDS);
                        continue;
                    }

                    studentAbsences.get(studentName).put(absenceDate.atZone(ZoneId.systemDefault()).toLocalDate(), true);
                    absenceDate = absenceDate.plusSeconds(DAY_IN_SECONDS);
                }
            }

            CellStyle redCellStyle = workbook.createCellStyle();
            redCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            redCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            redCellStyle.setAlignment(HorizontalAlignment.CENTER);
            redCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            CellStyle borderStyle = workbook.createCellStyle();
            borderStyle.setBorderTop(BorderStyle.THIN);
            borderStyle.setBorderBottom(BorderStyle.THIN);
            borderStyle.setBorderLeft(BorderStyle.THIN);
            borderStyle.setBorderRight(BorderStyle.THIN);

            int rowNum = 1;
            for (Map.Entry<String, Map<LocalDate, Boolean>> entry : studentAbsences.entrySet()) {
                String studentName = entry.getKey();
                Map<LocalDate, Boolean> absences = entry.getValue();

                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(studentName);

                for (Map.Entry<LocalDate, Boolean> absence : absences.entrySet()) {
                    LocalDate absenceDate = absence.getKey();
                    if (dateColumnIndex.containsKey(absenceDate)) {
                        int columnIndex = dateColumnIndex.get(absenceDate);
                        Cell cell = row.getCell(columnIndex);

                        if (cell == null) {
                            cell = row.createCell(columnIndex);
                        }

                        cell.setCellValue("X");
                        cell.setCellStyle(redCellStyle);
                    }
                }

                for (int i = 0; i < columnCount; i++) {
                    Cell cell = row.getCell(i);
                    if (cell == null) {
                        cell = row.createCell(i);
                    }
                    cell.setCellStyle(borderStyle);
                }
            }

            for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell == null) {
                    cell = headerRow.createCell(i);
                }
                cell.setCellStyle(borderStyle);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            byte[] bytes = outputStream.toByteArray();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(bytes);

        } catch (Exception e) {
            log.error("Ошибка в формировании отчета", e);
            throw new RuntimeException("Ошибка в формировании отчета");
        }
    }
}
