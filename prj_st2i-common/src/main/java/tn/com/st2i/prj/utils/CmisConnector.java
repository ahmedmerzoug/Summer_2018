package tn.com.st2i.prj.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Rendition;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.api.Tree;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.PropertyData;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.CapabilityQuery;
import org.apache.chemistry.opencmis.commons.exceptions.CmisContentAlreadyExistsException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

import org.apache.chemistry.opencmis.commons.impl.dataobjects.PropertiesImpl;

import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.commons.data.PropertyData;

@Component("cmisConnector")
public class CmisConnector {

	@Value("${cmis.url}")
	private String url;
	@Getter
	@Setter
	@Value("${cmis.user}")
	private String user;
	@Getter
	@Setter
	@Value("${cmis.password}")
	private String password;

	private Session cmisSession = null;

	/**
	 * getObject
	 * 
	 * @param id
	 * @return
	 */
	public CmisObject getObjectById(String id) {
		Session cmisSession = getSession();
		return cmisSession.getObject(cmisSession.createObjectId(id));
	}

	public CmisObject getObjectByRefDoss(String refDoss, String fileName) {
		Session cmisSession = getSession();
		Folder parentFolder = (Folder) cmisSession.getObject(cmisSession.createObjectId(refDoss));
		System.out.println(cmisSession.getObjectByPath(parentFolder.getPath() + "/" + fileName));
		return cmisSession.getObjectByPath(parentFolder.getPath() + "/" + fileName);
	}

	public List<Tree<FileableCmisObject>> getList_Contentby_RefDoss(String refDoss) {
		List<Tree<FileableCmisObject>> list = new ArrayList<Tree<FileableCmisObject>>();
		try {

			Map<String, String> results = new HashMap<String, String>();

			Session cmisSession = getSession();
			Folder parentFolder = (Folder) cmisSession.getObject(cmisSession.createObjectId(refDoss));
			list = this.getDescendents(parentFolder);

		}

		catch (Exception e) {

		}
		return list;
	}

	/*
	 * public Map<String, String> getList_Contentby_RefDoss(String refDoss) {
	 * Map<String, String> results = new HashMap<String, String>();
	 * 
	 * Session cmisSession = getSession(); Folder parentFolder = (Folder)
	 * cmisSession.getObject(cmisSession.createObjectId(refDoss));
	 * List<Tree<FileableCmisObject>> list = this.getDescendents(parentFolder);
	 * 
	 * for (Tree<FileableCmisObject> f : list) { if (f instanceof Document) {
	 * System.out.println("don't do anything for the moment"); } else {
	 * results.put(f.getItem().getId(),refDoss); }
	 * System.out.println(f.getItem().getId()+ "  "+refDoss); }
	 * 
	 * return results;
	 * 
	 * }
	 * 
	 */

	public List<String> getDescedendentsIds() {
		List<String> listeFilesName = new ArrayList<>();
		Session cmisSession = getSession();

		for (CmisObject child : cmisSession.getRootFolder().getChildren()) {
			//////// System.out.println(child.getName()+ " "+child.getId());
			listeFilesName.add(child.getId());
		}
		//// System.out.println(listeFilesName);
		return listeFilesName;

	}

	public List<Tree<FileableCmisObject>> getDes() {
		Session cmisSession = getSession();
		Folder root = cmisSession.getRootFolder();
		List<Tree<FileableCmisObject>> list = new ArrayList<Tree<FileableCmisObject>>();

		return cmisSession.getRootFolder().getDescendants(2); // getFolder
																// children
	}

