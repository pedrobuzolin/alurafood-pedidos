
package br.com.alurafood.pedidos.configuracao;

import br.com.alurafood.pedidos.dto.ItemDoPedidoDto;
import br.com.alurafood.pedidos.dto.PedidoDto;
import br.com.alurafood.pedidos.model.ItemDoPedido;
import br.com.alurafood.pedidos.model.Pedido;
import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Configuracao {

    @Bean
    public ModelMapper obeterModelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.registerModule(new RecordModule());

        mapper.createTypeMap(
                ItemDoPedido.class,
                ItemDoPedidoDto.class
        );

        mapper.createTypeMap(
                ItemDoPedidoDto.class,
                ItemDoPedido.class
        ).setConverter(context -> {
            ItemDoPedidoDto dto = context.getSource();

            ItemDoPedido item = new ItemDoPedido();
            item.setQuantidade(dto.quantidade());
            item.setDescricao(dto.descricao());

            return item;
        });

        mapper.createTypeMap(
                Pedido.class,
                PedidoDto.class
        ).setConverter(context -> {
            Pedido pedido = context.getSource();

            List<ItemDoPedidoDto> itens = pedido.getItens()
                    .stream()
                    .map(item -> mapper.map(
                            item, ItemDoPedidoDto.class
                    ))
                    .toList();

            return new PedidoDto(
                    pedido.getId(),
                    pedido.getDataHora(),
                    pedido.getStatus(),
                    itens
            );
        });

        mapper.createTypeMap(
                PedidoDto.class,
                Pedido.class
        ).setConverter(context -> {
            PedidoDto dto = context.getSource();

            Pedido pedido = new Pedido();

            List<ItemDoPedido> itens = dto.itens()
                    .stream()
                    .map(itemDto -> {
                        ItemDoPedido item = mapper.map(
                                itemDto, ItemDoPedido.class
                        );

                        item.setPedido(pedido);
                        return item;
                    })
                    .collect(java.util.stream.Collectors.toCollection(
                            ArrayList::new
                    ));

            pedido.setItens(itens);

            return pedido;
        });

        return mapper;
    }
}
