package tn.com.st2i.prj.admin.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "cmis_entity")
public class CmisEntity implements java.io.Serializable, Cloneable{
	public transient static final String _nameTable = "CMIS_ENTITY";
	
	private transient static final long serialVersionUID = 1L;

	public transient static String _idab = "ID_AB";
	public transient static  String _name = "NAME";
	public transient static  String _owner = "OWNER";
	public transient static String _description = "DESCRIPTION";
	public transient static  String _creationdate = "CREATIONDATE";
	public transient static  String _modificationdate = "MODIFICATIONDATE";
	public transient static  String _displayname = "DISPLAYNAME";
	
	
	@SuppressWarnings("serial")
	 public CmisEntity(String _idab, String _name, String _owner) {
	        super();
	        this._idab = _idab;
	        this._name = _name;
	        this._owner = _owner;
	    }

	public CmisEntity() {
		// TODO Auto-generated constructor stub
	}
	@SuppressWarnings("serial")
	public static transient final Map<String, String> listType = new HashMap<String, String>() {
		{
			put(_idab, "String");
		}
		{
			put(_name, "String");
		}
		{
			put(_owner, "String");
		}
		{
			put(_description, "String");
		}
		{
			put(_creationdate, "String");
		}
		{
			put(_modificationdate, "String");
		}	
		{
			put(_displayname, "String");
		}
	};
	
	
	
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_CMIS_ID", name = "cmis_id_entity_seq")
	@GeneratedValue(generator = "cmis_id_entity_seq", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "id_ab", unique = true,  nullable = false,length = 40)
	private String idab;

	@Column(name = "name", nullable = true,length = 40)
	private String name;

	@Column(name = "owner", nullable = true,length = 40)
	private String owner;

	@Column(name = "description", nullable = true,length = 70)
	private String description;

	@Column(name = "creationdate", nullable = true,length = 40)
	private String creationdate;
	
	@Column(name = "modificationdate", nullable = true,length = 40)
	private String modificationdate;

	@Column(name = "displayname", nullable = true,length = 40)
	private String displayname;
	
	
	
	
	public CmisEntity clone() throws CloneNotSupportedException {
		return (CmisEntity) super.clone();
	}
	
	
}
