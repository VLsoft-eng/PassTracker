package backend.academy.passtracker.core.mapper;

import backend.academy.passtracker.core.entity.PassRequest;
import backend.academy.passtracker.rest.model.pass.request.PassRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, MinioFileMapper.class, ExtendPassTimeRequestMapper.class})
public interface PassRequestMapper {

    PassRequestDTO entityToDTO(PassRequest passRequest);

}
