package br.com.pagamentoapi.model;

import br.com.pagamentoapi.enums.FormaPagamento;
import br.com.pagamentoapi.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Table(name = "pagamento")
@Entity(name = "pagamento")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer pedidoId;
    private FormaPagamento formaPagamento;
    private StatusPagamento statusPagamento;
}
