package tn.com.st2i.prj.controller.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.Tree;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.impl.Base64.InputStream;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import com.easyfaces.controller.tools.UiAbstractGen;

import groovy.util.Node;
import lombok.Getter;
import lombok.Setter;
import tn.com.st2i.prj.admin.model.AdmFonc;
import tn.com.st2i.prj.admin.model.CmisEntity;
import tn.com.st2i.prj.utils.CmisConnector;

@SuppressWarnings("serial")
@Controller("cMISBean")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CMISBean extends UiAbstractGen {

	public CMISBean() throws Exception {
		super("uiViewAdmin:listUsers");
	}

	private final static String NAVIGATION_NAME = "/pages/admin/utility/NewFile.xhtml";

	@Autowired()
	@Qualifier("cmisConnector")
	private CmisConnector cmisConnector;

	@Getter
	@Setter
	private String url;

	@Getter
	@Setter
	private String user;

	@Getter
	@Setter
	private String password;

	@Getter
	@Setter
	List<String> listeFilesName = new ArrayList<>();


	@Getter
	@Setter
	List<String> listeFilesName2 = new ArrayList<>();
	@Getter
	@Setter
	List<String> listTreeNodes = new ArrayList<>();

	@Getter
	@Setter
	private TreeNode root;
	
	@Getter
	@Setter
	private TreeNode arbre;

	@Getter
	@Setter
	private List<Tree<FileableCmisObject>> nodeList;
	@Getter
	@Setter
	private static List<Tree<FileableCmisObject>> araListe;
	@Getter
	@Setter
	private List<Tree<FileableCmisObject>> subList;
	@Getter
	@Setter
	private List<Tree<FileableCmisObject>> workplease;
	
	@Getter
	@Setter  
	 private CmisEntity selectCMIS;
	String resultTree = "";

	
	@Override
	protected void postConstructMethod() throws Exception {
		setNavigationName(NAVIGATION_NAME);

		//root = new DefaultTreeNode("Root", null);
		nodeList = cmisConnector.getDescedddndents();
		root=new DefaultTreeNode("Root",null);
		arbre = new DefaultTreeNode("/", root);
		
		
		
		recursiveDirectoryPrint(nodeList, 0, arbre);
		System.out.println("-------------------------------------------------------------------------");

		
		System.out.println("-------------------------------------------------------------------------");
		
		
		System.out.println("name  "+resultTree);
		//nodeList = cmisConnector.getDescedddndents();

		/*arbre = new DefaultTreeNode("", root);
		// printRecursive(root);
		recursive(nodeList, "0", arbre);*/

	}
	
	public String Return_Row_Number(String ID)
	{
		int i =0;	
		workplease=	cmisConnector.getList_Contentby_RefDoss(ID);
		
		
		
		for (Tree<FileableCmisObject> f :workplease)
		{
			String Type=f.getItem().getType().getId().toString();
			
			String S="cmis:document";
			if (Type.equals(S))
			{
			i++;
			}
		}
			return "12";
		
	}
	public List<String> Return_Document_Name(String ID)
	{ 
		List <String> dd = new ArrayList<>();
	
		int i =0;	
			workplease=	cmisConnector.getList_Contentby_RefDoss(ID);
		for (Tree<FileableCmisObject> f :workplease)
		{
			String Type=f.getItem().getType().getId().toString();
			System.out.println("type is    "+Type);
			String S="cmis:document";
			if (Type.equals(S))
			{
			i++;
			
			dd.add(f.getItem().getName().toString());
		}
		}
		System.out.println("ffffff "+i);
		//////////System.out.println(dd.add(name.getName()+"ssss"));
		////System.out.println("name"+name.getName());
		return dd;
	
		
	}
	
	
	public List<CmisEntity> create_CmisEntity(String ID)
	{ List<CmisEntity> xx = new ArrayList<>();
		try{
		
	String nn=null;
		workplease=	cmisConnector.getList_Contentby_RefDoss(ID);
		for (Tree<FileableCmisObject> f :workplease)
		{
			String Type=f.getItem().getType().getId().toString();
			System.out.println("type is    "+Type);
			String S="cmis:document";
			if (Type.equals(S))
			{
			
				CmisEntity capitaine = new CmisEntity();
				capitaine.setIdab(f.getItem().getId().toString());
				capitaine.setName(f.getItem().getName().toString());
				capitaine.setOwner(f.getItem().getCreatedBy().toString());
				
				
			    capitaine.setDescription((String) f.getItem().getProperty("cmis:description").getFirstValue());
				capitaine.setModificationdate(f.getItem().getLastModificationDate().getTime().toString());
				capitaine.setCreationdate(f.getItem().getCreationDate().getTime().toString());
				capitaine.setDisplayname((String) f.getItem().getProperty("cm:title").getFirstValue());
			
				xx.add(capitaine);
		}
		}
	}
	catch(Exception e){
		
	}
		
		//////////System.out.println(dd.add(name.getName()+"ssss"));
		////System.out.println("name"+name.getName());
		return xx;
		
		
		
	
		
	}
	
	public List<Tree<FileableCmisObject>> Return(String ID)
	{ 
		
		
			workplease=	cmisConnector.getList_Contentby_RefDoss(ID);
		for (Tree<FileableCmisObject> f :workplease)
		{
			String Type=f.getItem().getType().getId().toString();
			System.out.println("type is    "+Type);
			String S="cmis:document";
			if (Type.equals(S))
			{
			
				return workplease;
			
		}
		}
		
		//////////System.out.println(dd.add(name.getName()+"ssss"));
		////System.out.println("name"+name.getName());
		return workplease;
	
		
	}
	public String Return_Folder_Name(String ID)
	{ 
		
		if (ID.length()>2){
		CmisObject name = cmisConnector.getObjectById(ID);
		
		//////////System.out.println(dd.add(name.getName()+"ssss"));
		////System.out.println("name"+name.getName());
		return name.getName().toString();
	}
	else return "Root";
		
	}
	
	public String fixIcon(String ID) {
		String Type="";
		String name="";
		String result="";
		String result2="";
		try
		{
		CmisObject Object_Type = cmisConnector.getObjectById(ID);
		Type=Object_Type.getType().getId().toString();
		name=Object_Type.getName().toString();
		result = name.substring(name.length() -4, name.length());
		result2 = name.substring(name.length() -3, name.length());
		}
		catch(Exception e){
			
		}
			String S="cmis:document";
			
		    if ((Type.equals(S))&&(ID.length()>2)&&(result.equals("pptx")))
			{
		
			return "fa fa-file-powerpoint-o powerpoint";  ////or : collapsed
			}
			
			else if ((Type.equals(S))&&(ID.length()>2)&&(result2.equals("pdf")))
			{
		
			return "fa fa-file-pdf-o";  ////or : collapsed  "fa fa-file-pdf-o";
			}
			else if ((Type.equals(S))&&(ID.length()>2)&&(result2.equals("txt")))
			{
		
			return "fa fa-file-text";  ////or : collapsed  "fa fa-file-pdf-o";
			}
			else if ((Type.equals(S))&&(ID.length()>2)&&((result2.equals("jpg")||(result2.equals("png")||(result2.equals("JPG"))))))
			{
		
			return "fa fa-file-picture-o";  ////or : collapsed  "fa fa-file-pdf-o";
			}
			else if ((Type.equals(S))&&(ID.length()>2)&&(result.equals("docx")))
			{
		
			return "fa fa-file-word-o word";  ////or : collapsed  "fa fa-file-pdf-o";
			}
			else if ((Type.equals(S))&&(ID.length()>2)&&(result.equals("java")))
			{
		
			return "ui-icon-suppss";  ////or : collapsed  "fa fa-file-pdf-o";
			}
			else if ((Type.equals(S))&&(ID.length()>2))
			{
		
			return "fa fa-file-o file";  ////or : collapsed
			}
			else	return "fa fa-folder-open colorOpen";
		
	}
	

	public void recursiveDirectoryPrint(List<Tree<FileableCmisObject>> nodeList, int level , TreeNode node){
		
		for (  Tree<FileableCmisObject> d : nodeList){
		   
		    String UserHome_UserName = "[/User Homes/"+cmisConnector.getUser();
			String userHome = "[/User Homes]";
			
		
			
			String path = d.getItem().getPaths().toString();
			
			if (cmisConnector.getUser().equals("admin"))
			{
				String Type=d.getItem().getType().getId().toString();
				
				String S="cmis:folder";
				if (Type.equals(S))
				{
			nodeList = cmisConnector.getList_Contentby_RefDoss(d.getItem().getId());
	
			
			TreeNode childNode=new DefaultTreeNode(d.getItem().getId().toString(), node);

		   
			recursiveDirectoryPrint(nodeList , level+1, childNode );
				}
			}
			else if ((path.contains(userHome)) ||  (path.contains(UserHome_UserName)))
			{
				
				String Type=d.getItem().getType().getId().toString();
				System.out.println("type is    "+Type);
				String S="cmis:folder";
				if (Type.equals(S))
				{
				nodeList = cmisConnector.getList_Contentby_RefDoss(d.getItem().getId());
				
			
				TreeNode childNode=new DefaultTreeNode(d.getItem().getId().toString(), node);

			   
				recursiveDirectoryPrint(nodeList , level+1, childNode );
				}
			}
		
		}

}
	
	
    public void onNodeSelect(NodeSelectEvent event) {  
    	 String name =  (event.getTreeNode().getData().toString());
         String[] parts = name.split(",");
        //// for(String w:parts){  
      	   
      	   System.out.println("-----------//////////-----------");
      	 workplease = cmisConnector.getList_Contentby_RefDoss(name);
      	for (  Tree<FileableCmisObject> d : workplease){
      		String Type=d.getItem().getType().getId().toString();
			System.out.println("type is    "+Type);
			String S="cmis:document";
			if (Type.equals(S)&&(d instanceof Document) )
			{
				
				ContentStream contentStream = ((Document) d).getContentStream();
				InputStream stream = (InputStream) contentStream.getStream();
				System.out.println("hhh"+stream);
				
			}
      	////}
      	
      	   }  
           
           
     
         // ignore  
    
    }  

    
    
    public void onNodeExpand(NodeExpandEvent event) {  
    	try{
    	   System.out.println("---------------------------------------------");
           System.out.println("/////////////////////////////////////////////////1111");
    	////////System.out.println(event.getTreeNode());
           String name =  (event.getTreeNode().getChildren().toString());
           String[] parts = name.split(",");
           for(String w:parts){  
        	   System.out.println(w); 
        	   System.out.println("-----------//////////-----------");
        	   }  

    
    	   System.out.println("---------------------------------------------");
           System.out.println("/////////////////////////////////////////////////2222");
           
    	}
    	
     catch (Exception e) {  
        // ignore  
    }  
      
    }
	
	
	




	public List afficherRoot() {
		List<String> listeFilesName = new ArrayList<>(cmisConnector.getDescedendentsIds());
		////// cmisConnector.getList_folders();
		return listeFilesName;
	}

	@Override
	protected String addRow() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void deleteRow() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected String editRow() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void init() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initSearch() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateListComboRech() throws Exception {
		// TODO Auto-generated method stub

	}

}