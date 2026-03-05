package ggigeroa.impresora.runner;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Imagen {
	@JsonProperty("nombre")
	private String nombre;
	@JsonProperty("base64")
	private String base64;
	
	public Imagen() {
		super();
	}

	public Imagen(String nombre, String base64) {
		this.nombre = nombre;
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
		return "Imagen [nombre=" + nombre + ", base64=" + base64 + "]";
	}
	
}
