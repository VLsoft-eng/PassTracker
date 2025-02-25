package backend.academy.passtracker.core.service.impl;

import backend.academy.passtracker.core.exception.FacultyNotFoundException;
import backend.academy.passtracker.core.mapper.FacultyMapper;
import backend.academy.passtracker.core.repository.FacultyRepository;
import backend.academy.passtracker.core.service.FacultyService;
import backend.academy.passtracker.rest.model.FacultyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;

    @Transactional(readOnly = true)
    @Override
    public FacultyDTO getFacultyById(UUID facultyId) {
        return facultyMapper.entityToDTO(
                facultyRepository.findById(facultyId).orElseThrow(() -> new FacultyNotFoundException(facultyId))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<FacultyDTO> getFaculties(String facultyName) {
        return facultyRepository.findAllByFacultyNameLike(facultyName)
                .stream()
                .map(facultyMapper::entityToDTO)
                .collect(Collectors.toList());
    }
}
