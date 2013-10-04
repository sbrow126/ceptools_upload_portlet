package CEPTools;



import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import org.primefaces.component.datatable.DataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.primefaces.component.orderlist.OrderList;
import org.primefaces.event.FlowEvent;

import com.liferay.faces.bridge.model.UploadedFile;
import com.sun.faces.component.visit.FullVisitContext;




@ManagedBean(name="pubMedSearch")
@SessionScoped

public class PubMedSearch {

	  
	
	 private List<PubList> publications;
	 
	    private String pSearcher;
	    
	    private String searcher;
	    private String url;
	    private String base;
	    
	    public int counter;
	    private String buttonvalue;
	    
	    private  String userauthor;  
		 
	    private String usertitle;
	    
	    private String userpmid; 
	    private int selectedindex;
	    private PubList selectedPub;
	    private FileUploadController fchooser;
	    private FileDownloadController fdownload;
	    private File selecteddownloadfile;
	    
	    private List<File> files;
	    private List<String> filenames;
	    private List<FileStorer> allfiles;
	    private FileStorer selectedfile;
	    private int selectedpubpmid; //specifically for filechooser for storage
	    private ZipDirectory zip;
	    private int index;
	    
	    public int getIndex()
        {
        	return index;
        }
        
        public void setIndex(int i)
        {
        	this.index = i;
        }
        
	    public List<FileStorer> getAllfiles()
	    {
	    	return allfiles;
	    }
	    
	    public void setAllfiles(List<FileStorer> allfiles)
	    {
	    	this.allfiles = allfiles;
	    }
	    
	    public PubList getSelectedPub()
	    {
	    	return this.selectedPub;
	    }
	    
	    public void setSelectedPub(PubList spub)
	    {
	    	this.selectedPub = spub;
	    }
	    
	    public List<PubList> getPublications()
	    {
	    	return publications;
	    }
	    
	    public void setPublications(List<PubList> publist)
	    {
	    	this.publications = publist;
	    }
	    
	    
	    public  String getUserauthor()
        {
        	return userauthor;
        }
        
        public  String getUsertitle()
        {
        	return usertitle;
        }
        public  String getUserpmid()
        {
        	return userpmid;
        }
        
        public  void setUserauthor(String u)
        {
        	userauthor = u;
        }
        
        public  void setUsertitle(String u)
        {
        	usertitle = u;
        }
        
        public  void setUserpmid(String u)
        {
        	userpmid = u;
        }
	   
        public String setButtonvalue ()
        {
        	return buttonvalue;
        }
        
        public void getButtonvalue(String b)
        {
        	this.buttonvalue = b;
        }
	  
	    
	    private static Logger logger = Logger.getLogger(PubMedSearch.class.getName());  
	  
	   
	    
	   public PubMedSearch()
	    {
	    	 counter = 0;
	    	 searcher = "";
	    	 buttonvalue = "";
	    	 userauthor = usertitle = userpmid = url = searcher = pSearcher = "";
	    	 base = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/";
	    	 publications = new ArrayList<PubList>();
	    	 selectedindex = -1;
	    	 selectedPub = new PubList();
	    	 fchooser = new FileUploadController();
	    	 files = new ArrayList<File>();
	    	 filenames = new ArrayList<String>();
	    	 allfiles = new ArrayList<FileStorer>();
	    	 selectedfile = new FileStorer();
	    	 zip = new ZipDirectory();
	    	 fdownload = new FileDownloadController();
	    	 selecteddownloadfile = new File("");
	    	 index = 0;
	    }
	
	   public void downloadPrep()
	    {
		   logger.info("Made it this far!");
			if(selecteddownloadfile.exists()){
				
				String filetype = selecteddownloadfile.getName().substring(selecteddownloadfile.getName().length()-4, selecteddownloadfile.getName().length()-1);
				logger.info("The downloadprep has fired, and the filetype is: " + filetype);
				
				try
				{
				fdownload.downloadSelectedFile(selecteddownloadfile, filetype);
				}
				catch (Exception ex)
				{
					
				}
				
				}
				
	    }
	    
