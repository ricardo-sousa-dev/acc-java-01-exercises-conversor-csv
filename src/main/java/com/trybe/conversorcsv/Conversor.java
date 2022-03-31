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

          for (String linhaArray : linhas) {
            if (linhas.indexOf(linhaArray) != 0) {
              String nome = linhaArray.split(",")[0].toUpperCase();
              String data = linhaArray.split(",")[1].replace("/", "-");
              String email = linhaArray.split(",")[2].toLowerCase();

              String cpf = linhaArray.split(",")[3];
              MaskFormatter maskCpf = new MaskFormatter("###.###.###-##");
              JFormattedTextField cpfFormatado = new JFormattedTextField(maskCpf);
              cpfFormatado.setText(cpf);
              System.out.println(cpfFormatado.getText());

              String linhaConvertida =
                  nome + "," + data + "," + email + "," + cpfFormatado.getText();
              bufferedEscritor.write(linhaConvertida);
              bufferedEscritor.newLine();
            } else {
              bufferedEscritor.write(linhaArray);
              bufferedEscritor.newLine();
            }
          }
          bufferedEscritor.flush();
          bufferedEscritor.close();
          escritor.close();
          // }
        }
      }
    } catch (Exception e) {
      System.out.println("Erro: " + e.getMessage());
    }
  }
}