	public void listTopFolder() {
		Session cmisSession = getSession();
		List<String> listeFilesName = new ArrayList<>();
		Folder root = cmisSession.getRootFolder();
		ItemIterable<CmisObject> contentItems = root.getChildren();
		for (CmisObject contentItem : contentItems) {
			if (contentItem instanceof Document) {
				Document docMetadata = (Document) contentItem;
				ContentStream docContent = docMetadata.getContentStream();
				System.out.println(docMetadata.getName() + " [size=" + docContent.getLength() + "][Mimetype="
						+ docContent.getMimeType() + "][type=" + docMetadata.getType().getDisplayName() + "]");
			} else {
				System.out.println(contentItem.getName() + " [type=" + contentItem.getType().getDisplayName() + "]");
			}
		}
	}

	/**
	 * getObjectByPath
	 * 
	 * @param objectPath
	 * @return
	 */
	public CmisObject getObjectByPath(String objectPath) {
		Session cmisSession = getSession();
		System.out.println("hi man" + cmisSession.getObjectByPath(objectPath));
		return cmisSession.getObjectByPath(objectPath);
	}

	public List<CmisObject> getQueryResults() {
		Session session = getSession();
		List<CmisObject> objList = new ArrayList<CmisObject>();

		// execute query
		ItemIterable<QueryResult> results = session.query("SELECT * FROM cmis:folder", false);
		for (QueryResult qResult : results) {
			String objectId = "";
			PropertyData<?> propData = qResult.getPropertyById("cmis:objectId"); // Atom
																					// Pub
																					// binding
			if (propData != null) {
				objectId = (String) propData.getFirstValue();
			} else {
				objectId = qResult.getPropertyValueByQueryName("d.cmis:objectId"); // Web
																					// Services
																					// binding
			}
			CmisObject obj = session.getObject(session.createObjectId(objectId));
			objList.add(obj);
		}
		System.out.println(objList);
		return objList;
	}

	public Object see() {
		Session cmisSession = getSession();

		OperationContext context = cmisSession.createOperationContext();
		Map<String, String> re = new HashMap<String, String>();
		ItemIterable<QueryResult> results = cmisSession.query("SELECT * FROM cmis:folder Join cmis:document", false,
				context);
		Object value = new Object();

		for (QueryResult hit : results) {
			for (PropertyData<?> property : hit.getProperties()) {

				String queryName = property.getQueryName();
				value = (property.getFirstValue());

				System.out.println(value + "  " + queryName);
			}
			System.out.println(value);
		}

		return value;
	}

	/**
	 * createFolder
	 * 
	 * @param parentFolder
	 * @param folderName
	 * @param props
	 * @return
	 */
	public Folder createFolder(Folder parentFolder, String attachementName, Map<String, Object> props) {
		Session cmisSession = getSession();

		System.out.println("cmis:createFolder:::::::::::::" + attachementName);
		Folder subFolder = null;
		try {
			String parentPath = parentFolder.isRootFolder() ? "" : parentFolder.getPath();
			subFolder = (Folder) cmisSession.getObjectByPath(parentPath + "/" + attachementName);
			System.out.println("Folder already existed!");
		} catch (CmisObjectNotFoundException onfe) {
			// create a map of properties if one wasn't passed in
			if (props == null) {
				props = new HashMap<String, Object>();
			}

			// Add the object type ID if it wasn't already
			if (props.get("cmis:objectTypeId") == null) {
				props.put("cmis:objectTypeId", "cmis:folder");
			}

			// Add the name if it wasn't already
			if (props.get("cmis:name") == null) {
				props.put("cmis:name", attachementName);
			}

			subFolder = parentFolder.createFolder(props);
			String subFolderId = subFolder.getId();
			System.out.println("Created new folder: " + subFolderId);
		}

		return subFolder;
	}

	/**
	 * createFolder
	 * 
	 * @param folderPath
	 * @param folderName
	 * @param props
	 * @return
	 */
	public Folder createFolder(String[] folderPath, String attachementName, Map<String, Object> props) {
		Folder parentFolder = (Folder) getObjectByPath("/");
		for (String pathPart : folderPath) {
			parentFolder = createFolder(parentFolder, pathPart, null);
		}

		return createFolder(parentFolder, attachementName, props);
	}