	   public void setSelecteddownloadfile(File thefile)
	   {
		   this.selecteddownloadfile = thefile;
	   }
	   
	   public File getSelecteddownloadfile()
	   {
		   return selecteddownloadfile;
	   }
	   
	   public void setFdownload(FileDownloadController fdownload)
	   {
		   this.fdownload = fdownload;
	   }
	   
	   
	   public FileDownloadController getFdownload()
	   {
		   return fdownload;
	   }
	   
	   public void setSelectedfile(FileStorer thefile)
	   {
		   selectedfile = thefile;
	   }
	   
	   public FileStorer getSelectedfile()
	   {
		   return selectedfile;
	   }
	   
	   public FileUploadController getFchooser()
	    {
	    	return fchooser;
	    }
	    
	    public void setFchooser( FileUploadController fchooser)
	    {
	    	this.fchooser =  fchooser;
	    }
	   
	      
	    public String editPSearcher(String pSearcher)
	    {
	    	this.pSearcher = pSearcher;
	    	return pSearcher;
	    }
	    
	    public void setPSearcher(String pSearcher)
	    {
	    	this.pSearcher = pSearcher;
	    }
	 
	    public String getPSearcher()
	    {
	    	return pSearcher;
	    }
	    
	    public String getSearcher()
	    {
	    	return searcher;
	    }
    
	    public void setSearcher(String searcher)
	    {
	    	this.searcher = searcher;
	    }
	    
	    public void save(ActionEvent actionEvent) {  
	        //Persist user  
	          
	        FacesMessage msg = new FacesMessage("Successful", "Welcome " );  
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
	    }  
	      
	   public boolean checkSubmit()
	   {
		   if((this.getUserauthor().length() > 0)|| (this.getUsertitle().length() > 0 )|| (this.getUserpmid().length() > 0))
		   {
			   return true; 
		   }
		   else
		   {
			   return false;
		   }
	   }
	      
	    public String onFlowProcess(FlowEvent event) {  
	        logger.info("Current wizard step:" + event.getOldStep());  
	        
	        
	       if(event.getOldStep().equalsIgnoreCase("searchselect") )
	       {
			      if(searcher.equalsIgnoreCase("author"))
			      {  
			    	  
			    	return "searchauthor";  
			    	
			    	
			      }
			      else if(searcher.equalsIgnoreCase("title"))
			      {
			    	  return "searchtitle";  
			      }
			      else if(searcher.equalsIgnoreCase("pmid"))
			      {
			    	  return "searchpmid";  
			      }
			      else
			      {
			    	  return "searchpmid";
			      }
	       }
	       if((event.getOldStep().equalsIgnoreCase("searchauthor") ||event.getOldStep().equalsIgnoreCase("searchtitle") || event.getOldStep().equalsIgnoreCase("searchpmid"))
	    		    && ! checkSubmit()){
	    	   return "searchselect";
	       }
	      
	       if(event.getOldStep().equalsIgnoreCase("searchauthor") && checkSubmit())
	       {
	    	   try{
	    	   processUrl(); 
	    	   }
	    	   catch(Exception ex)
	    	   {
	    		   logger.info(ex);
	    	   }
	    	   return "resultsdisplay";
	       }
	       if(event.getOldStep().equalsIgnoreCase("searchtitle") && checkSubmit())
	       {
	    	   logger.info("Makin it here now!");
	    	   try{
		    	   processUrl(); 
		    	   }
		    	   catch(Exception ex)
		    	   {
		    		   
		    	   }
	    	   return "resultsdisplay";
	       }
	       if(event.getOldStep().equalsIgnoreCase("searchpmid") && checkSubmit())
	       {
	    	   try{
		    	   processUrl(); 
		    	   }
		    	   catch(Exception ex)
		    	   {
		    		   
		    	   }
	    	   return "resultsdisplay";
	       }
	       if(event.getOldStep().equalsIgnoreCase("resultsdisplay"))
	       {
	    	   testPub();
	    	
	    	   return "confirm";
	       }
	       if(event.getOldStep().equalsIgnoreCase("confirm"))
	       {
	    	   setFchooservar();
	    	   sendtoSolr();
	    	   
	    	   return "upload";
	       }
	       if(event.getOldStep().equalsIgnoreCase("upload"))
	       {
	    	   
	    	   getStoredFiles();
	    	   return "display";
	       }
	       else
	       {
	    	   return "searchselect";
	       }
	      
	    	    
	    
	    
}
	    
public List<File> getFiles()
{
	return files;
}
	    
public void setFiles(List<File> files)
{
	this.files = files;
}
	    
