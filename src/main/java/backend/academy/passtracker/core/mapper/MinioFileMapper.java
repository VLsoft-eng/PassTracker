package backend.academy.passtracker.core.mapper;

import backend.academy.passtracker.core.dto.UserCreateDto;
import backend.academy.passtracker.core.entity.MinioFile;
import backend.academy.passtracker.core.entity.User;
import backend.academy.passtracker.rest.model.minio.file.MinioFileDTO;
import backend.academy.passtracker.rest.model.minio.file.ShortMinioFileDTO;
import backend.academy.passtracker.rest.model.user.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MinioFileMapper {

    MinioFileDTO entityToDTO(MinioFile minioFile);

    ShortMinioFileDTO entityToShortDTO(MinioFile minioFile);

}
