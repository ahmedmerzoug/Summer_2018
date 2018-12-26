package tn.com.st2i.prj.exceptions;

@SuppressWarnings("serial")
public class ValResourceBundleException extends Exception {

	private String valResource;

	public ValResourceBundleException(String valResource) {
		this.valResource = valResource;
	}

	public String toString() {
		return "La valeur " + this.valResource
				+ " est introuvable dans les fichiers resources bundles";
	}
}
