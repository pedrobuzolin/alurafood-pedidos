package br.com.alurafood.pedidos.service;

import br.com.alurafood.pedidos.repository.PedidoRepository;
import br.com.alurafood.pedidos.dto.PedidoDto;
import br.com.alurafood.pedidos.dto.StatusDto;
import br.com.alurafood.pedidos.model.Pedido;
import br.com.alurafood.pedidos.model.Status;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ModelMapper modelMapper;

    public PedidoService(PedidoRepository pedidoRepository, ModelMapper modelMapper) {
        this.pedidoRepository = pedidoRepository;
        this.modelMapper = modelMapper;
    }

    public List<PedidoDto> obterTodos() {
        return pedidoRepository.findAll().stream()
                .map(p -> modelMapper.map(p, PedidoDto.class))
                .collect(Collectors.toList());
    }

    public PedidoDto obterPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(pedido, PedidoDto.class);
    }

    public PedidoDto criarPedido(PedidoDto dto) {
        Pedido pedido = modelMapper.map(dto, Pedido.class);

        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(Status.REALIZADO);
        pedido.getItens().forEach(item -> item.setPedido(pedido));
        pedidoRepository.save(pedido);

        return modelMapper.map(pedido, PedidoDto.class);
    }

    public PedidoDto atualizaStatus(Long id, StatusDto dto) {

        Pedido pedido = pedidoRepository.porIdComItens(id);

        if (pedido == null) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(dto.status());
        pedidoRepository.atualizaStatus(dto.status(), pedido);
        return modelMapper.map(pedido, PedidoDto.class);
    }

    public void aprovaPagamentoPedido(Long id) {

        Pedido pedido = pedidoRepository.porIdComItens(id);

        if (pedido == null) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(Status.PAGO);
        pedidoRepository.atualizaStatus(Status.PAGO, pedido);
    }
}
