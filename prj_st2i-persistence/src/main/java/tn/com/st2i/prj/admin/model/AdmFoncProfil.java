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
import javax.persistence.UniqueConstraint;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
@Entity
@Table(name = "adm_fonc_profil", uniqueConstraints = @UniqueConstraint(columnNames = { "id_fonc", "id_profil" }))
public class AdmFoncProfil implements java.io.Serializable, Cloneable {
	private transient static final long serialVersionUID = 1L;

	public transient static final String _nameTable = "ADM_FONC_PROFIL";

	public transient static final String _idFoncProfil = "ID_FONC_PROFIL";
	public transient static final String _idFonc = "ID_FONC";
	public transient static final String _idProfil = "ID_PROFIL";
	public transient static final String _FEditer = "F_EDITER";
	public transient static final String _FValider = "F_VALIDER";

	@SuppressWarnings("serial")
	public static transient final Map<String, String> listType = new HashMap<String, String>() {
		{
			put(_idFoncProfil, "LONG");
		}
		{
			put(_idFonc, "LONG");
		}
		{
			put(_idProfil, "LONG");
		}
		{
			put(_FEditer, "INTEGER");
		}
		{
			put(_FValider, "INTEGER");
		}
	};

	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "adm_fonc_profil_id_fonc_profil_seq", name = "adm_fonc_profil_id_fonc_profil_seq")
	@GeneratedValue(generator = "adm_fonc_profil_id_fonc_profil_seq", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "id_fonc_profil", unique = true, nullable = false)
	private Long idFoncProfil;

	@Column(name = "id_fonc", nullable = false)
	private Long idFonc;

	@Column(name = "id_profil", nullable = false)
	private Long idProfil;

	@Column(name = "f_editer", nullable = false, precision = 1, scale = 0)
	private Long FEditer;

	@Column(name = "f_valider", nullable = false, precision = 1, scale = 0)
	private Long FValider;

	public AdmFoncProfil clone() throws CloneNotSupportedException {
		return (AdmFoncProfil) super.clone();
	}
}
