package si.fri.rso.samples.imagecatalog.models.converters;

import si.fri.rso.samples.imagecatalog.lib.UporabnikMetadata;
import si.fri.rso.samples.imagecatalog.models.entities.UporabnikMetadataEntity;

public class UporabnikiMetadataConverter {

    public static UporabnikMetadata toDto(UporabnikMetadataEntity entity) {

        UporabnikMetadata dto = new UporabnikMetadata();
        dto.setId(entity.getId());
        dto.setIme(entity.getIme());
        dto.setPriimek(entity.getPriimek());
        dto.setEmail(entity.getEmail());
        return dto;

    }

    public static UporabnikMetadataEntity toEntity(UporabnikMetadata dto) {

        UporabnikMetadataEntity entity = new UporabnikMetadataEntity();
        entity.setId(dto.getId());
        entity.setIme(dto.getIme());
        entity.setPriimek(dto.getPriimek());
        entity.setEmail(dto.getEmail());
        return entity;

    }
}
