package br.com.pagamentoapi.service;

import br.com.pagamentoapi.enums.FormaPagamento;
import br.com.pagamentoapi.model.Pagamento;

public interface PagamentoService {
    Pagamento efetuarPagamento(Integer pedidoId, FormaPagamento formaPagamento);
    void processaPagamento() throws InterruptedException;
}
