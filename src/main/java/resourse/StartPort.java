package resourse;

import java.io.Serializable;

public class StartPort implements Serializable {
    private static final long serialVersionUID = -3895203507200457732L;
    private String name;
    private int port;

    public StartPort() {
        this.name = "Nothing";
        this.port = 0;
    }

    public StartPort(String name, int port) {
        this.setPort(port);
        this.setName(name);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "Name: " + name + " port: " + port;
    }
}