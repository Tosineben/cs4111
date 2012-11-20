package courseworks;

public class RequestType {

    public static ReqType getRequestType(String request_type) {
        if ("add".equals(request_type)) {
            return ReqType.Add;
        }
        if ("update".equals(request_type)) {
            return ReqType.Update;
        }
        if ("delete".equals(request_type)) {
            return ReqType.Delete;
        }
        return ReqType.Unknown;
    }


    public enum ReqType {
        Add, Update, Delete, Unknown
    }
}
