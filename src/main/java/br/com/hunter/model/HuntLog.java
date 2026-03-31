package br.com.hunter.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "hunt_log")
public class HuntLog {

    private static final DateTimeFormatter FORMATO_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataCacada;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cacador_id", nullable = false)
    private Cacador cacador;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "monstro_id", nullable = false)
    private Monstro monstro;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "local_cacada_id", nullable = false)
    private LocalCacada localCacada;

    public HuntLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataCacada() {
        return dataCacada;
    }

    public String getDataCacadaFormatada() {
        return dataCacada == null ? "" : dataCacada.format(FORMATO_BR);
    }

    public void setDataCacada(LocalDateTime dataCacada) {
        this.dataCacada = dataCacada;
    }

    public Cacador getCacador() {
        return cacador;
    }

    public void setCacador(Cacador cacador) {
        this.cacador = cacador;
    }

    public Monstro getMonstro() {
        return monstro;
    }

    public void setMonstro(Monstro monstro) {
        this.monstro = monstro;
    }

    public LocalCacada getLocalCacada() {
        return localCacada;
    }

    public void setLocalCacada(LocalCacada localCacada) {
        this.localCacada = localCacada;
    }
}
