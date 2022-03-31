package com.trybe.conversorcsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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

    try {
      for (File arquivo : pastaDeEntradas.listFiles()) {
        if (arquivo.getName().endsWith(".csv")) {
          FileReader leitor = new FileReader(arquivo);
          BufferedReader bufferedLeitor = new BufferedReader(leitor);
          String linha = bufferedLeitor.readLine();
          ArrayList<String> linhas = new ArrayList<String>();
          while (linha != null) {
            linhas.add(linha);
            linha = bufferedLeitor.readLine();
          }
          bufferedLeitor.close();
          leitor.close();

          File saida = new File(pastaDeSaidas, arquivo.getName());
          FileWriter escritor = null;
          BufferedWriter bufferedEscritor = null;
          escritor = new FileWriter(saida);
          bufferedEscritor = new BufferedWriter(escritor);
          for (String linhaConvertida : linhas) {
            bufferedEscritor.write(linhaConvertida);
            bufferedEscritor.newLine();
          }
          bufferedEscritor.flush();
          bufferedEscritor.close();
          escritor.close();
        }
      }
    } catch (Exception e) {
      System.out.println("Erro: " + e.getMessage());
    }

    // try {
    // escritor = new FileWriter(pastaDeEntradas);
    // bufferedEscritor = new BufferedWriter(escritor); // Objeto com o conteudo
    // escrito em um buffer
    // bufferedEscritor.write(senha); // Insere o contéudo que será escrito ao
    // buffer
    // bufferedEscritor.flush(); // Obtem o conteudo do buffer e escreve no arquivo

    // } catch (IOException e) {
    // e.printStackTrace();
    // } finally {
    // this.fecharObjetos(escritor, bufferedEscritor);
    // }
  }
}
