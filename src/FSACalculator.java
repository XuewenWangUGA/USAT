package ForensicStrAlleleCalculator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import tandemVarPacBio.STRConfigure;
import tandemVarPacBio.ForensicAllele;
/**
 * @author Xuewen Wang <xwwang@ymail.com>
 * @version V0.1
 * @since 2021-Dec-27
 * Summary.
 * 
 * Description. to convert the haplotype sequence of each forensic allele into forensic allele size e.g., 10.2
 * usage:
 * required: marker_configureFile_in_bed marker_allele_sequence_tabdelimited.txt Out_put_file_name
 * java -jar FSACalculator.jar STRRegionsV5xwwinBest.bed.txt MkHaplotype.txt MkHaplotype.fas.txt
 */

public class FSACalculator {
	static String[][] MarkerPositions;
	static private HashMap<String, Integer> RefinnerOffset = new HashMap<String, Integer>(); //<marker, inneroffset>
 	static private HashMap<String, String> RefAllelesize = new HashMap<String, String>(); //<marker, refallele>
 	static private HashMap<String, Integer> RefStrSeqLen = new HashMap<String, Integer>(); //<marker, StrLen>
 	static private HashMap<String, Integer> MapMarkerMotifUnitLen = new HashMap<String, Integer>();
	public static void main(String[] args) throws IOException {
		
		//***********get pars***********
		String configureFile = (args.length == 0) ? "C:\\STRtest\\settings\\STRRegionsV5xwwinBest.bed.txt" : args[0];
		String markSeqFile = (args.length == 0) ? "C:\\STRtest\\testData\\MkHaplotype.txt": args[1];
		String outputfile = (args.length == 0) ? "C:\\STRtest\\testData\\MkHaplotype.fas.txt" : args[2];		
		
		//Store results: Marker name, forensic allele size, sequence
		ArrayList <String> mkAlleleSeqArray=new ArrayList();
		
		//***********read in loci/marker configure file***********
         readMarkerConfigureFile(configureFile);
		
		//***********convert seq length into forensic allele size***********
         String stralleles="";
		 ForensicAllele FA=new ForensicAllele();
		 //readin marker sequence file and convert each line input
		 try (BufferedReader inSeq = Files.newBufferedReader(Paths.get(markSeqFile))){  
		 String line;

		 while( ( line=inSeq.readLine() ) != null  )  {			 
			 if(line.startsWith("#")) continue;
			 line=line.strip();
			 if(line.length() > 0){
				 String[] cells=line.split("\t");
				 String hap=cells[1];
				 String markername=cells[0];
				 String sampleid=cells[2];
				 hap=hap.replaceAll("\\s+", "");
				 int haplen=hap.length();
				 int haplenOri=haplen;
				 haplen=haplen-RefinnerOffset.get(markername);
				 stralleles=FA.forensicAlleleCalculator(haplen, MapMarkerMotifUnitLen, markername);
				 String outInfo=String.join("\t", markername, stralleles, String.valueOf(haplenOri), hap, sampleid);
				 mkAlleleSeqArray.add(outInfo);				 
			 }			 

		 }
		 }catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
		 
		//***********output***********
		 try ( FileWriter outf = new FileWriter(outputfile) ) {
		      String header=String.join("\t", "#Marker", "Allele_size", "Length_(bp)", "Sequence","SampleID");
		      outf.write(header+"\n");
		      for (int i=0; i< mkAlleleSeqArray.size(); i++) {
		    	  String outData=mkAlleleSeqArray.get(i);
		    	  outf.write(outData+"\n");
		      }
		      outf.close();
		 }
		 
		 //Summary
		 System.out.println("Total Alleles:\t"+mkAlleleSeqArray.size());
		 System.out.println("Output file:\t"+outputfile);
		 
}
	
	private static void readMarkerConfigureFile(String configureFile) throws IOException {
		ArrayList<String[]> positions = new ArrayList<String[]>();		
		BufferedReader in = new BufferedReader(new FileReader(configureFile));
		String str=in.readLine();
        do{ 
            if((str=in.readLine()) != null) {
            	Pattern pattern=Pattern.compile("[\t]");
        		String[] split = pattern.split(str.subSequence(0, str.length())); 
                split = clearSplits(split);
                positions.add(split);
            }else {
            	break;
            }
        }while(true);
        
        int configRows=positions.size();
        int bedLociCount=configRows;
        int configColumns=positions.get(0).length; //count col of 1st row
        MarkerPositions = new String[configRows][configColumns]; 
        System.out.println("Total loci in .bed file:\t"+configRows+" "+"\tColumns:\t"+configColumns+"\n");
	  
    	
       //**************get info of each marker
		   for(int i=0;i<MarkerPositions.length;i++) {
			   MarkerPositions[i] = positions.get(i);
			   String markername = MarkerPositions[i][3]; //col4			
			   int markerMotifUnitLen=Integer.valueOf(MarkerPositions[i][6]); //col7 period
			   int innerOffset=Integer.valueOf(MarkerPositions[i][10]); // col 11
			   String refallelesize=String.valueOf(MarkerPositions[i][9]); //col10
			   int refStrLen=Integer.valueOf(MarkerPositions[i][2])-Integer.valueOf(MarkerPositions[i][1]); //col3, 2
			   RefStrSeqLen.put(markername,  refStrLen);
			   RefinnerOffset.put(markername,  innerOffset);
			   RefAllelesize.put(markername,  refallelesize);
			   MapMarkerMotifUnitLen.put(markername, markerMotifUnitLen);
		   }
	}
	

	public static String[] clearSplits(String[] splits) {
		int nullstring=0;
		for(int i=0;i<splits.length;i++)
		{
		if(splits[i].equalsIgnoreCase(""))
		{
		nullstring++;
		}
		}
		String[] newsplits = new String[splits.length-nullstring];
		int count=0;
		for(int i=0;i<splits.length;i++)
		{
		if(!splits[i].equalsIgnoreCase(""))
		{
		newsplits[i-count] = splits[i];
		}else
		{
		count++;
		}
		}
		return newsplits;
		}
}


