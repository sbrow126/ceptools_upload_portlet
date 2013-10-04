package CEPTools;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStream;  

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;  
import javax.portlet.PortletResponse;
import javax.servlet.ServletContext;  
import javax.servlet.http.HttpServletResponse;
  
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;  
import org.primefaces.model.StreamedContent;  

import com.liferay.compat.portal.util.PortalUtil;






  
@ManagedBean(name="fileDownloadController")
@SessionScoped

public class FileDownloadController {  
  
	 private StreamedContent file;
	 private static Logger logger = Logger.getLogger(PubMedSearch.class.getName());  

	    public StreamedContent getFile() {
	        return file;
	    }

	    public void setFile(StreamedContent file) {
	        this.file = file;
	    }

	    /** Creates a new instance of FileDownloadBean */
	    public FileDownloadController() {
	  
	        
	    }
	    
	   
		public void downloadSelectedFile(File thefile, String filetype) throws Exception{

			String contentType = "application/zip";
			String filename = thefile.getName();

			if(filetype.equals("zip")){
			contentType = "application/zip";
			}

			if(filetype.equals("abf")){
			contentType = "application/abf";
			}

			logger.info("Here, the contentType was set to: "+ contentType);
			
			FacesContext facesContext = (FacesContext) FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
			HttpServletResponse response = PortalUtil.getHttpServletResponse(portletResponse);

			String userFolder = thefile.getCanonicalPath();
			File file = new File(userFolder , filename);
			BufferedInputStream input = null;
			BufferedOutputStream output = null;

			try {
			input = new BufferedInputStream(new FileInputStream(file), 10240);

			response.reset();
			response.setHeader("Content-Type", contentType);
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			response.flushBuffer();
			output = new BufferedOutputStream(response.getOutputStream(), 10240);

			byte[] buffer = new byte[10240];
			int length;
			while ((length = input.read(buffer)) > 0) {
			output.write(buffer, 0, length);
			}

			output.flush();
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
			} finally {
			try {
			output.close();
			input.close();
			} catch (IOException e) {
			e.printStackTrace();
			}
			}

			facesContext.responseComplete();
			logger.info("Done. File Download should have executed");
			}
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
}  