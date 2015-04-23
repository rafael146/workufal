SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `CinemaDB` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `CinemaDB` ;

-- -----------------------------------------------------
-- Table `CinemaDB`.`FUNCIONARIO` wwww
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `CinemaDB`.`FUNCIONARIO` (
  `idFUNCIONARIO` INT ZEROFILL NOT NULL AUTO_INCREMENT ,
  `NOME` VARCHAR(45) NOT NULL ,
  `CPF` MEDIUMTEXT NOT NULL ,
  `RG` VARCHAR(45) NOT NULL ,
  `EMAIL` VARCHAR(45) NOT NULL ,
  `LOGRADOURO` VARCHAR(45) NOT NULL ,
  `BAIRRO` VARCHAR(45) NOT NULL ,
  `ENDRNUMERO` MEDIUMTEXT NOT NULL ,
  `CIDADE` VARCHAR(45) NOT NULL ,
  `CEP` VARCHAR(45) NOT NULL ,
  `ESTADO` VARCHAR(45) NOT NULL ,
  `OBSERVACAO` VARCHAR(100) ,
  `FONE` VARCHAR(45) NOT NULL ,
  `TEMLOGIN` TINYINT(1) NOT NULL ,
  `FUNCAO` VARCHAR(45) NOT NULL ,
  `LOGIN` VARCHAR(45) ,
  PRIMARY KEY (`idFUNCIONARIO`)
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CinemaDB`.`USUARIO`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `CinemaDB`.`USUARIO` (
  `LOGIN` VARCHAR(45) NOT NULL ,
  `SENHA` VARCHAR(12) NOT NULL ,
  `idFUNCIONARIO` INT ZEROFILL NOT NULL ,
  PRIMARY KEY (`LOGIN`) ,
    FOREIGN KEY (`idFUNCIONARIO` ) REFERENCES `CinemaDB`.`FUNCIONARIO` (`idFUNCIONARIO` )
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CinemaDB`.`CLIENTE`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `CinemaDB`.`CLIENTE` (
  `idCLIENTE` INT ZEROFILL NOT NULL AUTO_INCREMENT ,
  `NOME` VARCHAR(45) NOT NULL ,
  `EMAIL` VARCHAR(45) NOT NULL ,
  `GENERO` VARCHAR(45) NOT NULL ,
  `LOGIN` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idCLIENTE`) ,
  FOREIGN KEY (`LOGIN` ) REFERENCES `CinemaDB`.`USUARIO` (`LOGIN` )
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CinemaDB`.`FILME`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `CinemaDB`.`FILME` (
  `idFILME` INT NOT NULL AUTO_INCREMENT ,
  `TITULOPTBR` VARCHAR(45) NOT NULL ,
  `TITULOORGN` VARCHAR(45) NOT NULL ,
  `GENERO` VARCHAR(45) NOT NULL ,
  `SINOPSE` VARCHAR(120) NOT NULL ,
  `DIRETOR` VARCHAR(45) NOT NULL ,
  `ANOLANCAMENTO` VARCHAR(45) NOT NULL ,
  `CLASSINDICATIVA` VARCHAR(45) NOT NULL ,
  `DURACAO` INT ZEROFILL NOT NULL ,
  `LOGIN` VARCHAR(45) NOT NULL ,
  `TIPOFORMATO` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idFILME`) ,
  FOREIGN KEY (`LOGIN` ) REFERENCES `CinemaDB`.`USUARIO` (`LOGIN` )
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CinemaDB`.`PROPAGANDA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `CinemaDB`.`PROPAGANDA` (
  `idPROPAGANDA` INT ZEROFILL NOT NULL AUTO_INCREMENT ,
  `ANUNCIANTE` VARCHAR(45) NOT NULL ,
  `FILME` VARCHAR(45) NOT NULL ,
  `LOGIN` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idPROPAGANDA`) ,
  FOREIGN KEY (`LOGIN` ) REFERENCES `CinemaDB`.`USUARIO` (`LOGIN` )
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CinemaDB`.`SALA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `CinemaDB`.`SALA` (
  `NUMEROSALA` INT ZEROFILL NOT NULL ,
  `CAPACIDADE` INT NOT NULL ,
  `TIPO` VARCHAR(45) NOT NULL ,
  `LOGIN` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`NUMEROSALA`) ,
  FOREIGN KEY (`LOGIN` ) REFERENCES `CinemaDB`.`USUARIO` (`LOGIN` )
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CinemaDB`.`SESSAO`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `CinemaDB`.`SESSAO` (
  `CODSESSAO` VARCHAR(20) NOT NULL ,
  `idFILME` INT NOT NULL ,
  `NUMEROSALA` INT NOT NULL ,
  `DATA` VARCHAR(20) NOT NULL ,
  `HORARIO` VARCHAR(20) NOT NULL ,
  `LOGIN` VARCHAR(25) NOT NULL ,
  PRIMARY KEY (`CODSESSAO`) ,
  FOREIGN KEY (`LOGIN`) REFERENCES `CinemaDB`.`USUARIO` (`LOGIN` ) ,
  FOREIGN KEY (`idFILME` ) REFERENCES `CinemaDB`.`FILME` (`idFILME` ) 
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CinemaDB`.`INGRESSO`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `CinemaDB`.`INGRESSO` (
  `CODINGRESSO` INT ZEROFILL NOT NULL AUTO_INCREMENT ,
  `VALOR` FLOAT NOT NULL ,
  `CODSESSAO` VARCHAR(45) NOT NULL ,
  `FORMAPAGAMENTO` VARCHAR(45) NOT NULL ,
  `LOGIN` VARCHAR(45) NOT NULL ,
  `CADEIRA` VARCHAR(10) NOT NULL ,
  PRIMARY KEY (`CODINGRESSO`) ,
  FOREIGN KEY (`LOGIN` ) REFERENCES `CinemaDB`.`USUARIO` (`LOGIN`) ,
  FOREIGN KEY (`CODSESSAO` ) REFERENCES `CinemaDB`.`SESSAO` (`CODSESSAO`)
)ENGINE = InnoDB;

USE `CinemaDB` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
