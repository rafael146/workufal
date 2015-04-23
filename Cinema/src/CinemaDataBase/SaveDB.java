/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CinemaDataBase;

import java.util.ArrayList;

/**
 *Esta Interface è responsavel pelas operações 
 * Efetuadas no  Banco de Dados
 * @author marcus-pc
 */
public interface SaveDB<T> {
        
    public  void   saveObject(T o);


	public void setObject(T o);

	public T  getObject(String o);
	
	public ArrayList<T> searchObjects();


     public  void deleteobject(String o);
    
    
       
            }
