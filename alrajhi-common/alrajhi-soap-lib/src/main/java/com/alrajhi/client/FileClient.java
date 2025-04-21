package com.alrajhi.client;

import com.alrajhi.config.UrlConfig;
import com.alrajhi.constant.FileClientConstant;
import com.alrajhi.dto.request.DocumentRequest;
import com.alrajhi.until.SoapUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 4/15/2025
 * @Time: 1:45 PM
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class FileClient {

    private final UrlConfig urlConfig;

    public String uploadFile(final DocumentRequest documentRequest, final String fileContent) throws MalformedURLException {

        log.info("send request to ESB for save the file and the request id {}", documentRequest.getRequestId());
        FileNetUploadService fileService = SoapUtil.buildFileNetUpload(urlConfig.getMainUrl(), urlConfig.getCommonUrl(), urlConfig.getUploadFile());

        FileNetUploadRsType response = fileService.fileNetUploadOperation(buildFileNetUploadRq(
                documentRequest, fileContent
        ));

        SoapUtil.responseCheck(documentRequest.getRequestId(), response.getHdr());

        log.info("response returned successfully and the request id {}", documentRequest.getRequestId());
        return response.getBody().getFileNetID();
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

        // Current timestamp
        LocalDateTime now = LocalDateTime.now();

        // Formatter to match your required format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


        MsgType msgType = new MsgType();
        msgType.setRqID(documentRequest.getRequestId());
        msgType.setSvcID(FileClientConstant.SERVICE_ID);
        msgType.setMsgTimestamp(now.format(formatter));
        msgType.setFuncID(FileClientConstant.FUNCTION_ID);
        msgType.setSubSvcID(FileClientConstant.SUB_FUNCTION_ID);

        fileRequest.setMsg(msgType);

        SysType sysType = new SysType();
        sysType.setChID(ChIDType.INPUT_FILE_REQUEST); //
        sysType.setOSID(FileClientConstant.OSID);
        sysType.setSessionID(documentRequest.getSessionId());
        sysType.setSessionLang(documentRequest.getReportLanguage().name());
        fileRequest.setSys(sysType);
        return fileRequest;
    }

    private FileNetUploadRqBodyType buildFileUploadBody(final String fileContent) {

        FileNetUploadRqBodyType body = new FileNetUploadRqBodyType();
        body.setFileContent(fileContent);
        body.setServiceType(FileClientConstant.SERVICE_TYPE);
        body.setFileName(SoapUtil.generateName());
        return body;
    }

}
