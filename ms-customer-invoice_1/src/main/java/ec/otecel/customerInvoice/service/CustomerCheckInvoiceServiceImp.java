package ec.otecel.customerInvoice.service;

import ec.otecel.customerInvoice.adapter.NetcrackerRDBAdapter;
import ec.otecel.customerInvoice.adapter.NetcrackerRBMAdapter;
import ec.otecel.customerInvoice.constants.MsConstants;
import ec.otecel.customerInvoice.dto.exposition.CheckInvoiceRequestDTO;
import ec.otecel.customerInvoice.dto.exposition.CheckInvoiceResponseDTO;
import ec.otecel.customerInvoice.dto.exposition.GetAccountNumberResponseDTO;
import ec.otecel.customerInvoice.dto.integration.GetInvoiceRequestDTO;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.component.logs.config.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.otecel.customerInvoice.util.BasicOperationService;
import ec.otecel.common.model.commontypes.ErrorCodeType;

@Service
public class CustomerCheckInvoiceServiceImp implements ICustomerCheckInvoiceService {

    @Autowired
    private NetcrackerRDBAdapter netcrackerRDBAdapter;

    @Autowired
    private NetcrackerRBMAdapter netcrackerRBMAdapter;

    @Autowired
    private LoggerService loggerService;

    @Override
    public CheckInvoiceResponseDTO checkInvoice(HeaderInType h, CheckInvoiceRequestDTO req) throws ComponentException {
        return new BasicOperationService<CheckInvoiceResponseDTO, CheckInvoiceRequestDTO>(h, new CheckInvoiceResponseDTO(), req) {
            @Override
            public CheckInvoiceResponseDTO process(HeaderInType headerIn, CheckInvoiceRequestDTO request) throws ComponentException {
                // 1) Obtener el número de cuenta
                GetAccountNumberResponseDTO accountResponse = netcrackerRDBAdapter.getAccountNumberByMsisdn(headerIn, new GetAccoutNumberRequestDTO(request.getPhoneNumber(), convertDate(request.getTransactionDate()), Integer.valueOf(request.getDelta())));
                if (accountResponse == null) throw new ComponentException(ErrorCodeType.SYS_ERROR, "Sin respuesta del sistema externo", true, new String[]{}, this.getClass().getSimpleName());

                // 2) Obtener la factura por número de cuenta
                GetInvoiceResponseDTO invoiceResponse = netcrackerRBMAdapter.getInvoiceByAccountNumber(headerIn, new GetInvoiceRequestDTO(accountResponse.getData().getBan(), convertDate(request.getTransactionDate()), request.getDelta(), request.getSourceSystem(), MsConstants.BILL_STATUS, MsConstants.STATUS));
                if (invoiceResponse == null) throw new ComponentException(ErrorCodeType.SYS_ERROR, "Sin respuesta del sistema externo", true, new String[]{}, this.getClass().getSimpleName());

                // 3) Retornar la respuesta
                return new CheckInvoiceResponseDTO(accountResponse.getData().getBan(), invoiceResponse.getData().getBills(), invoiceResponse.getData().getRetry());
            }
        }.initializer(loggerService)
         .setCommonInfo(MsConstants.SERVICE, MsConstants.METHOD_NAME_CHECK_INVOICE, getClass().getSimpleName())
         .run();
    }

    private String convertDate(String date) {
        // Lógica de conversión de formatos de fechas
        return date; // Implementar la lógica de conversión aquí
    }
}