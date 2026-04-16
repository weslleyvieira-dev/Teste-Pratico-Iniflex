package code;

import java.util.List;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class Main {
  List<Funcionario> funcionarios = new ArrayList<>();
  static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  static NumberFormat numberFormat = NumberFormat.getInstance(Locale.of("pt", "BR"));

  public static void main(String[] args) {
    System.out.println("-------------------------------------------------------");
    Main app = new Main();
    app.inserirFuncionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador");
    app.inserirFuncionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador");
    app.inserirFuncionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador");
    app.inserirFuncionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor");
    app.inserirFuncionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista");
    app.inserirFuncionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador");
    app.inserirFuncionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador");
    app.inserirFuncionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente");
    app.inserirFuncionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista");
    app.inserirFuncionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente");
    System.out.printf("3.1 - Funcionários adicionados com sucesso!%n");
    app.removerFuncionario("João");
    app.listarFuncionarios();
    app.ajustarTodosSalarios(new BigDecimal("1.10"));
    app.listarFuncionarios();
    app.agruparFuncionariosPorFuncao();
    app.listarFuncionariosPorAniversario(10);
    app.listarFuncionariosPorAniversario(12);
  }

  public void listarFuncionarios() {
    System.out.println("-------------------------------------------------------");
    System.out.println("Nome     | Data Nascimento | Salário    | Função");
    System.out.println("-------------------------------------------------------");
    for (Funcionario f : funcionarios) {
      String nome = f.getNome();
      String formattedDate = f.getDataNascimento().format(formatter);
      String salario = numberFormat.format(f.getSalario());
      String funcao = f.getFuncao();
      System.out.printf("%-8s | %-15s | %-10s | %-10s %n", nome, formattedDate, salario, funcao);
    }
    System.out.println("-------------------------------------------------------");
  }

  public void inserirFuncionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
    funcionarios.add(new Funcionario(nome, dataNascimento, salario, funcao));
  }

  public void removerFuncionario(String nome) {
    boolean removed = funcionarios.removeIf(f -> f.getNome().equals(nome));
    if (removed) {
      System.out.printf("3.2 - Funcionário '%s' removido com sucesso!%n", nome);
    } else {
      System.out.printf("3.2 - Funcionário '%s' não encontrado.%n", nome);
    }
  }

  /*
   * Ajusta o salário de todos os funcionários usando um fator multiplicador.
   * Ex: 1.10 = +10%, 0.90 = -10%.
   */
  public void ajustarTodosSalarios(BigDecimal fator) {
    funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(fator)));
    System.out.println("3.4 - Salários ajustados!");
  }

  public void agruparFuncionariosPorFuncao() {
    Map<String, List<Funcionario>> grupos = funcionarios.stream()
        .collect(Collectors.groupingBy(Funcionario::getFuncao, LinkedHashMap::new, Collectors.toList()));

    System.out.println("3.5 - Funcionários agrupados por função:");
    System.out.println("-------------------------------------------------------");

    grupos.forEach((funcao, lista) -> {
      System.out.println(funcao);
      lista.forEach(f -> {
        String nome = f.getNome();
        System.out.printf(" - %-10s%n", nome);
      });
      System.out.println("");
    });
    System.out.println("-------------------------------------------------------");
  }

  public void listarFuncionariosPorAniversario(Integer mes) {
    System.out.printf("3.6 - Listando aniversários do mês %s:", mes);
    System.out.println("\n-------------------------------------------------------");
    for (Funcionario f : funcionarios) {
      String nome = f.getNome();
      String formattedDate = f.getDataNascimento().format(formatter);
      Month mesAniversario = f.getDataNascimento().getMonth();
      if (mesAniversario.getValue() == mes) {
        System.out.printf("%-8s | %-15s%n", nome, formattedDate);
      }
    }
    System.out.println("-------------------------------------------------------");
  }
}
