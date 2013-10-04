package CEPTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;





public class PubList {
	
	  

	    private static Logger logger2 = Logger.getLogger(PubList.class.getName());  
        
        private List<String> fauthors;
	    private List<String> lauthors;
	    private List<String> fullnames;
	    private String title;
	    private String theabstract;
	    private String year;
	    private int pmid;
	    private int index;
	    private List<File> files;
	    private List<String> filenames;
	   

        
        public PubList()
        {
        	
        	fauthors = new ArrayList<String>();
        	lauthors = new ArrayList<String>();
        	fullnames = new ArrayList<String>();
        	files = new ArrayList<File>();
        	filenames = new ArrayList<String>();
        	title = "";
        	theabstract = "";
        	year = "";
        	index = -1;
        	pmid = 0;
        }

        
        PubList(String t, String a, String y, List<String> fa, List<String> la, List<String> fn, int pmid, int in)
        {
        	this.theabstract = a;
        	this.title = t;
        	this.year = y;
        	this.fauthors = fa;
        	this.lauthors = la;
        	this.fullnames = fn;
        	this.index = in;
        	this.pmid = pmid;
        }
        
  
        public int getIndex()
        {
        	return index;
        }
        
        public void setIndex(int i)
        {
        	this.index = i;
        }
        
      public void setFullnames(List<String> fullnames)
      {
    	  this.fullnames = fullnames;
      }
      
      public List<String> getFullnames()
      {
    	  return fullnames;
      }
        
   
	    
	   
	   public void setYear(String year )
	   {
		   this.year = year;
	   }
	   
	   public void setTitle(String title)
	   {
		   this.title = title;
	   }
	   
	   public void setAbstract(String theabstract )
	   {
		   this.theabstract = theabstract;
	   }
	   
	   public void setLauthors(List<String> lauthors )
	   {
		   this.lauthors = lauthors;
	   }
	   
	   public void setFauthors (List<String> fauthors )
	   {
		   this.fauthors  = fauthors;
	   }
	   
	   
	   public String getAbstract()
	   {
		  return theabstract; 
	   }
	   
	   public int getPmid()
	   {
		   return pmid;
	   }
	   
	   public void setPmid(int pmid)
	   {
		   this.pmid = pmid;
	   }
	
	   
	   public String getTitle()
	   {
		   return title;
	   }
	   
	   public String getYear()
	   {
		   return year;
	   }
	   
	   public List<String> getLauthors ()
	   {
		  return lauthors; 
	   }
    
      public List<String> getFauthors ()
	   {
		  return fauthors; 
	   }
	   

      public void setFilenames(List<String> filenames )
	   {
		   this.filenames = filenames;
	   }
      public void setFiles(List<File> files)
	   {
		   this.files = files;
	   }
      public List<String> getFilenames( )
	   {
		   return filenames;
	   }
      public List<File> getFiles( )
	   {
		   return files;
	   }
	   
	   
	   
	   
	   
	   
	   
        

}