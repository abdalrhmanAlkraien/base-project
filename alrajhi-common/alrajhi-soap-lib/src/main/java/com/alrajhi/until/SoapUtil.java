package com.alrajhi.until;

import com.alrajhi.client.FileNetUpload;
import com.alrajhi.client.FileNetUploadService;
import com.alrajhi.client.MsgRsHdrType;
import com.alrajhi.error.error.BusinessErrorCodes;
import com.alrajhi.error.exception.BusinessRoleException;
import com.sun.xml.ws.wsdl.parser.InaccessibleWSDLException;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import javax.xml.ws.BindingProvider;
import java.util.UUID;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 4/15/2025
 * @Time: 2:52 PM
 */
@UtilityClass
@Log4j2
public class SoapUtil {

    public FileNetUploadService buildFileNetUpload(
            final String baseUrl,
            final String pathUrl,
            final String directionUrl
    ) {

        try {

            FileNetUploadService fileService = new FileNetUpload().getFileNetUploadPort();

            BindingProvider bp = (BindingProvider) fileService;

            bp.getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", 10000);
            bp.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", 20000);

            // setup the url
            bp.getRequestContext().put(
                    BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                    buildUrl(baseUrl, pathUrl, directionUrl)
            );


            return fileService;

        } catch (InaccessibleWSDLException wsdlException) {

            log.error("error while building the wsdl url could not access the resource");
            log.error(wsdlException);
            throw new BusinessRoleException(BusinessErrorCodes.ESB_INTERNAL_API);
        }
    }

    public String generateName() {

        return UUID.randomUUID().toString().concat(".pdf");
    }

    public void responseCheck(final String requestId, MsgRsHdrType msgRsHdrType) {

        if (!msgRsHdrType.getStatus().getStatusCd().equals("I000000")) {

            log.error("the response have error code {} {} and the request id {}",
                    msgRsHdrType.getStatus().getStatusCd(),
                    msgRsHdrType.getStatus().getStatusDesc(),
                    requestId);

            throw new BusinessRoleException(BusinessErrorCodes.ESB_INTERNAL_API);
        }
    }

    private String buildUrl(final String baseUrl, final String path, final String directionUrl) {

        return new StringBuilder(baseUrl)
                .append("/")
                .append(path)
                .append("/")
                .append(directionUrl).toString();
    }
}
