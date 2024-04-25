package danila.mediasoft.test.warehouse.exceptions;

public class OperationNotFoundException extends RuntimeException {
    String operationValue;
    public OperationNotFoundException(String message){
        super(message);
    }

    public String getOperationValue() {
        return operationValue;
    }

    public OperationNotFoundException setOperationValue(String operationValue) {
        this.operationValue = operationValue;
        return this;
    }
}
