package CEPTools;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name="fileUploadController")
@SessionScoped

public class FileUploadController {

    private UploadedFile file;
    private ArrayList<UploadedFile> allfiles;
    private int pmid;
    private static Logger logger = Logger.getLogger(PubMedSearch.class.getName());
    
    public FileUploadController()
    {
    	allfiles = new ArrayList<UploadedFile> ();
    	pmid = 0;
    	
    }

    public UploadedFile getFile() {
        return file;
    }
    
    public int getPmid()
    {
    	return pmid;
    }
    
    public void setPmid(int pmid)
    {
    	this.pmid = pmid;
    }
    
    public void setAllFiles(ArrayList<UploadedFile> allfiles)
    {
    	this.allfiles = allfiles;
    }
    
    public ArrayList<UploadedFile> getAllFiles()
    {
    	return allfiles;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() {
        FacesMessage msg = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void FileSave()
    {
    	
    	String currpmid  = String.valueOf(pmid);
    	String currlocation = "/opt/ceptools_data_storage/" + currpmid+"/";
    	ArrayList<org.primefaces.model.UploadedFile> thefiles = new ArrayList<org.primefaces.model.UploadedFile>();
    	
    	logger.info("Here, setup the location");
    	 File thedir = new File(currlocation);
    	 
    	if(!thedir.exists())
    	{
    	 thedir.setExecutable(true);
    	 thedir.setWritable(true);
    	 thedir.setReadable(true);
    	 
    	 thedir.mkdirs();
    		logger.info("Should see directory created as: "+ currlocation);
    	}
    	else
    	{
    		logger.info("location already exists as: "+ currlocation);
    	}
    	
    	//Now add files to it
    	 try
    	   {
    	thefiles = this.getAllFiles();
    	 logger.info("inside try block");
    	for(org.primefaces.model.UploadedFile currfile: thefiles)
    	  { 
    		logger.info("looping now on file array");
    	   String fullfilelocation = currlocation + currfile.getFileName();
    	   File myfile = new File(fullfilelocation);
    	   logger.info("File creation started...should save as: "+fullfilelocation);
    	   logger.info("Filename is: " + currfile.getFileName());
    	   myfile.setExecutable(true);
      	 myfile.setWritable(true);
      	 myfile.setReadable(true);
    	  
    	     OutputStream out = new FileOutputStream(myfile);
    	 
    	  out.close();
    	}
    	
    	logger.info("Out of the for loop, finished try block");
    	
    	}
    	catch(Exception ex)
    	{
    		logger.info(ex);
    	}
    	
    	
    }
 
  

    public void handleFileUpload(FileUploadEvent event) {
    	if(event!=null)
    	{
    	this.setFile(event.getFile());
    	this.allfiles.add(event.getFile());
		FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
    	 FileSave();
	}
}