package backend.academy.passtracker.core.mapper;

import backend.academy.passtracker.core.entity.ExtendPassTimeRequest;
import backend.academy.passtracker.core.entity.MinioFile;
import backend.academy.passtracker.core.entity.PassRequest;
import backend.academy.passtracker.core.entity.User;
import backend.academy.passtracker.rest.model.pass.request.ExtendPassTimeRequestDTO;
import backend.academy.passtracker.rest.model.pass.request.PassRequestDTO;
import backend.academy.passtracker.rest.model.pass.request.PassRequestRequest;
import backend.academy.passtracker.rest.model.pass.request.ShortExtendPassTimeRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MinioFileMapper.class})
public interface ExtendPassTimeRequestMapper {

    ExtendPassTimeRequestDTO entityToDTO(ExtendPassTimeRequest request);

    ShortExtendPassTimeRequestDTO entityToShortDTO(ExtendPassTimeRequest request);

}
