package Sites;

import java.io.Serializable;
import java.util.Objects;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *<pre>
 * Class        Site.java
 * Description  A class representing the Site in the State Capitals Quiz.  
 * Project      Vacation Sites Quiz
 * Platform     jdk 21; NetBeans IDE 19; PC Windows 11
 * Course       CS 143
 * Date         10/10/2023
 * @author	<i>Chiquita Zephaniah</i>
 * </pre>
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class Site implements Comparable, Serializable{
    private int id;
    private String name;
    private String country;
    private float population;
    private String capital;
    private float area;
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Constructor  Site
     * Description  Default constructor of the Site class
     * Date         10/10/2023 
     * @author      <i>Chiquita Zephaniah</i>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public Site(){
        id = 0;
        population = area = 0;
        name = country = capital = "";
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Constructor  Site
     * Description  Overloaded constructor of the Site class
     * Date         10/10/2023
     * @param       name String
     * @param       country String
     * @param       population float
     * @param       capital String
     * @param       area float
     * @author      <i>Chiquita Zephaniah</i>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public Site(int id, String name, String country, float population, String capital, float area) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.population = population;
        this.capital = capital;
        this.area = area;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * <pre>
     * Constructor  Site
     * Description  Overloaded constructor of the Site class
     * Date         10/10/2023
     * @param       site2 Site
     * @author      <i>Chiquita Zephaniah</i>
     * </pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public Site(Site site2)
    {
        id = site2.id;
        name = site2.name;
        country = site2.country;
        population = site2.population;
        capital = site2.capital;
        area = site2.area;
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method       getName()
     * Description  Get method to return instance variable name
     * Date         10/10/2023 
     * @return      name String
     * @author      <i>Chiquita Zephaniah</i>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public String getName() {
        return name;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method       setName()
     * Description  Set method to set instance variable name
     * Date         10/10/2023 
     * @param       name String
     * @author      <i>Chiquita Zephaniah</i>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setName(String name) {
        this.name = name;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method       getCountry()
     * Description  Get method to return instance variable country
     * Date         10/10/2023 
     * @return      country String
     * @author      <i>Chiquita Zephaniah</i>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public String getCountry() {
        return country;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method       setCountry()
     * Description  Set method to set instance variable country
     * Date         10/10/2023 
     * @param       country String
     * @author      <i>Chiquita Zephaniah</i>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setCountry(String country) {
        this.country = country;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method       getPopulation()
     * Description  Get method to return instance variable population
     * Date         10/10/2023 
     * @return      population String
     * @author      <i>Chiquita Zephaniah</i>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public float getPopulation() {
        return population;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method       setPopulation()
     * Description  Set method to set instance variable population
     * Date         10/10/2023 
     * @param       population float
     * @author      <i>Chiquita Zephaniah</i>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setPopulation(float population) {
        this.population = population;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method       getCapital()
     * Description  Get method to return instance variable name
     * Date         10/10/2023 
     * @return      capital String
     * @author      <i>Chiquita Zephaniah</i>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public String getCapital() {
        return capital;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method       setCapital()
     * Description  Set method to set instance variable capital
     * Date         10/10/2023 
     * @param       capital String
     * @author      <i>Chiquita Zephaniah</i>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setCapital(String capital) {
        this.capital = capital;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method       getArea()
     * Description  Get method to return instance variable area
     * Date         10/10/2023 
     * @return      area float
     * @author      <i>Chiquita Zephaniah</i>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public float getArea() {
        return area;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method       setArea()
     * Description  Set method to set instance variable area
     * Date         10/10/2023 
     * @param       area float
     * @author      <i>Chiquita Zephaniah</i>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setArea(float area) {
        this.area = area;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       getID()
     * Description  Getter method to return id.
     * @return      id int
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023  
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public int getID() {
        return id;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *<pre>
     * Method       setID()
     * Description  Setter method to set id.
     * @param       id int
     * @author      <i>Chiquita Zephaniah</i>
     * Date         10/31/2023  
    *</pre>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setID(int id) {
        this.id = id;
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method       toString()
     * Description  Overridden toString() method to display a Site object
     * Date         10/10/2023 
     * @return      Site object String
     * @author      <i>Chiquita Zephaniah</i>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    @Override
    public String toString() {
        return "Site{" + "name=" + name + ", country=" + country + ", population=" + population + ", capital=" + capital + '}';
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method       hashCode()
     * Description  Overridden method to set hash code
     * Date         10/10/2023 
     * @return      hash int
     * @author      <i>Chiquita Zephaniah</i>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.country);
        hash = 47 * hash + Float.floatToIntBits(this.population);
        hash = 47 * hash + Objects.hashCode(this.capital);
        hash = 47 * hash + Float.floatToIntBits(this.area);
        return hash;
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method       equals()
     * Description  Check if one Site object equals to another
     * Date         10/10/2023 
     * @return      true or false Boolean
     * @author      <i>Chiquita Zephaniah</i>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Site other = (Site) obj;
        if (Float.floatToIntBits(this.population) != Float.floatToIntBits(other.population)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        return Objects.equals(this.capital, other.capital);
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Method       compareTo()
     * Description  Compare two sites with exact string
     * Date         10/10/2023 
     * @param       o Object
     * @author      <i>Chiquita Zephaniah</i>
    *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    @Override
    public int compareTo(Object o) {
        Site otherSite = (Site) o;
        if((this.getName()).equalsIgnoreCase(otherSite.getName()))
            return (int)(this.population - ((Site) o).population);
        else
            return (this.getName().toLowerCase()).compareTo(otherSite.getName().toLowerCase());
    }
}
