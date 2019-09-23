package pe.fintech.prestamos.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Prestamo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codPrestamo;
	
	@Column(nullable=false)
	private LocalDate fechaSolicitud;
	
	@Column(nullable=false)
	private BigDecimal monto;
	
	@Column(nullable=false)
	private Integer codCliente;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Estado estado;
	
	@Transient
	private String nombreCliente ="";
	
	@Transient
	private String emailCliente = "";
	
	@Transient
	private String telefonoCliente = "";
	
	public enum Estado {
		SOLICITADO, APROBADO, OTORGADO, NO_DISPONIBLE
	}
	
	public Prestamo() {
		
	}
	
	// Getters and Setters
	
	public Integer getCodPrestamo() {
		return codPrestamo;
	}

	public void setCodPrestamo(Integer codPrestamo) {
		this.codPrestamo = codPrestamo;
	}

	public LocalDate getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(LocalDate fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Integer getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(Integer codCliente) {
		this.codCliente = codCliente;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public String getTelefonoCliente() {
		return telefonoCliente;
	}

	public void setTelefonoCliente(String telefonoCliente) {
		this.telefonoCliente = telefonoCliente;
	}

	public Prestamo withCodPrestamo(Integer codPrestamo) {
		this.setCodPrestamo(codPrestamo);
		return this;
	}
	
	public Prestamo withFechaSolicitud(LocalDate fechaSolicitud) {
		this.setFechaSolicitud(fechaSolicitud);
		return this;
	}
	
	public Prestamo withMonto(BigDecimal monto) {
		this.setMonto(monto);
		return this;
	}
	
	public Prestamo withCodCliente(Integer codCliente) {
		this.setCodCliente(codCliente);
		return this;
	}
	
	public Prestamo withEstado(Estado estado) {
		this.setEstado(estado);
		return this;
	}
	
	public Prestamo withNombreCliente(String nombreCliente) {
		this.setNombreCliente(nombreCliente);
		return this;
	}
	
	public Prestamo withEmailCliente(String emailCliente) {
		this.setEmailCliente(emailCliente);
		return this;
	}
	
	public Prestamo withTelefonoCliente(String telefonoCliente) {
		this.setTelefonoCliente(telefonoCliente);
		return this;
	}
	
}
