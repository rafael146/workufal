/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema;

import CinemaDataBase.BancoFuncionario;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author henrique
 */
public class Funcionario extends Pessoa {

    private Endereco endereco;
    private String cpf;
    private String rg;
    private boolean temLogin = false;
    private String observacao;
    private String fone;
    private String funcao;

    public Funcionario() {
        
    }

    
    
    public Funcionario(String nome, String cpf, String rg, String email, Endereco endereco, String observacao, String fone, String funcao, User usuario) {
        super(nome, email, usuario);
        this.fone = fone;
        this.endereco = endereco;
        this.cpf = cpf;
        this.rg = rg;
        this.observacao = observacao;
        this.funcao = funcao;
    }

    public Funcionario(int id, String nome, String cpf, String rg, String email, Endereco endereco, String observacao, String fone, String funcao, User usuario, boolean login) {
        super(nome, email, usuario);
        this.fone = fone;
        this.endereco = endereco;
        this.cpf = cpf;
        this.rg = rg;
        this.observacao = observacao;
        this.funcao = funcao;
        this.setId(id);
        this.temLogin = login;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public boolean getTemLogin() {
        return temLogin;
    }

    public void setTemLogin(boolean temLogin) {
        this.temLogin = temLogin;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public boolean salvaFuncionario(Funcionario funcionario) {

        return (funcionario != null && funcionario.getNome().length() > 0 && funcionario.getEndereco().getLogradouro().length() > 0
                && funcionario.getEndereco().getNumero() != 0 && funcionario.getEndereco().getBairro().length() > 0
                && funcionario.getEndereco().getCidade().length() > 0 && funcionario.getEndereco().getEstado().length() > 0
                && funcionario.getCpf().length() > 0 && funcionario.getRg().length() > 0 && funcionario.getEndereco().getCep().length() > 0 && funcionario.getEmail().length() > 0
                && funcionario.getFone().length() > 0);
    }


    public boolean validaCPF(String strCpf) {
        int iDigito1Aux = 0, iDigito2Aux = 0, iDigitoCPF;
        int iDigito1 = 0, iDigito2 = 0, iRestoDivisao = 0;
        String strDigitoVerificador, strDigitoResultado;

        if (!strCpf.substring(0, 1).equals("")) {
            try {
                strCpf = strCpf.replace('.', ' ');
                strCpf = strCpf.replace('-', ' ');
                strCpf = strCpf.replaceAll(" ", "");
                for (int iCont = 1; iCont < strCpf.length() - 1; iCont++) {
                    iDigitoCPF = Integer.valueOf(strCpf.substring(iCont - 1, iCont)).intValue();
                    iDigito1Aux = iDigito1Aux + (11 - iCont) * iDigitoCPF;
                    iDigito2Aux = iDigito2Aux + (12 - iCont) * iDigitoCPF;
                }
                iRestoDivisao = (iDigito1Aux % 11);
                if (iRestoDivisao < 2) {
                    iDigito1 = 0;
                } else {
                    iDigito1 = 11 - iRestoDivisao;
                }
                iDigito2Aux += 2 * iDigito1;
                iRestoDivisao = (iDigito2Aux % 11);
                if (iRestoDivisao < 2) {
                    iDigito2 = 0;
                } else {
                    iDigito2 = 11 - iRestoDivisao;
                }
                strDigitoVerificador = strCpf.substring(strCpf.length() - 2, strCpf.length());
                strDigitoResultado = String.valueOf(iDigito1) + String.valueOf(iDigito2);
                return strDigitoVerificador.equals(strDigitoResultado);
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }
    
    
    public boolean verificarCPF(String cpf){
        ArrayList<Funcionario> funcionarios = null;
        funcionarios = new BancoFuncionario().searchObjects();
        for(Funcionario funcionario: funcionarios){
            if (funcionario.getCpf().equals(cpf)){
                return false;
            }
        }
        
        return true;
    }
    @Override
    public String toString() {
        return this.getNome()+" id = "+this.getId();
    }
    
    
}