	/**
	 * createFolder
	 * 
	 * @param parentPath
	 * @param folderName
	 * @param props
	 * @return
	 */
	public Folder createFolder(String parentPath, String folderName, Map<String, Object> props) {
		Folder parentFolder = (Folder) getObjectByPath(parentPath);

		return createFolder(parentFolder, folderName, props);
	}

	public void print() {
		System.out.println("hhh");
		System.out.println(url);
		System.out.println(user);
	}

	/**
	 * createFolder
	 * 
	 * @param folderName
	 * @param props
	 * @return
	 */
	public Folder createFolder(String attachementName, Map<String, Object> props) {
		Calendar calendar = Calendar.getInstance();
		ArrayList<String> path = new ArrayList<String>();
		path.add(props.get("Type_jurid").toString());
		path.add(props.get("Jurid").toString());
		path.add(Integer.toString(calendar.get(Calendar.YEAR)));
		path.add(props.get("ref").toString());
		props.remove("Type_jurid");
		props.remove("Jurid");
		props.remove("ref");
		int n = path.size();
		String[] parentPath = new String[n];
		for (int i = 0; i < n; i++) {
			parentPath[i] = path.get(i);
		}
		return createFolder(parentPath, attachementName, props);
	}

	/**
	 * getSession
	 * 
	 * @return
	 */

	////// F1
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Session getSession() {
		if (cmisSession == null) {
			SessionFactory factory = SessionFactoryImpl.newInstance();
			Map parameter = new HashMap();

			// connection settings
			/*
			 * url = "http://localhost:8180/alfresco/cmisatom"; user = "admin";
			 * password = "admin";
			 */
			parameter.put(SessionParameter.ATOMPUB_URL, url);
			parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
			parameter.put(SessionParameter.AUTH_HTTP_BASIC, "true");
			parameter.put(SessionParameter.USER, user);
			parameter.put(SessionParameter.PASSWORD, password);

			// Set the alfresco object factory
			// Used when using the CMIS extension for Alfresco for working with
			// aspects
			parameter.put(SessionParameter.OBJECT_FACTORY_CLASS,
					"org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");

			List<Repository> repositories = factory.getRepositories(parameter);

			cmisSession = repositories.get(0).createSession();
		}

		return cmisSession;
	}

	/**
	 * getFolderTree
	 * 
	 * @param id
	 * @return
	 */

	////////// F2
	public List<Tree<FileableCmisObject>> getFolderTree(Folder parentFolder) {
		Session cmisSession = getSession();
		if (parentFolder == null)
			parentFolder = cmisSession.getRootFolder();
		System.out.println(parentFolder);
		return parentFolder.getFolderTree(-1); // getFolderTree
	}

	/**
	 * getDescendents Tree
	 * 
	 * @param id
	 * @return
	 * @return
	 */
	public List<Tree<FileableCmisObject>> getDescendents(Folder parentFolder) {
		Session cmisSession = getSession();
		if (parentFolder == null)
			parentFolder = cmisSession.getRootFolder();
		//// System.out.println(parentFolder.getDescendants(1));
		return parentFolder.getDescendants(2); // getFolder children
	}

	public List<Tree<FileableCmisObject>> getDescedddndents() {
		Session cmisSession = getSession();
		List<Tree<FileableCmisObject>> list = new ArrayList<Tree<FileableCmisObject>>();

		return cmisSession.getRootFolder().getDescendants(2); // getFolder
																// children
	}

	public Folder getRootFolder() {
		Session cmisSession = getSession();
		cmisSession.getRootFolder().getName();

		return cmisSession.getRootFolder();

	}

	public OperationContext createOperationContext() {
		Session cmisSession = getSession();

		return cmisSession.createOperationContext();

	}

