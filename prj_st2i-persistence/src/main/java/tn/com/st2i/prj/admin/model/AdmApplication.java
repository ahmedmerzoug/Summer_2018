package tn.com.st2i.prj.admin.model;

/**
 * Generated by easyfaces Hibernate Tools 4.3.1
 * 
 * Postgres dataBase
 *
 */
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
@Entity
@Table(name = "adm_application")
public class AdmApplication implements java.io.Serializable, Cloneable {
	private transient static final long serialVersionUID = 1L;

	public transient static final String _nameTable = "ADM_APPLICATION";

	public transient static final String _idApp = "ID_APP";
	public transient static final String _codApp = "COD_APP";
	public transient static final String _desApp = "DES_APP";
	public transient static final String _desAppAr = "DES_APP_AR";

	@SuppressWarnings("serial")
	public static transient final Map<String, String> listType = new HashMap<String, String>() {
		{
			put(_idApp, "LONG");
		}
		{
			put(_codApp, "STRING");
		}
		{
			put(_desApp, "STRING");
		}
		{
			put(_desAppAr, "STRING");
		}
	};
				@GeneratedValue
			@Id
	@Column(name = "id_app", unique = true, nullable = false)
	private Long idApp;

	@Column(name = "cod_app", nullable = false, length = 40)
	private String codApp;

	@Column(name = "des_app", nullable = false, length = 300)
	private String desApp;

	@Column(name = "des_app_ar", nullable = false, length = 300)
	private String desAppAr;

	public AdmApplication clone() throws CloneNotSupportedException {
		return (AdmApplication) super.clone();
	}
}
