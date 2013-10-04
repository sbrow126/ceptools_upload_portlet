package CEPTools;

public class FileStorer {
	
	String filename;
	String filelocation;
	
	private int index;
	
	public FileStorer()
	{
		index = 0;
	}
	
	  public int getIndex()
      {
      	return index;
      }
      
      public void setIndex(int i)
      {
      	this.index = i;
      }
	
	public void setFilename(String name)
	{
		this.filename = name;
	}
	
	public void setFilelocation(String location)
	{
		this.filelocation = location;
	}
	
	public String getFilename()
	{
		return filename;
	}
	
	public String getFilelocation()
	{
		return filelocation;
	}

}
