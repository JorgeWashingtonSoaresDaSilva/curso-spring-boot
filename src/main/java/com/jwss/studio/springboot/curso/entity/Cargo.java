package com.jwss.studio.springboot.curso.entity;

public enum Cargo {
    JUNIOR("Júnior"),
    PLENO("Pleno"),
    SENIOR("Sênior");
private String nome;// chame no html com thymeleaf usando c.nome ou so c
    // th:each="c : ${T(com.jwss.studio.springboot.curso.entity.Cargo).values()}" //
    // th:value="${c}" th:text="${c.nome}"
    private Cargo(String nome) {
        this.nome =nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.name();// tem que usar name que é propriedade do enum se não da erro
    }
}
