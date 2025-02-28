package backend.academy.passtracker.core.mapper;

import backend.academy.passtracker.core.entity.ExtendPassTimeRequest;
import backend.academy.passtracker.core.entity.MinioFile;
import backend.academy.passtracker.core.entity.PassRequest;
import backend.academy.passtracker.core.entity.User;
import backend.academy.passtracker.rest.model.pass.request.ExtendPassTimeRequestDTO;
import backend.academy.passtracker.rest.model.pass.request.PassRequestDTO;
import backend.academy.passtracker.rest.model.pass.request.PassRequestRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, MinioFileMapper.class, ExtendPassTimeRequestMapper.class})
public interface ExtendPassTimeRequestMapper {

    @Mapping(target = "passRequestId", source = "passRequestId")
    ExtendPassTimeRequestDTO entityToDTO(ExtendPassTimeRequest request);

}
