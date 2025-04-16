package com.alrajhi.until;

import com.alrajhi.client.FileNetUpload;
import com.alrajhi.client.MsgRsHdrType;
import com.alrajhi.error.error.BusinessErrorCodes;
import com.alrajhi.error.exception.BusinessRoleException;
import lombok.experimental.UtilityClass;

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
public class SoapUtil {

    public FileNetUpload buildFileNetUpload(String appendUrl) throws MalformedURLException {
        // URL of the actual WSDL on your SOAP server
        URL wsdlUrl = new URL("http://localhost:8080/ws/CommonUtilities/".concat(appendUrl).concat("?wsdl"));

        // QName = namespace URI + service name from your WSDL <service name="...">
        QName qname = new QName("http://www.xx.com/CommonUtilities", "CommonUtilities");

        return new FileNetUpload(wsdlUrl, qname);
    }

    public String generateName() {

        return UUID.randomUUID().toString().concat(".pdf");
    }

    public void responseCheck(MsgRsHdrType msgRsHdrType) {

        if (!msgRsHdrType.getStatus().getStatusCd().equals("I000000")) {

            throw new BusinessRoleException(BusinessErrorCodes.ESB_INTERNAL_API);
        }
    }
}
