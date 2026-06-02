package ec.otecel.digercic.service;

import ec.otecel.digercic.dto.exposition.DigercicAuthRequestDTO;
import ec.otecel.digercic.dto.exposition.DigercicAuthResponseDTO;
import ec.otecel.digercic.dto.exposition.DigercicDacRequestDTO;
import ec.otecel.digercic.dto.exposition.DigercicDacResponseDTO;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.component.error.exception.ComponentException;

public interface IDigercicService {
    DigercicAuthResponseDTO getToken(HeaderInType headerReq, DigercicAuthRequestDTO request) throws ComponentException;
    DigercicDacResponseDTO queryByDac(HeaderInType headerReq, DigercicDacRequestDTO request) throws ComponentException;
}