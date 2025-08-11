package com.kaldi.app.common.enums;

import jakarta.ws.rs.core.Response;

public enum ResponseStatus {
    //ToDo EXAMPLE
    SUCCESS {
        @Override
        public Response buildResponse(Object content) {
            return Response.ok(content).build();
        }
    },
    ERROR {
        @Override
        public Response buildResponse(Object content) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(content)
                    .build();
        }
    },
    BAD_REQUEST {
        @Override
        public Response buildResponse(Object content) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(content)
                    .build();
        }
    },
    NOT_FOUND {
        @Override
        public Response buildResponse(Object content) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(content)
                    .build();
        }
    };

    public abstract Response buildResponse(Object content);
}
