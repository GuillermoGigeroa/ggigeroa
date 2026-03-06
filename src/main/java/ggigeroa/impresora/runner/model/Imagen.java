package ggigeroa.impresora.runner.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Imagen {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonProperty("nombre")
	private String nombre;
	
	@JsonProperty("base64")
	private String base64;
	
	public Imagen() {
		super();
	}

	public Imagen(String nombre, String base64) {
		this.nombre = nombre;
		this.base64 = base64;
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

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	@Override
	public int hashCode() {
		return Objects.hash(base64, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Imagen other = (Imagen) obj;
		return Objects.equals(base64, other.base64) && Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Imagen [id=" + id + ", nombre=" + nombre + ", base64=" + base64 + "]";
	}
	
}
