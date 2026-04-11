package org.learning.sistemacanchas.mapper;

import org.learning.sistemacanchas.DTOs.CanchaDTOReq;
import org.learning.sistemacanchas.DTOs.CanchaSummaryDTORes;
import org.learning.sistemacanchas.entity.Cancha;

public class CanchaMapper {
    public static Cancha canchaDTOReqToCancha(CanchaDTOReq dto){
        return Cancha.builder()
                .nombre(dto.getNombre())
                .tipo(dto.getTipo())
                .build();
    }

    public static CanchaSummaryDTORes canchaToCanchaSummaryDTORes(Cancha entity){
        return CanchaSummaryDTORes.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .tipo(entity.getTipo())
                .creacion(entity.getCreacion())
                .ultimaActualizacion(entity.getUltimaActualizacion())
                .build();
    }
}
