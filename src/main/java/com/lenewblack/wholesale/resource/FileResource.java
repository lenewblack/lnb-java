package com.lenewblack.wholesale.resource;

import com.google.gson.Gson;
import com.lenewblack.wholesale.RequestOptions;
import com.lenewblack.wholesale.auth.TokenManager;
import com.lenewblack.wholesale.http.HttpClient;
import com.lenewblack.wholesale.http.HttpResponse;
import com.lenewblack.wholesale.param.FileUploadParams;

import java.util.LinkedHashMap;
import java.util.Map;

public final class FileResource extends AbstractResource {

    public FileResource(HttpClient httpClient, TokenManager tokenManager, Gson gson,
                        String baseUrl, RequestOptions defaultOptions) {
        super(httpClient, tokenManager, gson, baseUrl, defaultOptions);
    }

    public Map<String, Object> upload(FileUploadParams params) {
        return upload(params, null);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> upload(FileUploadParams params, RequestOptions opts) {
        Map<String, Object> parts = new LinkedHashMap<>();
        if (params.getFile() != null) {
            parts.put("file", params.getFile());
            parts.put("file_mime", params.getMimeType());
        } else {
            parts.put("file", params.getContent());
            parts.put("file_filename", params.getFilename());
            parts.put("file_mime", params.getMimeType());
        }
        if (params.getContext() != null) {
            parts.put("context", params.getContext());
        }
        HttpResponse response = authenticatedMultipart("/files", parts, opts);
        return parseBody(response, Map.class);
    }

    public void delete(String fileId) {
        delete(fileId, null);
    }

    public void delete(String fileId, RequestOptions opts) {
        authenticatedDelete("/files/" + fileId, opts);
    }
}
