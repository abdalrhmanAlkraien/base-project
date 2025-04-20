package com.alrajhi.until;

import com.alrajhi.client.FileNetUpload;
import com.alrajhi.client.MsgRsHdrType;
import com.alrajhi.error.error.BusinessErrorCodes;
import com.alrajhi.error.exception.BusinessRoleException;
import com.sun.xml.ws.wsdl.parser.InaccessibleWSDLException;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 4/15/2025
 * @Time: 2:52 PM
 */
@UtilityClass
@Log4j2
public class SoapUtil {

    public FileNetUpload buildFileNetUpload(String appendUrl) throws MalformedURLException {

        try {
            Class<?> saajClass = Class.forName("com.sun.xml.messaging.saaj.soap.SAAJMetaFactoryImpl");
            System.out.println("SAAJ MetaFactory class found: " + saajClass.getName());
        } catch (ClassNotFoundException e) {
            System.err.println("SAAJMetaFactoryImpl not found — saaj-impl is missing from runtime classpath!");
        }

        try {
            // ✅ Load the WSDL from classpath (e.g. in common-lib/resources/wsdl/CommonUtilities.wsdl)
            URL wsdlUrl = FileNetUpload.class
                    .getClassLoader()
                    .getResource("wsdl/CommonUtilities.wsdl");

            if (wsdlUrl == null) {
                throw new RuntimeException("WSDL not found in classpath at wsdl/CommonUtilities.wsdl");
            }

            // ✅ Create service instance manually with QName
            QName serviceName = new QName("http://www.alrajhiwebservices.com/CommonUtilities", "FileNetUpload");

            return new FileNetUpload(wsdlUrl, serviceName);

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

            log.info("the response have error code {} and the request id {}",
                    msgRsHdrType.getStatus().getStatusCd(),
                    requestId);

            throw new BusinessRoleException(BusinessErrorCodes.ESB_INTERNAL_API);
        }
    }
}
