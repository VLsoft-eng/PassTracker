package backend.academy.passtracker.core.service;

import backend.academy.passtracker.core.entity.Faculty;
import backend.academy.passtracker.rest.model.faculty.CreateFacultyRequest;
import backend.academy.passtracker.rest.model.faculty.FacultyDTO;

import java.util.List;
import java.util.UUID;

public interface FacultyService {

    FacultyDTO getFacultyById(UUID facultyId);

    Faculty getRawFacultyById(UUID facultyId);

    List<FacultyDTO> getFaculties(String facultyName);

    FacultyDTO createFaculty(CreateFacultyRequest createFacultyRequest);

    void deleteFaculty(UUID facultyId);

}
