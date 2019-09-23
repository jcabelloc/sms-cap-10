package pe.fintech.prestamos.events.models;

public class ClienteChangeModel{
    private String type;
    private String action;
    private Integer codCliente;
    private String correlationId;

    public ClienteChangeModel(){
        super();
    }

    public  ClienteChangeModel(String type, String action, Integer codCliente, String correlationId) {
        super();
        this.type   = type;
        this.action = action;
        this.codCliente = codCliente;
        this.correlationId = correlationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(Integer codCliente) {
		this.codCliente = codCliente;
	}

	public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

	@Override
	public String toString() {
		return "ClienteChangeModel [type=" + type + ", action=" + action + ", codCliente=" + codCliente
				+ ", correlationId=" + correlationId + "]";
	}

    
}
