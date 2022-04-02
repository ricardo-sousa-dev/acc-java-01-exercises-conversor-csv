package com.trybe.conversorcsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

public class Conversor {

  /**
   * Função utilizada apenas para validação da solução do desafio.
   *
   * @param args Não utilizado.
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou gravar os
   *     arquivos de saída.
   */
  public static void main(String[] args) throws IOException {
    File pastaDeEntradas = new File("./entradas/");
    File pastaDeSaidas = new File("./saidas/");
    new Conversor().converterPasta(pastaDeEntradas, pastaDeSaidas);
  }

  /**
   * Converte todos os arquivos CSV da pasta de entradas. Os resultados são gerados na pasta de
   * saídas, deixando os arquivos originais inalterados.
   *
   * @param pastaDeEntradas Pasta contendo os arquivos CSV gerados pela página web.
   * @param pastaDeSaidas Pasta em que serão colocados os arquivos gerados no formato requerido pelo
   *     subsistema.
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou gravar os
   *     arquivos de saída.
   */
  public void converterPasta(File pastaDeEntradas, File pastaDeSaidas) throws IOException {
    // cria pastaDeSaidas;
    if (!pastaDeSaidas.exists()) {
      pastaDeSaidas.mkdir();
    }

    try {
      for (File arquivo :
          pastaDeEntradas.listFiles()) { // lista todos os arquivos na pasta de entrada
        if (arquivo.getName().endsWith(".csv")) {

          FileReader leitor = new FileReader(arquivo); // cria leitor do arquivo
          BufferedReader bufferedLeitor = new BufferedReader(leitor); // cria buffer para leitor
          String linha = bufferedLeitor.readLine(); // lê a linha do arquivo
          ArrayList<String> linhas = new ArrayList<String>(); // cria lista de linhas

          while (linha != null) { // enquanto houver linhas no arquivo
            linhas.add(linha); // adiciona linha a lista
            linha = bufferedLeitor.readLine(); // lê a linha do arquivo
          }
          bufferedLeitor.close(); // fecha o buffer
          leitor.close(); // fecha o leitor

          File saida = new File(pastaDeSaidas, arquivo.getName()); // cria arquivo de saída
          FileWriter escritor = null; // cria escritor do arquivo
          BufferedWriter bufferedEscritor = null; // cria buffer para escritor
          escritor = new FileWriter(saida); // cria escritor do arquivo
          bufferedEscritor = new BufferedWriter(escritor); // cria buffer para escritor

          for (String linhaArray : linhas) { // para cada linha do arquivo
            if (linhas.indexOf(linhaArray) != 0) { // se não for a primeira linha (cabeçalho)
              String nome = linhaArray.split(",")[0].toUpperCase(); // pega o nome

              String data = linhaArray.split(",")[1]; // pega a data
              String dataFormat =
                  data.substring(6)
                      + "-"
                      + data.substring(3, 5)
                      + "-"
                      + data.substring(0, 2); // formata a data

              String email = linhaArray.split(",")[2].toLowerCase(); // pega o email

              String cpf = linhaArray.split(",")[3]; // pega o cpf
              MaskFormatter maskCpf = new MaskFormatter("###.###.###-##"); // formata o cpf
              JFormattedTextField cpfFormatado =
                  new JFormattedTextField(maskCpf); // cria um campo de texto formatado
              cpfFormatado.setText(cpf); // seta o cpf formatado

              String linhaConvertida =
                  nome + "," + dataFormat + "," + email + "," + cpfFormatado.getText(); // formata
              // a linha
              bufferedEscritor.write(linhaConvertida); // escreve a linha no arquivo
              bufferedEscritor.newLine(); // pula uma linha
            } else {
              bufferedEscritor.write(linhaArray); // escreve a linha no arquivo
              bufferedEscritor.newLine(); // pula uma linha
            }
          }
          bufferedEscritor.flush(); // grava o arquivo
          bufferedEscritor.close(); // fecha o buffer
          escritor.close(); // fecha o escritor
        }
      }
    } catch (Exception e) { // caso ocorra algum erro
      System.out.println("Erro: " + e.getMessage()); // imprime o erro
    }
  }
}

// REFERENCIES:
// https://pt.stackoverflow.com/questions/320980/pegar-cada-linha-de-um-arquivo-csv?newreg=e576788a4d6441c397f2a98bd726523a
// https://www.guj.com.br/t/ler-csv-delimitar-e-atualizar-parte-da-linha-do-csv/85291
// https://www.guj.com.br/t/formatar-string-com-mascara-de-cnpj/353602
// https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/javax/swing/text/MaskFormatter.html
// https://www.tutorialspoint.com/swingexamples/creating_masked_textfield.htm
// https://www.demo2s.com/java/java-maskformatter-tutorial-with-examples.html
// https://www.tutorialspoint.com/get-the-index-of-a-particular-element-in-an-arraylist-in-java#:~:text=The%20index%20of%20a%20particular%20element%20in%20an%20ArrayList%20can,indexOf().
