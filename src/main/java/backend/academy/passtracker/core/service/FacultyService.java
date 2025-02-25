package backend.academy.passtracker.core.service;

import backend.academy.passtracker.rest.model.FacultyDTO;

import java.util.List;
import java.util.UUID;

public interface FacultyService {

    FacultyDTO getFacultyById(UUID facultyId);

    List<FacultyDTO> getFaculties(String facultyName);

}
