package ec.otecel.allmsisdn.service;

import ec.otecel.allmsisdn.dto.exposition.AllMsisdnRequestDTO;
import ec.otecel.allmsisdn.dto.exposition.AllMsisdnResponseDTO;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.component.error.exception.ComponentException;

public interface IAllMsisdnService {
    AllMsisdnResponseDTO allMsisdn(HeaderInType headerReq, AllMsisdnRequestDTO request) throws ComponentException;
}