	    public void setFchooservar()
	    {
	    	selectedpubpmid = this.selectedPub.getPmid();
	    	fchooser.setPmid(selectedpubpmid);
	        logger.info("The pmid should be saved to fchooser as: " + fchooser.getPmid());
	    }
	    
	    public void testPub()
	    {
	    	PubList thispub = this.selectedPub;
	    	logger.info("The selected Publication was: ");
	    	logger.info("Title: "+thispub.getTitle());
	    	logger.info("Authors: "+thispub.getFullnames());
	    	logger.info("Abstracts: "+thispub.getAbstract());
	    	logger.info("Year: "+thispub.getYear());
	    	
	    	
	    }
	    
	    public void setSelectedindex(int si)
	    {
	    	this.selectedindex = si;
	    }
	    
	    public PubList identifyRecord()
	    {
	    	return publications.get(this.selectedindex);
	    }
	    

	    public void getStoredFiles()
	    {
	    	
	    	logger.info("made it this far!");
	    	String currlocation = "/opt/ceptools_data_storage/" + this.selectedPub.getPmid() + "/";
	    	File foldertozip = new File(currlocation);
	    	try{
	    	zip.zipFiles(foldertozip);
	    	}
	    	catch (Exception ex)
	    	{
	    		logger.info(ex);
	    		
	    	}
	    	
	    	logger.info("the folder path is going to be:" + currlocation);
	    	File folder = new File(currlocation);
	    	
	    	logger.info("The number of files in this listing is " + folder.listFiles().length);
	    
	    	
	    	for(File currfile: folder.listFiles())
	    	{
	    		FileStorer currfilestore = new FileStorer();
	    		currfilestore.setFilename(currfile.getName());
	    		currfilestore.setFilelocation(currfile.getPath());
	    		allfiles.add(currfilestore);
	    		files.add(currfile);
	    		filenames.add(currfile.getName());
	    	}
	    	
	    	selectedPub.setFiles(files);
	    	selectedPub.setFilenames(filenames);

	       logger.info("The file count on the new allfiles is: " + allfiles.size());
	    }
	    
	    
	    public void sendtoSolr()
	    {
	    	 
	    	try
	    	{
	    
	    	 SolrInputDocument metadoc = new SolrInputDocument();
	    	 
	    	 metadoc.addField("pmid", selectedPub.getPmid());
	    	 metadoc.addField("pid", UUID.randomUUID());
	    	 logger.info("pid version stored.");
	    	    metadoc.addField("abstract", selectedPub.getAbstract());
	    	   metadoc.addField("publicationdate_year", selectedPub.getYear());
	    	  
	    	   metadoc.addField("ptitle", selectedPub.getTitle() );  
	    	   
	    	  for(int i=0; i<selectedPub.getFauthors().size(); i++) 
	    	  {
	    	   metadoc.addField("author_firstname",selectedPub.getFauthors().get(i));
	    	    metadoc.addField("author_lastname",selectedPub.getLauthors().get(i)); 
	    	  }
	    	    
	    	  HttpSolrServer server = new HttpSolrServer("http://localhost:8983/solr");
	    	 server.add(metadoc);
	    	    server.commit();
	    	}
	    	catch(Exception ex)
	    	{
	    		
	    	}
	    }

	    
public int getCounter()
{
	return this.counter;
}


public void FileSave()
{
	
	String currpmid  = String.valueOf(selectedPub.getPmid());
	String currlocation = "/opts/ceptools_data_storage/" + currpmid;
	ArrayList<org.primefaces.model.UploadedFile> thefiles = new ArrayList<org.primefaces.model.UploadedFile>();
	
	 File thedir = new File(currlocation);
	 
	if(!thedir.exists())
	{
	 thedir.mkdirs();
	}
	
	//Now add files to it
	 try
	   {
	thefiles = fchooser.getAllFiles();
	OutputStream out = new FileOutputStream(thedir);
	for(org.primefaces.model.UploadedFile currfile: thefiles)
	{
	   String fullfilelocation = currlocation + currfile.getFileName();
	   File myfile = new File(fullfilelocation);
	  
	     out = new FileOutputStream(myfile);
	 
	  
	}
	
	
	out.close();
	}
	catch(Exception ex)
	{
		logger.info(ex);
	}
	
}

public void setFilestring()
{
	for(File currfile:this.files)
	{
		filenames.add(currfile.getName());
	}
}


public void setFilenames(List<String> filenames)
{
	  this.filenames = filenames;
}

public List<String> getFilenames()
{
	  return filenames;
}

public void initialProcess() throws Exception
{
	
	
	String input = "";
	
	if(this.getUsertitle().length() > 0)
	{
	 input = this.getUsertitle();	
	}
	else if(this.getUserauthor().length() > 0)
	{
		input = this.getUserauthor();
	}
	else if(this.getUserpmid().length() > 0)
	{
		input = this.getUserpmid();
	}
	
	  if (searcher.equalsIgnoreCase("title") )
	    {
	        
      
	          input = input.replaceAll("\\s", "+");
	        if( input.charAt(input.length()-1) == ((char) '+')   )
	        {
	           input = input.substring(0, input.length()-1);
	        }
	        
	        url = base + "esearch.fcgi?db=pubmed&term="+input+"&retmax=15&tool=ceptools";
	    }  
	    else if (searcher.equalsIgnoreCase("pmid") )
	    {
	       
	    
	        
	         input = input.replaceAll("\\s", "+");
	        if( input.charAt(input.length()-1) == ((char) '+')   )
	        {
	           input = input.substring(0, input.length()-1);
	        }
	        
	        url = base + "efetch.fcgi?db=pubmed&id="+input+"&retmode=xml&rettype=abstract";
	    }
	    else if (searcher.equalsIgnoreCase("author") )
	    {
	        
	        
	     
	        
	       input = input.replaceAll("\\s", "+");
	        if( input.charAt(input.length()-1) == ((char) '+')   )
	        {
	           input = input.substring(0, input.length()-1);
	        }
	        
	        url = base + "esearch.fcgi?db=pubmed&term="+input+"&retmax=15&tool=ceptools";
	    }
	    


	   
	    
}
	    
