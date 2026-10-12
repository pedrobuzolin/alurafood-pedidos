package br.com.alurafood.pedidos.dto;

import br.com.alurafood.pedidos.model.Status;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoDto(Long id, LocalDateTime dataHora, Status status, List<ItemDoPedidoDto> itens) {
}
