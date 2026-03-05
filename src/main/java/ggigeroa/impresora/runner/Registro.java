package ggigeroa.impresora.runner;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Registro {
	
	@JsonProperty("path")
	private String path;
	@JsonProperty("id")
	private String id;
	
	public Registro() {
		super();
	}

	public Registro(String path, String id) {
		this.path = path;
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(path, id);
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
		return Objects.equals(path, other.path) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Registro [path=" + path + ", id=" + id + "]";
	}
	
}
