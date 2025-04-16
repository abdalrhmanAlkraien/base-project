package com.alrajhi.client;

import com.alrajhi.config.UrlConfig;
import com.alrajhi.constant.FileClientConstant;
import com.alrajhi.dto.request.DocumentRequest;
import com.alrajhi.until.SoapUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.time.Instant;
import java.util.UUID;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 4/15/2025
 * @Time: 1:45 PM
 */
@Service
@RequiredArgsConstructor
public class FileClient {

    private final UrlConfig urlConfig;

    public void uploadFile(final DocumentRequest documentRequest, final String fileContent) throws MalformedURLException {

        FileNetUploadService fileService = SoapUtil.buildFileNetUpload(urlConfig.getUploadFile()).getFileNetUploadPort();

        FileNetUploadRsType response = fileService.fileNetUploadOperation(buildFileNetUploadRq(
                documentRequest, fileContent
        ));

        SoapUtil.responseCheck(response.getHdr());
    }

    private FileNetUploadRqType buildFileNetUploadRq(final DocumentRequest documentRequest, final String fileContent) {

        FileNetUploadRqType fileRequest = new FileNetUploadRqType();
        fileRequest.setBody(buildFileUploadBody(fileContent));
        fileRequest.setHdr(buildFileHdrRq(documentRequest));
        return fileRequest;
    }

    private MsgRqHdrType buildFileHdrRq(final DocumentRequest documentRequest) {

        MsgRqHdrType fileRequest = new MsgRqHdrType();

        AgtType agt = new AgtType();
        agt.setCICNum(documentRequest.getCicNum());
        fileRequest.setAgt(agt);

        MsgType msgType = new MsgType();
        msgType.setRqID(documentRequest.getRequestId());
        msgType.setSvcID(FileClientConstant.SERVICE_ID);
        msgType.setMsgTimestamp(Instant.now().toString());
        msgType.setFuncID(FileClientConstant.FUNCTION_ID);
        msgType.setSubSvcID(FileClientConstant.SUB_FUNCTION_ID);

        fileRequest.setMsg(msgType);

        SysType sysType = new SysType();
        sysType.setChID(ChIDType.INPUT_FILE_REQUEST); //
        sysType.setOSID(FileClientConstant.OSID);
        sysType.setSessionID(UUID.randomUUID().toString());
        sysType.setSessionLang(documentRequest.getReportLanguage().name());
        fileRequest.setSys(sysType);
        return fileRequest;
    }

    private FileNetUploadRqBodyType buildFileUploadBody(final String fileContent) {

        FileNetUploadRqBodyType body = new FileNetUploadRqBodyType();
        body.setFileContent(fileContent);
        body.setServiceType("Document service");
        body.setFileName(SoapUtil.generateName());
        return body;
    }

}
