package com.serverless.services;

import java.net.URL;

public interface BillFileService {

    URL getBillFileUrl(String clientId, String billId);

    URL getBillFileUploadUrl(String clientId, String billId);

}
