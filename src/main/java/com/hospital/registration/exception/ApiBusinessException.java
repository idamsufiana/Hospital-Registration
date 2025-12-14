package com.hospital.registration.exception;

public class ApiBusinessException extends RuntimeException {

        private final String code;
        private final int httpStatus;

        public ApiBusinessException(String code, String message) {
            super(message);
            this.code = code;
            this.httpStatus = 400; // default business error
        }

        public ApiBusinessException(String code, String message, int httpStatus) {
            super(message);
            this.code = code;
            this.httpStatus = httpStatus;
        }

        public String getCode() {
            return code;
        }

        public int getHttpStatus() {
            return httpStatus;
        }


}