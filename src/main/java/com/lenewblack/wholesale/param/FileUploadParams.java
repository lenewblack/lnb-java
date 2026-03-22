package com.lenewblack.wholesale.param;

import java.io.File;

public final class FileUploadParams {

    private final File file;
    private final byte[] content;
    private final String filename;
    private final String mimeType;
    private final String context;

    private FileUploadParams(Builder builder) {
        this.file = builder.file;
        this.content = builder.content;
        this.filename = builder.filename;
        this.mimeType = builder.mimeType != null ? builder.mimeType : "application/octet-stream";
        this.context = builder.context;
    }

    public File getFile() { return file; }
    public byte[] getContent() { return content; }
    public String getFilename() { return filename; }
    public String getMimeType() { return mimeType; }
    public String getContext() { return context; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private File file;
        private byte[] content;
        private String filename;
        private String mimeType;
        private String context;

        public Builder setFile(File file) { this.file = file; return this; }
        public Builder setContent(byte[] content) { this.content = content; return this; }
        public Builder setFilename(String filename) { this.filename = filename; return this; }
        public Builder setMimeType(String mimeType) { this.mimeType = mimeType; return this; }
        public Builder setContext(String context) { this.context = context; return this; }

        public FileUploadParams build() {
            if (file == null && content == null) {
                throw new IllegalStateException("Either file or content must be provided");
            }
            if (content != null && filename == null) {
                throw new IllegalStateException("filename is required when uploading raw content");
            }
            return new FileUploadParams(this);
        }
    }
}
