package ggigeroa.impresora.runner.model;

public enum Constants {
	NAME("Impresora");
	private String value;
	private Constants(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
