package ggigeroa.impresora.runner.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Registro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonProperty("Nombre")
	private String nombre;

	@JsonProperty("idUsuario")
	private String idUsuario;

	public Registro() {
		super();
	}

	public Registro(String nombre, String idUsuario) {
		this.nombre = nombre;
		this.idUsuario = idUsuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre, idUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Registro other = (Registro) obj;
		return Objects.equals(nombre, other.nombre) && Objects.equals(idUsuario, other.idUsuario);
	}

	@Override
	public String toString() {
		return "Registro [id=" + id + ", nombre=" + nombre + ", idUsuario=" + idUsuario + "]";
	}

}