	public List<CmisObject> getPagingChildren(Folder parentFolder, int startpage, int numitmesperpage) {
		Session cmisSession = getSession();
		List<CmisObject> list = null;
		if (parentFolder == null)
			parentFolder = cmisSession.getRootFolder();
		ItemIterable<CmisObject> children = parentFolder.getChildren();
		ItemIterable<CmisObject> page = children.skipTo(numitmesperpage * startpage).getPage();
		if (page != null) {
			Iterator<CmisObject> pageItems = page.iterator();

			list = new ArrayList<CmisObject>();
			while (pageItems.hasNext()) {
				CmisObject item = pageItems.next();
				list.add(item);
			}
		}
		return list;
	}

	/// test
	public void testRendition(String nodeRef) {
		Session s = getSession();
		OperationContext context = s.createOperationContext();
		// context.setRenditionFilterString("cmis:thumbnail");
		context.setRenditionFilterString("*");
		// CmisObject doc =
		// s.getObject("workspace://SpacesStore/acf2b517-f7be-4339-aa99-7178e589baa4;1.0",
		// context);
		CmisObject doc = s.getObject(nodeRef, context);
		// CmisObject doc =
		// context.getObject("workspace://SpacesStore/acf2b517-f7be-4339-aa99-7178e589baa4;1.0");
		List<Rendition> renditions = doc.getRenditions();
		for (Rendition rendition : renditions) {
			System.out.println("kind: " + rendition.getKind());
			System.out.println("mimetype: " + rendition.getMimeType());
			System.out.println("width: " + rendition.getWidth());
			System.out.println("height: " + rendition.getHeight());
			System.out.println("stream id: " + rendition.getStreamId());
			System.out.println("url: " + rendition.getContentUrl());
		}
		System.out.println(renditions);
	}

	/**
	 * doQuery
	 * 
	 * @param cql
	 * @param limit
	 * @param offset
	 * @return
	 */
	public void doQuery(String cql, int maxItems) {
		Session cmisSession = getSession();

		OperationContext oc = new OperationContextImpl();
		oc.setMaxItemsPerPage(maxItems);

		ItemIterable<QueryResult> results = cmisSession.query(cql, false, oc);

		for (QueryResult result : results) {
			for (PropertyData<?> prop : result.getProperties()) {
				System.out.println(prop.getQueryName() + ": " + prop.getFirstValue());
			}
			System.out.println("--------------------------------------");
		}

		System.out.println("--------------------------------------");
		System.out.println("Total number: " + results.getTotalNumItems());
		System.out.println("Has more: " + results.getHasMoreItems());
		System.out.println("--------------------------------------");
	}

	public void doExample() {
		doQuery("SELECT cmis:objectId,cmis:name,cmis:lastModifiedBy,cmis:lastModificationDate,cmis:baseTypeId FROM cmis:folder",
				5000);
		//////// System.out.println(doQuery("select * from cmis:document where
		//////// cmis:name='Data Dictionary'"));
	}

