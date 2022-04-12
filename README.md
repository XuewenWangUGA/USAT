# USAT
## Universal STR Allele Toolkit (USAT)

USAT is a bioinformatic software with a graphic interface for universal Tandem Repeat (TR) allele manipulation toolkit, including short TR (STR). It is free for all acdemic and educational purposes. Industrial and business users need to obtain license. Please contact for more information.
The USAT is full programmed in Java and, it is ready for running with mouse clicks only after downloading this software,so no installation is needed. 

## Function of USAT
USAT conducts detailed haplotype comparison and graphic plot of any STR alleles to provide information clues of difference between any alleles.
STR allele intreactibe viewing, sorting, filtering, and comparison. 
Graphic plot the distribution of alleles and comparison. 
Haplotype comprison
Converting allele length in bp to repeat time based allele size. 

## Installation and run
 Download the software into your computer and put in a directory called "usat". Double click the USAT.jar to run. This software will use a new version of Java runtime enviroment V17. If your computer has an older version, please install the newest Java runtime enviroment V17 or higher from https://www.oracle.com/java/technologies/downloads/#jdk17-windows 
 
 Run USAT
 method 1: double click the forensicAllele-1.1-SNAPSHOT.jar to run
 
 or mehotd 2 in command terminal, type 
 
` java -jar forensicAllele-1.1-SNAPSHOT.jar`

For more information, please read user mannual ![What is this](USAT_user_manual.pdf)


## Format of input sequence file:
USAT takes a sequence file with haplotype sequence for each STR and an optional BED file for specific information at each loci.
The format is a tabular text file with data like marker1 <tab> haplotype sequence <tab> SampleID, one haplotype per line. If there are multiple haplotypes, the same marker ID could be used for each locus. 
 
 e.g.
  MK1 CTATCTATCTATCTATCTATCTATCTATCTATCTATCTATCTAT S1
  MK1 CTATCTATCTATCTATCTATCTATCTATCTATCTATCTATCTATCTATCTATCTATCTAT S0
  
  A test dataset come with the software release for testing.
 
## Output
 
 All output will be displayed in graphic interface in an interactive manner.
 Allele table and comparison
![What is this](USAT_viewTableAlign_panel.png)
 
 Allele size/length comparison
 ![What is this](USAT_plot_panel.png)
 
 Allele comparison for multiple DNA sources
  ![What is this](Comp_HG002_003.png)
 
 ## Citing
 USAT is under consideration for publication.
  
