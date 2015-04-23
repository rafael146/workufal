/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema;

import CinemaDataBase.BancoFuncionario;
import CinemaDataBase.BancoUsuario;
import GUI.JanelaPrincipal;
import GUI.Vendas;
import java.util.ArrayList;

/**
 *
 * @author marcus;
 */
public class Cinema {
          private String nome;
          private String CNPJ;
          private Endereco endereco;
          
         
    
     

          
          public static void main(String[] args) throws Exception{
              if (new BancoUsuario().searchObjects().isEmpty()){
                BancoFuncionario a = new BancoFuncionario();
                Funcionario funcionario = new Funcionario(1,"admin","admin","admin","admin",new Endereco("logradouro", "bairro", 9, "cidade", "cep", "estado"),"admin","admin","admin",null,true);
                funcionario.setTemLogin(true);
                a.saveObject(funcionario);
                funcionario.setId(1);
                System.out.println(funcionario.getId());
                BancoUsuario b = new BancoUsuario();
                char[] senhaA = {'a','d','m','i','n'};
                String senha = User.criptografarkey(senhaA);
                b.saveObject(new User("admin",senha,funcionario));
                System.out.println("primeira vez no sistema!");
              }
              new JanelaPrincipal().setVisible(true);
              
          }
          
          
          
          
            
        }
