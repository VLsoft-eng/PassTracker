package backend.academy.passtracker.core.controller;

import backend.academy.passtracker.core.service.FacultyService;
import backend.academy.passtracker.rest.model.faculty.FacultyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    @GetMapping
    private FacultyDTO getFacultyById(
            @RequestParam UUID facultyId
    ) {
        return facultyService.getFacultyById(facultyId);
    }

    @GetMapping("/list")
    private List<FacultyDTO> getFaculties(
            @RequestParam String facultyName
    ) {
        return facultyService.getFaculties(facultyName);
    }

}
