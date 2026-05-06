package ec.otecel.customerInvoice.service;

import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.customerInvoice.dto.exposition.CheckInvoiceRequestDTO;
import ec.otecel.customerInvoice.dto.exposition.CheckInvoiceResponseDTO;
import ec.otecel.component.error.exception.ComponentException;

public interface ICustomerCheckInvoiceService {
    CheckInvoiceResponseDTO checkInvoice(HeaderInType headers, CheckInvoiceRequestDTO request) throws ComponentException;
}