	    public void processUrl() throws Exception
	    {
	    	initialProcess();
	    	
	    	
	    	
	    	SAXReader reader = new SAXReader();
	    	SAXReader reader2 = new SAXReader();
		     Document document;
	        
	         OutputFormat format = OutputFormat.createPrettyPrint();
	      
	         XMLWriter writer = new XMLWriter( System.out, format );
	         
	         String mytitle, myabstract, myyear, myfullname = "";
	         int mypmid;
	         List<String> mylauthors = new ArrayList<String>();
	         List<String> myfauthors = new ArrayList<String>();
	         List<String> myfnames = new ArrayList<String>();
	        
	         
	         //PubMed
	         if (searcher.equalsIgnoreCase("pmid")) 
	         {  
	        	 document = reader.read(url);
	    	   
	               List firstnames = document.selectNodes( "//ForeName" );
	               List lastnames = document.selectNodes( "//LastName" );

	              mytitle = document.selectSingleNode("//ArticleTitle").getText();
	              myyear =  document.selectSingleNode("//PubDate/Year").getText();
	              myabstract = document.selectSingleNode("//AbstractText").getText();
	              mypmid = Integer.valueOf(document.selectSingleNode(".//PMID").getText());

	                Iterator fiter=firstnames.iterator();
	                Iterator liter=lastnames.iterator();

	                //System.out.println("The Authors: ");
	                
	                 while(fiter.hasNext()){
	                 Element currelement= (Element) fiter.next();
	                 myfauthors.add(currelement.getText());
	                 Element currelement2 = (Element) liter.next();
	                mylauthors.add(currelement2.getText());
	                
	                myfullname = currelement.getText() + " " + currelement2.getText();
	                myfnames.add(myfullname);
	                
	                 }
	                 
	                publications.add(new PubList(mytitle, myabstract, myyear, myfauthors, mylauthors, myfnames, mypmid, 0)); 
	           
	           }
	          else if (searcher.equalsIgnoreCase("title"))
	           {
	        	  document = reader.read(url);
	                   List idlist = document.selectNodes( "//IdList/Id" );
	                   Iterator iditer = idlist.iterator();
	                   String pubmedlist = "";
	                   int x = 0;
	                   this.counter = 0;
	               

	            
	                   while(iditer.hasNext())
	                   {
	                       Element pelement= (Element) iditer.next();
	                       if(x==0)
	                       {
	                       pubmedlist += pelement.getText() ;
	                       x+=1;
	                       }
	                       else
	                       {
	                           pubmedlist += "," + pelement.getText() ; 
	                       }
	                   }
	                   String msg = "";
	                   msg += pubmedlist;
	                 logger.info(msg);  
	                 url = base + "efetch.fcgi?db=pubmed&id="+pubmedlist+"&retmax=15&retmode=xml&rettype=abstract";   
	                 Document pubdoc = reader2.read(url);   
	            

	  	           List<Node> thelist = pubdoc.selectNodes("//PubmedArticle| //PubmedBookArticle");
	  	           
	       logger.info(url);
	       logger.info("the list size is"+ thelist.size());
	  	          
	  	           List currlist;
	  	           Element abstractnode, titlenode, yearsnode, pmidnode;
	  	           List firstnamenode;
	  	           List lastnamenode;
	  	          
	  	           
	  	           int n= 1;
	  	           int currindex = 0;
	  	           
	  	          for (Node currnode: thelist)
	  	          {
	  	        	
	  	              titlenode= (Element) currnode.selectSingleNode(".//ArticleTitle | .//BookTitle");
	  	              yearsnode= (Element) currnode.selectSingleNode(".//PubDate/Year | .//DateCompleted/Year");
	  	              firstnamenode= currnode.selectNodes(".//ForeName");
	  	              lastnamenode=  currnode.selectNodes(".//LastName");
	  	              abstractnode = (Element) currnode.selectSingleNode(".//Abstract/AbstractText[1]");
	  	              pmidnode = (Element) currnode.selectSingleNode(".//PMID");
	  	              myfnames = new ArrayList<String>();
	  	              Iterator fiter = firstnamenode.iterator();
	  	              Iterator liter = lastnamenode.iterator();
	  	           
	  	            
	  	            
	  	              mytitle = titlenode.getText();
	  	              
	  	              myyear = yearsnode.getText();
	  	              
	  	              mypmid = Integer.valueOf(pmidnode.getText());
	  	               
	  	                
	  	                while(fiter.hasNext())
	  	                {
	  	                 Element fname =  (Element) fiter.next();   
	  	                 Element lname =  (Element) liter.next();  
	  	                 
	  	                 myfauthors.add(fname.getText());
	  	               mylauthors.add(lname.getText());
	  	               
	  	             myfullname = fname.getText() + " " + lname.getText();
		                myfnames.add(myfullname);
	  	                 
	  	                }
	  	                
	  	              
	  	              if(abstractnode != null)
	  	              {   
	  	            	myabstract = abstractnode.getText();
	  	              }
	  	              else
	  	              {
	  	            	  myabstract = "NO ABSTRACT FOUND.";
	  	              }    
	  	             
	  	              
	  	           publications.add(new PubList(mytitle, myabstract, myyear, myfauthors, mylauthors, myfnames, mypmid, currindex)); 
	  	           
	  	           
	  	           
	  	              n++;
	  	              currindex++;
	  	          }  
	  	         
	  	       
	             
	           }
	            else if (searcher.equalsIgnoreCase("author"))
	           {
	            	document = reader.read(url);
	                   List idlist = document.selectNodes( "//IdList/Id" );
	                   Iterator iditer = idlist.iterator();
	                   String pubmedlist = "";
	                   int x = 0;
	                   
	                   
	           
	            Document pubdoc = reader2.read(url);    
	            
	                   while(iditer.hasNext())
	                   {
	                       Element pelement= (Element) iditer.next();
	                       if(x==0)
	                       {
	                       pubmedlist += pelement.getText() ;
	                       x+=1;
	                       }
	                       else
	                       {
	                           pubmedlist += "," + pelement.getText() ; 
	                       }
	                   }
	                   
	                url = base + "efetch.fcgi?db=pubmed&id="+pubmedlist+"&retmax=15&retmode=xml&rettype=abstract";       
	             pubdoc = reader2.read(url);
	                   
 
	           List<Node> thelist = pubdoc.selectNodes("//PubmedArticle| //PubmedBookArticle");
	           
 
	           List currlist;
  	           Element abstractnode, titlenode, yearsnode, pmidnode;
  	           List firstnamenode;
  	           List lastnamenode;
  	           
  	           int n= 1;
  	           int currindex = 0;
  	          for (Node currnode: thelist)
  	          {
  	        	
  	              titlenode= (Element) currnode.selectSingleNode(".//ArticleTitle | .//BookTitle");
  	              yearsnode= (Element) currnode.selectSingleNode(".//PubDate/Year | .//DateCompleted/Year");
  	              firstnamenode= currnode.selectNodes(".//ForeName");
  	              lastnamenode=  currnode.selectNodes(".//LastName");
  	              pmidnode = (Element) currnode.selectSingleNode(".//PMID");
  	              abstractnode = (Element) currnode.selectSingleNode(".//Abstract/AbstractText[1]");
  	              myfnames = new ArrayList<String>();
  	              Iterator fiter = firstnamenode.iterator();
  	              Iterator liter = lastnamenode.iterator();
  	           
  	            
  	            
  	              mytitle = titlenode.getText();
  	              
  	              myyear = yearsnode.getText();
  	            mypmid = Integer.valueOf(pmidnode.getText());
  	           
  	               
  	                
  	                while(fiter.hasNext())
  	                {
  	                 Element fname =  (Element) fiter.next();   
  	                 Element lname =  (Element) liter.next();  
  	                 
  	                 myfauthors.add(fname.getText());
  	               mylauthors.add(lname.getText());
  	               
  	             myfullname = fname.getText() + " " + lname.getText();
	                myfnames.add(myfullname);
  	                 
  	                }
  	                
  	              
  	              if(abstractnode != null)
  	              {   
  	            	myabstract = abstractnode.getText();
  	              }
  	              else
  	              {
  	            	  myabstract = "NO ABSTRACT FOUND.";
  	              }    
	              
	              publications.add(new PubList(mytitle, myabstract, myyear, myfauthors, mylauthors, myfnames, mypmid, currindex)); 
	              n++;
	              currindex++;
	          }  
	         
	      }
	         
	  logger.info("Final Test");
   
	  String currtitle, currabstract, curryear = "";
	  
	  for(PubList mypub: publications)
	  {
		  currtitle = mypub.getTitle();
		  currabstract = mypub.getAbstract();
		  curryear = mypub.getYear();
		  
		  logger.info("Title:"+currtitle+"\n Abstract:"+ currabstract + "\n Year:"+ curryear+"\n Authors:");
		  
		  
		  for (String currname: mypub.getFullnames())
		  {
			  logger.info(currname);
		  }
		  
	  }

	         
	    
	      
	    
	         
	    }   
	    
	    
}
	  

