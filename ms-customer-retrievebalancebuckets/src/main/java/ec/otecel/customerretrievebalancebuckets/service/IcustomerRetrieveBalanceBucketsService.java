package ec.otecel.customerretrievebalancebuckets.service;

import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.customerretrievebalancebuckets.dto.exposition.GetretrievebalancebucketsRequestDTO;
import ec.otecel.customerretrievebalancebuckets.dto.exposition.GetretrievebalancebucketsResponseDTO;

public interface IcustomerRetrieveBalanceBucketsService {
    GetretrievebalancebucketsResponseDTO getretrievebalancebuckets(HeaderInType headers, GetretrievebalancebucketsRequestDTO request) throws ComponentException;
}