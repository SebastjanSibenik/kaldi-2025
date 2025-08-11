package com.kaldi.app.common.response;

import com.kaldi.app.common.enums.ResponseStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class BuildResponse {
    public Response createResponse(ResponseStatus responseStatus, Object content) {
        return responseStatus.buildResponse(content);
    }
}
