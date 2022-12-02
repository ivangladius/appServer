
package appServer;

import java.io.Serializable;

public class MessageObject implements Serializable {
    private int key;
    private String operation;
    private String payload; // optional if empty null

    public MessageObject() { }

    public MessageObject(int key, String operation, String payload) {
	this.key = key;
	this.operation = operation;
	this.payload = payload;
    }

    public int getKey() { return key; }
    public String getOperation() { return operation; }
    public String getPayload() { return payload; }

    public void setKey(int key) { this.key = key; }
    public void setOperation(String operation) { this.operation = operation; }
    public void setPayload(String payload) { this.payload = payload; }


    public void print() {
	System.out.println("key: " + key);
	System.out.println("operation: " + operation);
	System.out.println("payload: " + payload);
    }
}
