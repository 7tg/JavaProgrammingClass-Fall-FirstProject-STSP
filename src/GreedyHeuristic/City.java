/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GreedyHeuristic;

/**
 *
 * @author Tayyip
 */
public class City {
    public String name;
    public String country;
    public double lat;
    public double lon;
    public boolean flag;

    public void printInfo()
    {
        System.out.println("City name :"+this.name);
        System.out.println("Country name :"+this.country);
        System.out.println("Lat :"+this.lat);
        System.out.println("Lon :"+this.lon);
    }
    public City(String name, String country, double lat, double lon) {
        this.name = name;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
        this.flag = true;
    }
    public City() {
        this.name = null;
        this.country = null;
        this.lat = 0;
        this.lon = 0;
        this.flag = true;
    }
    
}
