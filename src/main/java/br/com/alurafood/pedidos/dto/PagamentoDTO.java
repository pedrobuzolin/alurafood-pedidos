package br.com.alurafood.pedidos.dto;

import br.com.alurafood.pedidos.model.StatusPagamento;

import java.math.BigDecimal;

public record PagamentoDTO(Long id,
                           BigDecimal valor,
                           String nome,
                           String numero,
                           String expiracao,
                           String codigo,
                           StatusPagamento status,
                           Long pedidoId,
                           Long formaPagamentoId) {
}
