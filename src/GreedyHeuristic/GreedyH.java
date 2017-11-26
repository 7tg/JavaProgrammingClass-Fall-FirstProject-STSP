package GreedyHeuristic;
import GPS.*;
import java.io.*;
import java.util.Scanner;
import java.nio.CharBuffer;
import java.text.DecimalFormat;
import java.time.format.*;
import java.util.Locale;


/**
 *
 * @author Tayyip
 */
public class GreedyH {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //Getting N from User
        System.out.print("Welcome,\nPlease input N number of cities :");
        int N = sc.nextInt();
        System.out.println("");
        sc.nextLine();
        //Getting file name from User
        System.out.print("Please input file name eg.(gsp_turkiye.txt) :");
        String fileName = "src/"+sc.nextLine();
        System.out.println("");
        long startTime = System.currentTimeMillis();
        //Reading cities from file 
        City[] cities = new City[N];
        readFile(cities,fileName);
        //Finding the route
        PrintWriter  pt = null;
        try {
          pt = new PrintWriter(fileName.replaceAll(".txt","")+"_for"+N+"_sonuc.txt","UTF-8");
        } catch (Exception e) {
            System.err.println("Error :"+e);
        }
        System.out.println(findNearest(cities,0,0,pt));
        long endTime = System.currentTimeMillis();
        pt.println("Number of cities : "+N);
        pt.println("Total Runnig Time :"+(endTime-startTime)+"ms");
        
        pt.close();
        
    }
    
    public static String findNearest(City[] cities,int index,double total,PrintWriter pt)
    {
        if(cities[index].flag == false)
            return findNearestBack(cities,index,total,pt);
        double[] min = new double[2];
        min[0] = Double.MAX_VALUE;
        cities[index].flag = false;
        double temp;
        for (int i = 0; i <cities.length; i++) {
            if (cities[i].flag == false)
                continue;
            else
            {
            temp = GPS.distance( cities[index].lat, cities[i].lat, cities[index].lon, cities[i].lon);
            if(temp < min[0])
            {
                min[0] = temp;
                min[1] = i;  
            }
            }
            
        }
        if(min[0] == Double.MAX_VALUE)
        {
            System.out.println("\nTotal Calculation :"+cities[(int)min[1]].name+"----- "+(String.format("%.2f",(total * 0.001)))+"km "+"------>"+cities[index].name+"\n");
            pt.println("\nTotal Calculation :"+cities[(int)min[1]].name+"----- "+(String.format("%.2f",(total * 0.001)))+"km "+"------>"+cities[index].name+"\n");
            return findNearest(cities,index,total,pt);
        }
        System.out.println(cities[index].name+"["+cities[index].country.trim()+"]"+"---"+(String.format("%.2f",(min[0] * 0.001)))+"km"+"--->"+cities[(int)min[1]].name+"["+cities[(int)min[1]].country.trim()+"]");
        pt.println(cities[index].name+"["+cities[index].country.trim()+"]"+"---"+(String.format("%.2f",(min[0] * 0.001)))+"km"+"--->"+cities[(int)min[1]].name+"["+cities[(int)min[1]].country.trim()+"]");
        return findNearest(cities,(int)min[1],total += min[0],pt);
    }
    
    public static String findNearestBack(City[] cities,int index,double total,PrintWriter pt)
    {
        if(cities[index].flag == true)
            return (String.format("%.2f",(total * 0.001)));
        double[] min = new double[2];
        min[0] = Double.MAX_VALUE;
        cities[index].flag = true;
        double temp;
        for (int i = 0; i <cities.length; i++) {
            if (cities[i].flag == true)
                continue;
            else
            {
            temp = GPS.distance( cities[index].lat, cities[i].lat, cities[index].lon, cities[i].lon);
            if(temp < min[0])
            {
                min[0] = temp;
                min[1] = i;  
            }
            }
            
        }
        if(min[0] == Double.MAX_VALUE)
        {
            System.out.println("\nTotal Calculation :"+cities[(int)min[1]].name+"----- "+(String.format("%.2f",(total * 0.001)))+"km "+"------>"+cities[index].name);
            pt.println("\nTotal Calculation Back:"+cities[(int)min[1]].name+"----- "+(String.format("%.2f",(total * 0.001)))+"km "+"------>"+cities[index].name);
            return findNearestBack(cities,(int)min[1],total,pt);
        }
        System.out.println(cities[index].name+"["+cities[index].country.trim()+"]"+"---"+(String.format("%.2f",(min[0] * 0.001)))+"km"+"--->"+cities[(int)min[1]].name+"["+cities[(int)min[1]].country.trim()+"]");
        pt.println(cities[index].name+"["+cities[index].country.trim()+"]"+"---"+(String.format("%.2f",(min[0] * 0.001)))+"km"+"--->"+cities[(int)min[1]].name+"["+cities[(int)min[1]].country.trim()+"]");
        return findNearestBack(cities,(int)min[1],total += min[0],pt);
    }
    
    public static boolean readFile(City[] cities,String fileName)
    {
        FileReader in = null;
        BufferedReader br = null;
        try{
        in = new FileReader(fileName);
        br = new BufferedReader(in);
        }catch(FileNotFoundException s)
        {
            System.out.println("File not found exiting the program."+s);
            System.exit(0);
        }

        StringBuilder sb = new StringBuilder();
        String text = null ;
        
        
        for(int i = 0;i < cities.length*4;i++)
        {
            try{
            text = br.readLine();
            }catch(IOException s)
            {
                System.out.println("IOException"+s);
                System.exit(0);
            }
            if(text.compareTo("") != 0)
            {
            sb.append(text);
            sb.append(System.lineSeparator());
            }
        }
        text = sb.toString();
        String[] parsed = text.split("\n");
        int index = 0;
        for (int i = 0; i < cities.length; i++) {
           String[] temp = parsed[index].split(", ");
           cities[i] = new City(temp[0],temp[1],Double.parseDouble(parsed[++index]),Double.parseDouble(parsed[++index]));
           index++;
           cities[i].printInfo();
            
        }
        return true;
    }
    
}