	/*
	 * public void searchMetadataAndFTS(Session session) { // Check if the repo
	 * supports Metadata search and Full Text Search (FTS) RepositoryInfo
	 * repoInfo = session.getRepositoryInfo(); if
	 * (repoInfo.getCapabilities().getQueryCapability().equals(CapabilityQuery.
	 * METADATAONLY)) { logger.warn("Repository does not support FTS [repoName="
	 * + repoInfo.getProductName() + "][repoVersion=" +
	 * repoInfo.getProductVersion() + "]"); } else { String query =
	 * "SELECT * FROM cmis:document WHERE cmis:name LIKE 'OpenCMIS%'";
	 * ItemIterable<QueryResult> searchResult = session.query(query, false);
	 * logSearchResult(query, searchResult);
	 * 
	 * query =
	 * "SELECT * FROM cmis:document WHERE cmis:name LIKE 'OpenCMIS%' AND CONTAINS('testing')"
	 * ; searchResult = session.query(query, false); logSearchResult(query,
	 * searchResult); } } private void logSearchResult(String query,
	 * ItemIterable<QueryResult> searchResult) {
	 * logger.info("Results from query " + query); int i = 1; for (QueryResult
	 * resultRow : searchResult) {
	 * logger.info("--------------------------------------------\n" + i + " , "
	 * + resultRow.getPropertyByQueryName("cmis:objectId").getFirstValue() +
	 * " , " +
	 * resultRow.getPropertyByQueryName("cmis:objectTypeId").getFirstValue() +
	 * " , " + resultRow.getPropertyByQueryName("cmis:name").getFirstValue());
	 * i++; } }
	 */
	/*
	 * public Folder getFolderByPath(String folderPath) throws Exception {
	 * Session cmisSession = getSession();
	 * 
	 * try { CmisObject object = cmisSession.getObjectByPath(folderPath); Folder
	 * folder = (Folder) object;
	 * 
	 * return new Folder(this, folder); } catch (Exception e) {
	 * 
	 * throw e; // or throw as normal } catch (Exception e) { throw new
	 * Exception(e); } }
	 */
	/**
	 * Utility method for reading a file's content.
	 */
	private byte[] readFile(File file) throws IOException {

		byte[] fileContent = new byte[(int) file.length()];
		FileInputStream istream = new FileInputStream(file);
		istream.read(fileContent);
		istream.close();
		return fileContent;
	}

	/////////////////////////////////// upload part
	/////////////////////////////////// /////////////////////////////////////////////////////
	/**
	 * addLink
	 * 
	 * @param uploadedDocument
	 * @param parentFolderForShortcut
	 * @param props
	 * @return
	 */
	////// F3
	public String addLink(Document uploadedDocument, Folder parentFolderForShortcut, Map<String, Object> props) {
		String link = null;
		Session cmisSession = getSession();
		// create a map of properties if one wasn't passed in
		if (props == null) {
			props = new HashMap<String, Object>();
			props.put(PropertyIds.BASE_TYPE_ID, BaseTypeId.CMIS_ITEM.value());
			// Define a name and description for the link
			props.put(PropertyIds.NAME, "lienDu_" + uploadedDocument.getName());
			props.put("cmis:description", uploadedDocument.getDescription());
			props.put(PropertyIds.OBJECT_TYPE_ID, "I:app:filelink");

			// Define the destination node reference
			String[] nodeRef = uploadedDocument.getId().split(";");
			props.put("cm:destination", "workspace://SpacesStore/" + nodeRef[0]);
		}
		link = cmisSession.createItem(props, parentFolderForShortcut).toString();

		return link;
	}

	/**
	 * uploadFile
	 * 
	 * @param parentFolder
	 * @param fileName
	 * @param inputStream
	 * @param size
	 * @param mimetype
	 * @param props
	 * @return
	 */

	//////// F4
	public Document createDocument(Folder parentFolder, String fileName, InputStream inputStream, long size,
			String mimetype, Map<String, Object> props) {
		System.out.println("jawwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
		Session cmisSession = getSession();

		// create a map of properties if one wasn't passed in
		if (props == null) {
			props = new HashMap<String, Object>();
		}

		// Add the object type ID if it wasn't already
		if (props.get("cmis:objectTypeId") == null) {
			props.put("cmis:objectTypeId", "cmis:document");
		}

		// Add the name if it wasn't already
		if (props.get("cmis:name") == null) {
			props.put("cmis:name", fileName);
		}

		// set contentStream object
		ContentStream contentStream = cmisSession.getObjectFactory().createContentStream(fileName, size, mimetype,
				inputStream);

		// process the upload
		Document document = null;
		try {
			document = parentFolder.createDocument(props, contentStream, null);
			System.out.println("Created new document: " + document.getId());
		} catch (CmisContentAlreadyExistsException ccaee) {
			document = (Document) cmisSession.getObjectByPath(parentFolder.getPath() + "/" + fileName);
			System.out.println("Document already exists: " + fileName);
		}

		return document;
	}

}
