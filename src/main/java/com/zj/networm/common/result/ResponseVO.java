package com.zj.networm.common.result;


/**
 * @author Zhaojie
 * @param <T>
 */
public class ResponseVO<T> {

    private String code;
    private String message;
    private T data;

    public ResponseVO(String code,String message){
        this.code = code;
        this.message = message;
    }


    public ResponseVO(ReponseBuilder<T> builder){
        this.code = builder.code;
        this.message = builder.message;
        this.data = builder.data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static<T> ResponseVO<T> success(T data){
        return new ReponseBuilder().code("200").data(data).build();
    }


    @Override
    public String toString() {
        return "ResponseVO{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static void main(String[] args) {
        ResponseVO<String> result = ResponseVO.success("hello");
        System.out.println(result);
    }


    static class ReponseBuilder<T>{
        private String code;
        private String message;
        private T data;

        public ReponseBuilder<T> code(String code){
           this.code = code;
           return this;
        }

        public ReponseBuilder<T> message(String message){
            this.message = message;
            return this;
        }

        public ReponseBuilder<T> data(T data){
            this.data = data;
            return this;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public <T> ResponseVO<T> build(){
            return new ResponseVO(this);
        }
    }
}
