package ec.otecel.customerInvoice.service;

import ec.otecel.customerInvoice.adapter.NetcrackerRDBAdapter;
import ec.otecel.customerInvoice.adapter.NetcrackerRBMAdapter;
import ec.otecel.customerInvoice.constants.MsConstants;
import ec.otecel.customerInvoice.dto.exposition.CheckInvoiceRequestDTO;
import ec.otecel.customerInvoice.dto.exposition.CheckInvoiceResponseDTO;
import ec.otecel.customerInvoice.util.BasicOperationService;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.logs.config.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.otecel.customerInvoice.dto.exposition.GetAccountNumberRequestDTO;
import ec.otecel.customerInvoice.dto.exposition.GetAccountNumberResponseDTO;
import ec.otecel.customerInvoice.dto.exposition.GetInvoiceRequestDTO;
import ec.otecel.customerInvoice.dto.exposition.GetInvoiceResponseDTO;

@Service
public class CustomerCheckInvoiceServiceImp implements ICustomerCheckInvoiceService {

    @Autowired
    private LoggerService loggerService;

    @Autowired
    private NetcrackerRDBAdapter netcrackerRDBAdapter;

    @Autowired
    private NetcrackerRBMAdapter netcrackerRBMAdapter;

    @Override
    public CheckInvoiceResponseDTO checkInvoice(HeaderInType h, CheckInvoiceRequestDTO req) throws ComponentException {
        return new BasicOperationService<CheckInvoiceResponseDTO, CheckInvoiceRequestDTO>(h, new CheckInvoiceResponseDTO(), req) {
            @Override
            public CheckInvoiceResponseDTO process(HeaderInType headerIn, CheckInvoiceRequestDTO request) throws ComponentException {
                // Paso 1: Obtener el número de cuenta
                GetAccountNumberResponseDTO accountResponse = netcrackerRDBAdapter.getAccountNumberByMsisdn(headerIn, new GetAccountNumberRequestDTO(request.getPhoneNumber(), request.getTransactionDate(), Integer.valueOf(request.getDelta())));
                // Paso 2: Obtener la factura por número de cuenta
                GetInvoiceResponseDTO invoiceResponse = netcrackerRBMAdapter.getInvoiceByAccountNumber(headerIn, new GetInvoiceRequestDTO(accountResponse.getData().getBan()));
                // Paso 3: Mapear al DTO de exposición (contrato del controller)
                return new CheckInvoiceResponseDTO(accountResponse.getData().getBan(), invoiceResponse.getData().getBills(), invoiceResponse.getData().getRetry());
            }
        }.initializer(loggerService)
         .setCommonInfo(MsConstants.SERVICE, MsConstants.METHOD_NAME_CHECK_INVOICE, getClass().getSimpleName())
         .run();
    }
}